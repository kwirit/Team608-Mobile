package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Error.TYPE_CONVERSION_MISMATCH
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_NOT_FOUND
import com.example.scratchinterpretermobile.Controller.Utils.validateNameVariable

class СonvertationTypeBlock(
    override var context: Context
) : InstructionBlock {
    override var runResult: Int = SUCCESS.id

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
        val outputVariableBlock = context.getVar(outputVariable) ?: return VARIABLE_NOT_FOUND.id
        outputVariableBlock

        val errorValidateInputVariable = validateNameVariable(inputVariable)
        if (errorValidateInputVariable != 0) return errorValidateInputVariable
        val inputVariableBlock = context.getVar(outputVariable) ?: return VARIABLE_NOT_FOUND.id

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
                    "Int" -> {}
                    "String" -> {}
                    "Boolean" -> {}
                }
            }
            "String" -> {
                when (inputType) {
                    "Int" -> {}
                    "String" -> {}
                    "Boolean" -> {}
                }
            }
            "Boolean" -> {
                when (inputType) {
                    "Int" -> {}
                    "String" -> {}
                    "Boolean" -> {}
                }
            }
        }
        return SUCCESS.id
    }
}