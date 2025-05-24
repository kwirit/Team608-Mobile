package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression

class LoopBlock(
) : InstructionBlock() {
    private var leftPartCondition: String = ""
    private var rightPartCondition: String = ""
    private var operator: String = "=="

    private var resultValue: Boolean = false

    private var blocksToRun: MutableList<InstructionBlock> = mutableListOf()

    private var scope: HashMap<String, VarBlock> = hashMapOf();

    init {
        mainContext.pushScope(scope)
    }
    fun processInput(leftPartCondition: String, rightPartCondition: String, operator: String) {
        this.leftPartCondition = leftPartCondition
        this.rightPartCondition = rightPartCondition
        this.operator = operator
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
        blocksToRun.add(index, block)
    }

    override fun run(): Int {
        var compareError = compare()

        if (compareError != 0){
            mainContext.popScope();
            return compareError
        }

        while (resultValue) {
            for (block in blocksToRun) {
                val result = block.run();
                if (result != 0) {
                    mainContext.popScope();
                    return result
                }
            }
            compareError = compare()
            if (compareError != 0){
                mainContext.popScope();
                return compareError
            }
        }
        mainContext.popScope()
        return 0;
    }
}