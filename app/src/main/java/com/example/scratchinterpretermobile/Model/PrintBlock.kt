package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.INVALID_FORMAT
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_NOT_FOUND
import com.example.scratchinterpretermobile.Controller.Utils.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.Utils.calculationStringExpression
import com.example.scratchinterpretermobile.Controller.Utils.parserSplit
import com.example.scratchinterpretermobile.Controller.Utils.validateNameVariable
import com.example.scratchinterpretermobile.Controller.Utils.validateString


class PrintBlock(
    var output: String = "",
    var consoleOutput: String = "",

) : InstructionBlock {
    override var context: Context = UIContext
    override var runResult: Int = SUCCESS.id

    fun updateOutput(newOutput: String) {
        output = newOutput.trim();
    }

    fun processInput(input: String) {
        updateOutput(input);
    }

    override fun removeBlock() {
        return
    }

    fun printElement(input: String) : Pair<String, Int> {
        val trimmedInput = input.trim()
        when {
            validateNameVariable(trimmedInput) == 0 -> {
                val value: VarBlock<*> = context.getVar(trimmedInput) ?: return Pair("", VARIABLE_NOT_FOUND.id)
                return when (value) {
                    is IntegerArrayBlock -> Pair(value.getValue().joinToString(separator = " "), SUCCESS.id)
                    is IntegerBlock -> Pair(value.getValue().toString(), SUCCESS.id)
                    is StringBlock -> Pair(value.getValue(), SUCCESS.id)
                    else -> Pair("", VARIABLE_NOT_FOUND.id)
                }
            }
            validateString(trimmedInput) == 0 -> return Pair(trimmedInput.substring(1, trimmedInput.length - 1), SUCCESS.id)
            else -> {
                val (resultArithmeticCalculation, errorArithmeticCalculation) = calculationArithmeticExpression(trimmedInput, context)
                if (errorArithmeticCalculation == 0) {
                    return Pair(resultArithmeticCalculation.toString(), SUCCESS.id)
                }
                val (resultStringCalculation, errorStringCalculation) = calculationStringExpression(trimmedInput, context)
                if (errorStringCalculation == 0) return Pair(resultStringCalculation, SUCCESS.id)
                else return Pair("", INVALID_FORMAT.id)
            }
        }
    }

    override fun run(): Int {
        consoleOutput = ""
        if (output == "") return 0
        val elements = parserSplit(output)

        for (element in elements){
            val (outputElement, errorElement) = printElement(element)
            if (errorElement == 0) consoleOutput += outputElement
            else return errorElement
        }
        return 0
    }
}