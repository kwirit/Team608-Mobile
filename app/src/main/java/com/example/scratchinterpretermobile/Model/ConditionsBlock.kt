package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression

class ConditionsBlock : InstructionBlock() {
    private var leftPartCondition: String = ""
    private var rightPartCondition: String = ""
    private var operator: String = "=="

    private var resultValue: Boolean = false

    var thenBlock: List<InstructionBlock> = emptyList()
    var elseBlock: List<InstructionBlock> = emptyList()

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

    fun addThenBlock(block: InstructionBlock) : Int {
        return 0
    }
    fun addElseBlock(block: InstructionBlock) : Int {
        return 0
    }

    override fun run(): Int {
        val compareError = compare()
        if (compareError != 0){
            return compareError
        }

        val blocksToRun = if (resultValue) thenBlock else elseBlock

        for (block in blocksToRun) {
            val result = block.run();
            if (result != 0) {
                return result
            }
        }

        return 0
    }
}