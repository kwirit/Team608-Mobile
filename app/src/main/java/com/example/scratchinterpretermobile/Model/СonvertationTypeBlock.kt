package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.INVALID_BOOLEAN
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Error.TYPE_CONVERSION_MISMATCH
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_NOT_FOUND
import com.example.scratchinterpretermobile.Controller.Utils.validateNameVariable

class СonvertationTypeBlock(
    override var context: Context
) : InstructionBlock {

    var inputType: String = ""
    var inputVariable = ""
    var inputVariableBlock: VarBlock<*>? = null

    var outputType: String = ""
    var outputVariable = ""
    var outputVariableBlock: VarBlock<*>? = null

    /**
     * Обрабатывает входные данные условия, проверяет оператор сравнения и выполняет сравнение.
     * Удаляет scope из context по
     * @param booleanExpression boolean выражение.
     * @return Код результата выполнения:
     *   - [SUCCESS.id] — успешное выполнение.
     *   - Код ошибки из метода [compare], если сравнение частей условия завершилось с ошибкой.
     */
    fun assembleBlock(outputType: String, outputVariable: String, inputType: String, inputVariable: String): Int {
        this.inputType = inputType
        this.outputType = outputType
        this.inputVariable = inputVariable
        this.outputVariable = outputVariable

        val errorValidateOutputVariable = validateNameVariable(outputVariable)
        if (errorValidateOutputVariable != 0) return errorValidateOutputVariable
        val outputVariableBlock = context.getVar(outputVariable)
        if (outputVariableBlock == null) {
            return VARIABLE_NOT_FOUND.id
        }

        val errorValidateInputVariable = validateNameVariable(inputVariable)
        if (errorValidateInputVariable != 0) return errorValidateInputVariable
        val inputVariableBlock = context.getVar(outputVariable)
        if (inputVariableBlock == null) {
            return VARIABLE_NOT_FOUND.id
        }
        when {
            outputVariableBlock is IntegerBlock && outputType == "Int" -> {}
            outputVariableBlock is StringBlock && outputType == "String" -> {}
            outputVariableBlock is BooleanBlock && outputType == "Boolean" -> {}
            else -> return TYPE_CONVERSION_MISMATCH.id
        }
        when {
            inputVariableBlock is IntegerBlock && inputType == "Int" -> {}
            inputVariableBlock is StringBlock && inputType == "String" -> {}
            inputVariableBlock is BooleanBlock && inputType == "Boolean" -> {}
            else -> return TYPE_CONVERSION_MISMATCH.id
        }

        return SUCCESS.id
    }

    override fun removeBlock() {
        return
    }


    override fun run(): Int {
        when (outputType) {
            "Int" -> {
                when (inputType) {
                    "Int" -> {
                        val resultValue = (inputVariableBlock as IntegerBlock).getValue()
                        (outputVariableBlock as IntegerBlock).setValue(resultValue)
                    }
                    "String" -> {
                        val stringValue = (inputVariableBlock as StringBlock).getValue()
                        val intValue = stringValue.toIntOrNull() ?: return TYPE_CONVERSION_MISMATCH.id
                        (outputVariableBlock as IntegerBlock).setValue(intValue)
                    }
                    "Boolean" -> {
                        val booleanValue = (inputVariableBlock as BooleanBlock).getValue()
                        (outputVariableBlock as IntegerBlock).setValue(if (booleanValue) 1 else 0)
                    }
                    else -> return TYPE_CONVERSION_MISMATCH.id
                }
            }

            "String" -> {
                when (inputType) {
                    "Int" -> {
                        val intValue = (inputVariableBlock as IntegerBlock).getValue()
                        (outputVariableBlock as StringBlock).setValue(intValue.toString())
                    }
                    "String" -> {
                        val stringValue = (inputVariableBlock as StringBlock).getValue()
                        (outputVariableBlock as StringBlock).setValue(stringValue)
                    }
                    "Boolean" -> {
                        val booleanValue = (inputVariableBlock as BooleanBlock).getValue()
                        (outputVariableBlock as StringBlock).setValue(booleanValue.toString())
                    }
                    else -> return TYPE_CONVERSION_MISMATCH.id
                }
            }

            "Boolean" -> {
                when (inputType) {
                    "Int" -> {
                        val intValue = (inputVariableBlock as IntegerBlock).getValue()
                        (outputVariableBlock as BooleanBlock).setValue(intValue != 0)
                    }
                    "String" -> {
                        val stringValue = (inputVariableBlock as StringBlock).getValue()
                        val booleanValue = stringValue.toBooleanStrictOrNull()
                        if (booleanValue != null) {
                            (outputVariableBlock as BooleanBlock).setValue(booleanValue)
                        } else {
                            return INVALID_BOOLEAN.id
                        }
                    }
                    "Boolean" -> {
                        val booleanValue = (inputVariableBlock as BooleanBlock).getValue()
                        (outputVariableBlock as BooleanBlock).setValue(booleanValue)
                    }
                    else -> return TYPE_CONVERSION_MISMATCH.id
                }
            }

            else -> return TYPE_CONVERSION_MISMATCH.id
        }
        return SUCCESS.id
    }
}