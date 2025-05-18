package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.CONTEXT_IS_NULL
import com.example.scratchinterpretermobile.Controller.Error.MULTIPLE_INITIALIZATION
import com.example.scratchinterpretermobile.Controller.Error.REDECLARING_A_VARIABLE
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.calculationPostfix
import com.example.scratchinterpretermobile.Controller.getElementFromString
import com.example.scratchinterpretermobile.Controller.transferPrefixToPostfix
import com.example.scratchinterpretermobile.Controller.validateNameVariable

class InitBlock : InstructionBlock() {
    private var newVarBlocks = mutableListOf<VarBlock>()

    private fun fillIntegerBlock(variableName: String, newBlocks: MutableList<VarBlock>): Int {
        val words = variableName.split(",").map { it.trim() }

        for(word in words) {
            val validateNameVariableError = validateNameVariable(word)
            if(validateNameVariableError != SUCCESS.id) return validateNameVariableError

            if(Context.getVar(word) != null) return REDECLARING_A_VARIABLE.id

            val newBlock = IntegerBlock(word, 0)
            newBlocks.add(newBlock)
        }

        return SUCCESS.id
    }

    private fun fillIntegerArrayBlock(variableName: String, arrayLength: String, newBlocks: MutableList<VarBlock>): Int {
        val words = variableName.split(",").map { it.trim() }
        if(words.size > 1) return MULTIPLE_INITIALIZATION.id

        val word = words[0]

        val validateNameVariableError = validateNameVariable(word)
        if(validateNameVariableError != SUCCESS.id) return validateNameVariableError

        val (arrayLength, arifmeticError) = calculationArithmeticExpression(arrayLength)
        if(arifmeticError != SUCCESS.id) return arifmeticError

        val newBlock = IntegerArrayBlock(word, mutableListOf<Int>(arrayLength))
        newBlocks.add((newBlock))

        return SUCCESS.id
    }

    private fun updateContext(newBlocks: MutableList<VarBlock>): Int {
        val scope = Context.peekScope()

        for(varBlock in newVarBlocks) {
            scope!!.remove(varBlock.name)
        }

        for(varBlock in newBlocks) {
            scope!!.put(varBlock.name, varBlock)
        }

        return SUCCESS.id
    }

    fun processInput(variableName:String, arrayLength:String = ""): Int {
        if(Context.peekScope() == null) return CONTEXT_IS_NULL.id

        val newBlocks = mutableListOf<VarBlock>()

        var fillError = SUCCESS.id
        if(arrayLength == "") {
            fillError = fillIntegerBlock(variableName, newBlocks)
        }
        else {
            fillError = fillIntegerArrayBlock(variableName, arrayLength, newBlocks)
        }

        if(fillError != SUCCESS.id) return fillError

        updateContext(newBlocks)

        return SUCCESS.id
    }

    override fun run(): Int {
        //
        
        return 0
    }
}