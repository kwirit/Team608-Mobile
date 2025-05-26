package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Model.VarBlock

class ConditionBlock(
) : InstructionBlock {
    override var context: Context = UIContext

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


    fun processInput(leftPartCondition: String, rightPartCondition: String, operator: String, thenBlock: MutableList<InstructionBlock>, elseBlock: MutableList<InstructionBlock>) {
        this.leftPartCondition = leftPartCondition
        this.rightPartCondition = rightPartCondition
        this.operator = operator
        this.thenBlock = thenBlock
        this.elseBlock = elseBlock

        compare()
        if(resultValue) {
            rollbackElseBlock()
            rollThenBlock()
        }
        else {
            rollbackThenBlock()
            rollElseBlock()
        }
    }

    /**
     * Выполняет сравнение двух арифметических выражений на основе заданного оператора.
     * @return 0 в случае успеха, код ошибки — в случае неудачи.
     * В случае успеха изменяет resultValue.
     */
    private fun compare(): Int {
        val (valueLeftPart, errorLeft) = calculationArithmeticExpression(leftPartCondition)
        if (errorLeft != SUCCESS.id) {
            return errorLeft;
        }

        val (valueRightPart, errorRight) = calculationArithmeticExpression(rightPartCondition)
        if (errorRight != SUCCESS.id) {
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

        return SUCCESS.id
    }

    fun setThenBlock(thenBlocks:MutableList<InstructionBlock>) {
        this.thenBlock = thenBlocks

        return
    }

    fun setElseBlock(elseBlocks: MutableList<InstructionBlock>) {
        this.elseBlock = elseBlocks

        return
    }

    fun rollbackThenBlock() {
        rollbackActions(thenBlock)

        return
    }

    fun rollbackElseBlock() {
        rollbackActions(elseBlock)

        return
    }

    fun rollThenBlock() {
        rollActions(thenBlock)

        return
    }

    fun rollElseBlock() {
        rollActions(elseBlock)

        return
    }

    override fun removeBlock() {}

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