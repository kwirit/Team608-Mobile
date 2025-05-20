package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_NOT_FOUND
import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.validateNameVariable
import com.example.scratchinterpretermobile.Controller.validateSyntaxArrayName

class PrintBlock(
    private var output: String = "",
    public var consoleOutput: String = ""
) : InstructionBlock() {

    fun updateOutput(newOutput: String) {
        output = newOutput.trim();
    }

    override fun run(): Int {
        consoleOutput = ""
        val errorValidateNameVariable = validateNameVariable(output)
        if (errorValidateNameVariable == 0) {
            val value: VarBlock = interpreterContext.getVar(output) ?: return VARIABLE_NOT_FOUND.id
            when (value) {
                is IntegerArrayBlock -> consoleOutput = (value.value as MutableList<Int>).joinToString(separator = " ")
                is IntegerBlock -> consoleOutput = (value.value as Int).toString()
            }
        }
        else {
            val (resultCalculation, errorCalculation) = calculationArithmeticExpression(output)
            if (errorCalculation == 0) {
                consoleOutput.plus(resultCalculation.toString())
            }
            else return errorCalculation
        }
        return 0
    }
}