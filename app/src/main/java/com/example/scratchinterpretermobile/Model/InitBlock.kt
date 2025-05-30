package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.CONTEXT_IS_NULL
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
    override var runResult: Int = RUNTIME_ERROR.id
    private val newVarBlocks:MutableList<VarBlock<*>> = mutableListOf()

    private fun setRunResult(codeError:Int): Int {
        runResult = codeError
        return codeError
    }

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
        if(fillError != SUCCESS.id) return setRunResult(fillError)

        // Добавляем новые переменные в контекст и сохраняем их копию
        val scoupe = context.peekScope()
        for(variableName in variableNames) {
            val newIntegerBlock = IntegerBlock(variableName, 0)
            newVarBlocks.add(newIntegerBlock.getCopy())
        }

        return setRunResult(SUCCESS.id)
    }

    fun assembleIntegerArrayBlock(inputArrayName:String, inputArrayLength:String): Int {
        context?: return CONTEXT_IS_NULL.id

        removeBlock()

        val arrayNames = mutableListOf<String>()
        val fillError =  fillNames(arrayNames, inputArrayName)
        if(fillError != SUCCESS.id) return setRunResult(fillError)
        else if(arrayNames.size > 1) return setRunResult(MULTIPLE_INITIALIZATION.id)
        else if(inputArrayName.isEmpty()) return setRunResult(INVALID_ARRAY_LENGTH.id)

        val (arrayLength, calculationLengthError) = calculationArithmeticExpression(inputArrayLength, context)
        if(calculationLengthError != SUCCESS.id) return setRunResult(calculationLengthError)
        else if(arrayLength <= 0) return setRunResult(INVALID_ARRAY_LENGTH.id)

        //сохраняем копию
        for(arrayName in arrayNames) {
            val newIntegerArrayBlock = IntegerArrayBlock(arrayName, MutableList<Int>(arrayLength) {0})
            newVarBlocks.add(newIntegerArrayBlock.getCopy())
        }

        return setRunResult(SUCCESS.id)
    }

    // для тестов
    fun removeBlocksFromContext() {
        for(varBlock in newVarBlocks) {
            context.removeVar(varBlock.getName())
        }
        return
    }

    override fun removeBlock(){
        runResult = RUNTIME_ERROR.id

        for(varBlock in newVarBlocks) {
            context.removeVar(varBlock.getName())
        }
        newVarBlocks.clear()

        return;
    }

    override fun run(): Int {
        if(runResult != SUCCESS.id) return runResult

        for(varBlock in newVarBlocks) {
            if(context.getVar(varBlock.getName()) != null) return REINITIALIZE_VARIABLE.id

            context.peekScope()!!.put(varBlock.getName(), varBlock.getCopy())
        }

        return SUCCESS.id
    }
}
