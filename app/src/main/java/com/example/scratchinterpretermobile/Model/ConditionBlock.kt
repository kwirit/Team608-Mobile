package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Model.VarBlock

class ConditionBlock(
) : InstructionBlock() {
    private var operator: String = "=="
    private var leftPartCondition: String = ""
    private var rightPartCondition: String = ""

    private var resultValue: Boolean = false

    private var thenBlock: MutableList<InstructionBlock> = mutableListOf()
    private var elseBlock: MutableList<InstructionBlock> = mutableListOf()

    private var scope: HashMap<String, VarBlock<*>> = hashMapOf();

    init {
        context.pushScope(scope)
    }

    fun processInput(leftPartCondition: String, rightPartCondition: String, operator: String, thenBlock: MutableList<InstructionBlock>) {
        this.leftPartCondition = leftPartCondition
        this.rightPartCondition = rightPartCondition
        this.operator = operator
        this.thenBlock = thenBlock
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

    fun addThenBlock(index: Int, block: InstructionBlock) {
        thenBlock.add(index, block)
    }
    fun addElseBlock(index: Int, block: InstructionBlock) {
        elseBlock.add(index, block)
    }

    override fun run(): Int {
        val compareError = compare()
        if (compareError != 0){
            context.popScope();
            return compareError
        }

        val blocksToRun = if (resultValue) thenBlock else elseBlock

        for (block in blocksToRun) {
            val result = block.run();
            if (result != 0) {
                context.popScope();
                return result
            }
        }
        context.popScope();
        return 0
    }
}