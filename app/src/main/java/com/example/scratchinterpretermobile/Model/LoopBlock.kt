package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression

class LoopBlock(
) : InstructionBlock {
    override var context: Context = UIContext

    private var leftPartCondition: String = ""
    private var rightPartCondition: String = ""
    private var operator: String = "=="
    private var iterations = 0

    private var resultValue: Boolean = false

    private var blocksToRun: MutableList<InstructionBlock> = mutableListOf()

    private var scope: HashMap<String, VarBlock<*>> = hashMapOf();

//    init {
//        context.pushScope(scope)
//    }

    fun processInput(leftPartCondition: String, rightPartCondition: String, operator: String, blocksToRun: MutableList<InstructionBlock>): Int {
        context.pushScope(scope)

        this.leftPartCondition = leftPartCondition
        this.rightPartCondition = rightPartCondition
        this.operator = operator
        setBlocksToRun(blocksToRun)

        var errorCompare = compare()
        rollbackBlocksToRun()

        while (errorCompare == SUCCESS.id && resultValue) {
            rollBlocksToRun()
            errorCompare = compare()
            ++iterations
        }

        context.popScope()
        return SUCCESS.id
    }

    fun setBlocksToRun(blocksToRun: MutableList<InstructionBlock>) {
        this.blocksToRun = blocksToRun
    }
    fun rollbackBlocksToRun() {
        rollbackActions(blocksToRun)
    }
    fun rollBlocksToRun() {
        rollActions(blocksToRun)
    }


    /**
     * Выполняет сравнение двух арифметических выражений на основе заданного оператора.
     * @return 0 в случае успеха, код ошибки — в случае неудачи.
     * В случае успеха изменяет resultValue.
     */
    private fun compare(): Int {
        val (valueLeftPart, errorLeft) = calculationArithmeticExpression(leftPartCondition)
        if (errorLeft != 0) {
            return errorLeft;
        }

        val (valueRightPart, errorRight) = calculationArithmeticExpression(rightPartCondition)
        if (errorRight != 0) {
            return errorRight
        }

        resultValue = when (operator) {
            "==" -> valueLeftPart == valueRightPart
            "<=" -> valueLeftPart <= valueRightPart
            "<" -> valueLeftPart < valueRightPart
            ">=" -> valueLeftPart >= valueRightPart
            ">" -> valueLeftPart > valueRightPart
            "!=" -> valueLeftPart != valueRightPart
            else -> false
        }

        return 0
    }

    override fun removeBlock() {
        rollbackBlocksToRun()
    }

    override fun run(): Int {
        context.pushScope(scope)
        var compareError = compare()

        if (compareError != 0){
            context.popScope();
            return compareError
        }

        while (resultValue) {
            for (block in blocksToRun) {
                val result = block.run();
                if (result != 0) {
                    context.popScope();
                    return result
                }
            }
            compareError = compare()
            if (compareError != 0){
                context.popScope();
                return compareError
            }
        }
        context.popScope()
        return 0;
    }
}