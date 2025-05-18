package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Model.Context

class ConditionsBlock(
    private val leftPartCondition: String,
    private val rightPartCondition: String,
    private val operator: String = "=="
) : InstructionBlock() {

    private var resultValue: Boolean = false

    private var thenBlock: MutableList<InstructionBlock> = mutableListOf()
    private var elseBlock: MutableList<InstructionBlock> = mutableListOf()

    private var scope: HashMap<String, VarBlock> = hashMapOf();

    init {
        mainContext.pushScope(scope)
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
            mainContext.popScope();
            return compareError
        }

        val blocksToRun = if (resultValue) thenBlock else elseBlock

        for (block in blocksToRun) {
            val result = block.run();
            if (result != 0) {
                mainContext.popScope();
                return result
            }
        }
        mainContext.popScope();
        return 0
    }
}