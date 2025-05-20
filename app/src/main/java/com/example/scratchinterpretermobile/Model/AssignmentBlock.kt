package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.CLASS_DOESNT_NOT_EXIST
import com.example.scratchinterpretermobile.Controller.Error.CONTEXT_IS_NULL
import com.example.scratchinterpretermobile.Controller.Error.EMPTY_ARITHMETIC
import com.example.scratchinterpretermobile.Controller.Error.EMPTY_NAME
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_ELEMENT_ASSIGNMENT
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ASSIGNMENT_ARRAY
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ASSIGNMENT_INTEGER
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_DOES_NOT_EXIST
import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression

class AssignmentBlock:InstructionBlock() {
    var oldVarBlock:VarBlock? = null
    var newVarBlock:VarBlock? = null

    private fun updateOldVarBlock(varBlock: VarBlock): Int {
        if(varBlock is IntegerBlock) {
            oldVarBlock = getCopyIntegerBlock(varBlock)
        }
        else if(varBlock is IntegerArrayBlock) {
            oldVarBlock = getCopyIntegerArrayBlock(varBlock)
        }

        return SUCCESS.id
    }

    private fun assignmentIntegerBlock(varBlock: IntegerBlock, valueVariable: String): Int {
        var newIntegerBlock = getCopyIntegerBlock(varBlock)

        val (newValue, calculatingError) = calculationArithmeticExpression(valueVariable)
        if(calculatingError != SUCCESS.id) return calculatingError

        varBlock.value = newValue // Работа с контекстом проверить

        newIntegerBlock.value = newValue
        newVarBlock = newIntegerBlock

        return SUCCESS.id
    }

    private fun assignmentArrayElement(varBlock: IntegerArrayBlock, valueVariable: String, arrayIndex: String) : Int {
        val (index, calculationIndexError) = calculationArithmeticExpression(arrayIndex)
        if(calculationIndexError != SUCCESS.id) return calculationIndexError

        val (newValue, calculationValueError) = calculationArithmeticExpression(valueVariable)
        if(calculationValueError != SUCCESS.id) return calculationValueError

        val newIntegerArrayBlock = getCopyIntegerArrayBlock(varBlock)
        (newIntegerArrayBlock.value as MutableList<Int>)[index] = newValue

        (varBlock.value as MutableList<Int>)[index] = newValue // Рвбота с контекстом проверить

        newVarBlock = newIntegerArrayBlock

        return SUCCESS.id
    }

    private fun assignmentArray(varBlock: IntegerArrayBlock, valueVariable: String): Int {
        val newIntegerArrayBlock = getCopyIntegerArrayBlock(varBlock)
        var newValue = newIntegerArrayBlock.value as MutableList<Int>

        val arifmeticExpressions = valueVariable.split(",")
        if(arifmeticExpressions.size != newValue.size) return INVALID_ASSIGNMENT_ARRAY.id

        var i = 0
        for(arifmeticExpression in arifmeticExpressions) {
            val (arifmeticResult, calculationError) = calculationArithmeticExpression(arifmeticExpression)
            if(calculationError != SUCCESS.id) return calculationError

            (varBlock.value as MutableList<Int>)[i] = arifmeticResult // Работа с контекстом проверить
            newValue[i] = arifmeticResult

            ++i
        }

        newVarBlock = newIntegerArrayBlock

        return SUCCESS.id
    }

    private fun assignmentIntegerArrayBlock(varBlock: IntegerArrayBlock, valueVariable: String, arrayIndex: String): Int {
        var assignmentError = SUCCESS.id

        if(arrayIndex.isEmpty()) {
            assignmentError = assignmentArray(varBlock, valueVariable)
        }
        else {
            assignmentError = assignmentArrayElement(varBlock, valueVariable, arrayIndex)
        }

        if(assignmentError != SUCCESS.id) return assignmentError

        return SUCCESS.id
    }

    private fun updateNewVarBlock(varBlock: VarBlock, valueVariable: String, arrayIndex: String): Int {
        var assignmentError = SUCCESS.id

        if(varBlock is IntegerBlock) {
            assignmentError = assignmentIntegerBlock(varBlock, valueVariable)
        }
        else if(varBlock is IntegerArrayBlock) {
            assignmentError = assignmentIntegerArrayBlock(varBlock, valueVariable, arrayIndex)
        }
        else return VARIABLE_DOES_NOT_EXIST.id

        if(assignmentError != SUCCESS.id) return assignmentError

        return SUCCESS.id
    }

    private fun removeContextChanges(varBlock: VarBlock) : Int {
        if(oldVarBlock != null) mainContext.setVar(oldVarBlock!!.name, oldVarBlock!!)

        return SUCCESS.id
    }

    fun processInput(nameVariable:String, valueVariable:String, arrayIndex:String = String()): Int {
        if(mainContext == null) return CONTEXT_IS_NULL.id

        if(nameVariable.isEmpty()) return EMPTY_NAME.id
        else if(valueVariable.isEmpty()) return EMPTY_ARITHMETIC.id

        var varBlock = mainContext.getVar(nameVariable) ?: return VARIABLE_DOES_NOT_EXIST.id

        // откатываемся на прошлое сохранение при каждом изменении
        removeContextChanges(varBlock)

        // делаем сохранение
        updateOldVarBlock(varBlock)

        // Обновляем переменную которая будет присваиваться
        val updateNewVarBlockError =  updateNewVarBlock(varBlock, valueVariable, arrayIndex)
        if(updateNewVarBlockError != SUCCESS.id) return updateNewVarBlockError

        return SUCCESS.id
    }

    override fun run(): Int {
        return SUCCESS.id
    }

}