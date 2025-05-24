package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.CONTEXT_IS_NULL
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_LENGTH
import com.example.scratchinterpretermobile.Controller.Error.MULTIPLE_INITIALIZATION
import com.example.scratchinterpretermobile.Controller.Error.REDECLARING_A_VARIABLE
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.validateNameVariable

class InitBlock : InstructionBlock() {
    private val newVarBlocks:MutableList<VarBlock<*>> = mutableListOf()

    private fun fillNames(variableNames: MutableList<String>, input: String): Int {
        val names = input.split(",").map { it.trim() }
        for(name in names) {
            val validateNameVariableError = validateNameVariable(name)
            if(validateNameVariableError != SUCCESS.id) return validateNameVariableError
            else if(mainContext.getVar(name) != null) return REDECLARING_A_VARIABLE.id

            variableNames.add(name)
        }

        return SUCCESS.id
    }

    fun initIntegerBlock(input:String): Int {
        if(mainContext == null) return CONTEXT_IS_NULL.id

        // Удаляем инициализированные переменные этого блока из контекста
        for(varBlock in newVarBlocks) mainContext.removeVar(varBlock.getName())
        newVarBlocks.clear()

        val variableNames = mutableListOf<String>()
        val fillError =  fillNames(variableNames, input)
        if(fillError != SUCCESS.id) return fillError

        // Добавляем новые переменные в контекст и сохраняем их копию
        val scoupe = mainContext.peekScope()
        for(variableName in variableNames) {
            val newIntegerBlock = IntegerBlock(variableName, 0)
            newVarBlocks.add(newIntegerBlock.getCopy())
            scoupe!!.put(newIntegerBlock.getName(), newIntegerBlock)
        }

        return SUCCESS.id
    }

    fun initIntegerArrayBlock(inputArrayName:String, inputArrayLength:String): Int {
        if(mainContext == null) return CONTEXT_IS_NULL.id

        // Удаляем инициализированные переменные этого блока из контекста
        for(varBlock in newVarBlocks) mainContext.removeVar(varBlock.getName())
        newVarBlocks.clear()

        val arrayNames = mutableListOf<String>()
        val fillError =  fillNames(arrayNames, inputArrayName)
        if(fillError != SUCCESS.id) return fillError
        else if(arrayNames.size > 1) return MULTIPLE_INITIALIZATION.id
        else if(inputArrayName.isEmpty()) return INVALID_ARRAY_LENGTH.id

        val (arrayLength, calculationLengthError) = calculationArithmeticExpression(inputArrayLength)
        if(calculationLengthError != SUCCESS.id) return calculationLengthError
        else if(arrayLength <= 0) return INVALID_ARRAY_LENGTH.id

        // Добавляем новый массив в контекст и сохраняем копию
        val scope = mainContext.peekScope()
        for(arrayName in arrayNames) {
            val newIntegerArrayBlock = IntegerArrayBlock(arrayName, MutableList<Int>(arrayLength) {0})
            newVarBlocks.add(newIntegerArrayBlock.getCopy())
            scope!!.put(newIntegerArrayBlock.getName(), newIntegerArrayBlock)
        }

        return SUCCESS.id
    }


    override fun run(): Int {

        return 0
    }
}
