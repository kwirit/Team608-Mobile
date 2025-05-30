package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.CONTEXT_IS_NULL
import com.example.scratchinterpretermobile.Controller.Error.INVALID_FORMAT
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_NOT_FOUND
import com.example.scratchinterpretermobile.Controller.Utils.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.Utils.calculationStringExpression
import com.example.scratchinterpretermobile.Controller.Utils.parserSplit
import com.example.scratchinterpretermobile.Controller.Utils.validateNameVariable
import com.example.scratchinterpretermobile.Controller.Utils.validateString


class PrintBlock(
    override var context: Context,
    var output: String = "",
    var consoleOutput: String = "",

) : InstructionBlock {
    fun updateOutput(newOutput: String) {
        output = newOutput.trim();
    }

    override fun removeBlock() {
        return
    }

    fun printElement(input: String) : Pair<String, Int> {
        val trimmedInput = input.trim()
        when {
            validateNameVariable(trimmedInput) == SUCCESS.id -> {
                val value: VarBlock<*> = context.getVar(trimmedInput) ?: return Pair("", VARIABLE_NOT_FOUND.id)
                return when (value) {
                    is IntegerArrayBlock -> Pair(value.getValue().joinToString(separator = " "), SUCCESS.id)
                    is IntegerBlock -> Pair(value.getValue().toString(), SUCCESS.id)
                    is StringBlock -> Pair(value.getValue(), SUCCESS.id)
                    else -> Pair("", VARIABLE_NOT_FOUND.id)
                }
            }
            validateString(trimmedInput) == SUCCESS.id -> return Pair(trimmedInput.substring(1, trimmedInput.length - 1), SUCCESS.id)
            else -> {
                val (resultArithmeticCalculation, errorArithmeticCalculation) = calculationArithmeticExpression(trimmedInput, context)
                if (errorArithmeticCalculation == SUCCESS.id) {
                    return Pair(resultArithmeticCalculation.toString(), SUCCESS.id)
                }
                val (resultStringCalculation, errorStringCalculation) = calculationStringExpression(trimmedInput, context)
                if (errorStringCalculation == SUCCESS.id) return Pair(resultStringCalculation, SUCCESS.id)
                else return Pair("", INVALID_FORMAT.id)
            }
        }
    }

    override fun run(): Int {
        context ?: return CONTEXT_IS_NULL.id

        consoleOutput = ""
        if (output == "") return SUCCESS.id
        val elements = parserSplit(output)

        for (element in elements){
            val (outputElement, errorElement) = printElement(element)
            if (errorElement == SUCCESS.id) consoleOutput += outputElement
            else return errorElement
        }

        outputList.add(consoleOutput)
        return SUCCESS.id
    }
}