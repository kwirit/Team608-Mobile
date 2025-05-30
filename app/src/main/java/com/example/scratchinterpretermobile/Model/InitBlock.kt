package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.CONTEXT_IS_NULL
import com.example.scratchinterpretermobile.Controller.Error.INITIALIZATION_ERROR
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_LENGTH
import com.example.scratchinterpretermobile.Controller.Error.MULTIPLE_INITIALIZATION
import com.example.scratchinterpretermobile.Controller.Error.REINITIALIZE_VARIABLE
import com.example.scratchinterpretermobile.Controller.Error.RUNTIME_ERROR
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Utils.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.Utils.validateNameVariable

class InitBlock(
    override var context: Context
) : InstructionBlock {
    private val newVarBlocks:MutableList<VarBlock<*>> = mutableListOf()

    private fun fillNames(variableNames: MutableList<String>, input: String): Int {
        val names = input.split(",").map { it.trim() }
        for(name in names) {
            val validateNameVariableError = validateNameVariable(name)
            if(validateNameVariableError != SUCCESS.id) return validateNameVariableError

            variableNames.add(name)
        }

        return SUCCESS.id
    }

    fun assembleIntegerBlock(input:String): Int {
        context ?: return CONTEXT_IS_NULL.id

        removeBlock()

        val variableNames = mutableListOf<String>()
        val fillError =  fillNames(variableNames, input)
        if(fillError != SUCCESS.id) return fillError

        // Добавляем новые переменные в контекст и сохраняем их копию
        val scoupe = context.peekScope()
        for(variableName in variableNames) {
            val newIntegerBlock = IntegerBlock(variableName, 0)
            newVarBlocks.add(newIntegerBlock.getCopy())
        }

        return SUCCESS.id
    }

    fun assembleIntegerArrayBlock(inputArrayName:String, inputArrayLength:String): Int {
        context?: return CONTEXT_IS_NULL.id

        removeBlock()

        val arrayNames = mutableListOf<String>()
        val fillError =  fillNames(arrayNames, inputArrayName)
        if(fillError != SUCCESS.id) return fillError
        else if(arrayNames.size > 1) return MULTIPLE_INITIALIZATION.id
        else if(inputArrayName.isEmpty()) return INVALID_ARRAY_LENGTH.id

        val (arrayLength, calculationLengthError) = calculationArithmeticExpression(inputArrayLength, context)
        if(calculationLengthError != SUCCESS.id) return calculationLengthError
        else if(arrayLength <= 0) return INVALID_ARRAY_LENGTH.id

        //сохраняем копию
        for(arrayName in arrayNames) {
            val newIntegerArrayBlock = IntegerArrayBlock(arrayName, MutableList<Int>(arrayLength) {0})
            newVarBlocks.add(newIntegerArrayBlock.getCopy())
        }

        return SUCCESS.id
    }

    fun assembleStringBlock(inputStringName:String, inputStringValue:String): Int {
        context?: return CONTEXT_IS_NULL.id

        removeBlock()

        val stringNames = mutableListOf<String>()
        val fillError = fillNames(stringNames, inputStringName)
        if(fillError != SUCCESS.id) return fillError
        else if(stringNames.size != 1) return INITIALIZATION_ERROR.id

        for(stringName in stringNames) {
            val newStringBlock = StringBlock(stringName, inputStringValue)
            newVarBlocks.add(newStringBlock)
        }

        return SUCCESS.id
    }

    fun assembleBooleanBlock(inputBooleanName:String): Int {
        context?: return CONTEXT_IS_NULL.id

        removeBlock()

        val booleanNames = mutableListOf<String>()
        val fillError = fillNames(booleanNames, inputBooleanName)
        if(fillError != SUCCESS.id) return fillError
        else if(booleanNames.size != 1) return INITIALIZATION_ERROR.id

        for(booleanName in booleanNames) {
            val newStringBlock = BooleanBlock(booleanName, false)
            newVarBlocks.add(newStringBlock)
        }

        return SUCCESS.id
    }

    // для тестов
    fun removeBlocksFromContext() {
        for(varBlock in newVarBlocks) {
            context.removeVar(varBlock.getName())
        }
        return
    }

    override fun removeBlock(){
        for(varBlock in newVarBlocks) {
            context.removeVar(varBlock.getName())
        }
        newVarBlocks.clear()

        return;
    }

    override fun run(): Int {
        context ?: return CONTEXT_IS_NULL.id

        if(newVarBlocks.size <= 0) return RUNTIME_ERROR.id

        for(varBlock in newVarBlocks) {
            if(context.getVar(varBlock.getName()) != null) return REINITIALIZE_VARIABLE.id

            context.peekScope()!!.put(varBlock.getName(), varBlock.getCopy())
        }

        return SUCCESS.id
    }
}
