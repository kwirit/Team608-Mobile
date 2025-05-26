package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_NOT_FOUND
import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.validateNameVariable

class PrintBlock(
    var output: String = "",
    var consoleOutput: String = "",

) : InstructionBlock {
    override var context: Context = UIContext

    fun updateOutput(newOutput: String) {
        output = newOutput.trim();
    }

    fun processInput(input: String) {
        updateOutput(input);
    }

    override fun run(): Int {
        consoleOutput = ""
        if (output == "") return 0
        val errorValidateNameVariable = validateNameVariable(output)
        if (errorValidateNameVariable == 0) {
            val value: VarBlock<*> = context.getVar(output) ?: return VARIABLE_NOT_FOUND.id
            when (value) {
                is IntegerArrayBlock -> consoleOutput = value.getValue().joinToString(separator = " ")
                is IntegerBlock -> consoleOutput = value.getValue().toString()
            }
        }
        else {
            val (resultCalculation, errorCalculation) = calculationArithmeticExpression(output)
            if (errorCalculation == 0) {
                consoleOutput = resultCalculation.toString()
            }
            else return errorCalculation
        }
        return 0
    }
}