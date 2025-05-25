package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.ASSIGNING_DIFFERENT_TYPES
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_ACCESS
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_INDEX
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ASSIGNMENT_ARRAY
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_DOES_NOT_EXIST
import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.validateNameVariable


class AssignmentBlock:InstructionBlock() {
    private var newVarBlock:VarBlock<*>? = null

    private fun getIntegerBlock(integerName: String, integerValue: String): Pair<IntegerBlock, Int> {
        val newIntegerBlock = IntegerBlock("", 0)

        val name = integerName.trim()
        val validateNameError = validateNameVariable(name)
        if(validateNameError != SUCCESS.id) return Pair(newIntegerBlock, validateNameError)

        val integerBlock = context.getVar(name) ?: return Pair(newIntegerBlock, VARIABLE_DOES_NOT_EXIST.id)

        if(!(integerBlock is IntegerBlock)) return Pair(newIntegerBlock, ASSIGNING_DIFFERENT_TYPES.id)

        val (value, calculationValueError) = calculationArithmeticExpression(integerValue)
        if(calculationValueError != SUCCESS.id) return Pair(newIntegerBlock, calculationValueError)

        newIntegerBlock.setName(name)
        newIntegerBlock.setValue(value)

        return Pair(newIntegerBlock, SUCCESS.id)
    }

    fun assignIntegerBlock(integerName:String, integerValue:String):Int {
        val (newIntegerBlock, getError) = getIntegerBlock(integerName, integerValue)
        if(getError != SUCCESS.id) return getError

        context.setVar(newIntegerBlock.getName(), newIntegerBlock)
        newVarBlock = newIntegerBlock.getCopy()

        return SUCCESS.id
    }

    private fun getIntegerArrayBlockByElements(arrayName: String, arrayElements: List<String>): Pair<IntegerArrayBlock, Int> {
        val newIntegerArrayBlock = IntegerArrayBlock(arrayName, mutableListOf<Int>())

        val name = arrayName.trim()
        val validateNameError = validateNameVariable(arrayName)
        if(validateNameError != SUCCESS.id) return Pair(newIntegerArrayBlock, validateNameError)

        val arrayBlock = context.getVar(arrayName) ?: return Pair(newIntegerArrayBlock, VARIABLE_DOES_NOT_EXIST.id)

        if(!(arrayBlock is IntegerArrayBlock)) return Pair(newIntegerArrayBlock, ASSIGNING_DIFFERENT_TYPES.id)

        if(arrayElements.size != arrayBlock.getValue().size) return Pair(newIntegerArrayBlock, INVALID_ARRAY_ACCESS.id)

        val value = mutableListOf<Int>()
        for(arrayElement in arrayElements) {
            val (arrayElementValue, calculationValueError) = calculationArithmeticExpression(arrayElement)
            if(calculationValueError != SUCCESS.id) return Pair(newIntegerArrayBlock, calculationValueError)

            value.add(arrayElementValue)
        }

        newIntegerArrayBlock.setName(name)
        newIntegerArrayBlock.setValue(value)

        return Pair(newIntegerArrayBlock, SUCCESS.id)
    }

    private fun getIntegerArrayBlockByArray(arrayName: String, anotherArrayName: String): Pair<IntegerArrayBlock, Int> {
        val newIntegerArrayBlock = IntegerArrayBlock("", mutableListOf<Int>())

        // первый массив
        val name = arrayName.trim()
        val validateNameError = validateNameVariable(name)
        if(validateNameError != SUCCESS.id) return Pair(newIntegerArrayBlock, validateNameError)
        val integerBlock = context.getVar(name) ?: return Pair(newIntegerArrayBlock, VARIABLE_DOES_NOT_EXIST.id)

        // второй массив
        val anotherName = anotherArrayName.trim()
        val validateAnotherNameError = validateNameVariable(anotherName)
        if(validateAnotherNameError != SUCCESS.id) return Pair(newIntegerArrayBlock, validateAnotherNameError)
        val anotherIntegerBlock = context.getVar(anotherName) ?: return Pair(newIntegerArrayBlock, VARIABLE_DOES_NOT_EXIST.id)

        if(!(integerBlock is IntegerArrayBlock) || !(anotherIntegerBlock is IntegerArrayBlock)) {
            return Pair(newIntegerArrayBlock, ASSIGNING_DIFFERENT_TYPES.id)
        }

        newIntegerArrayBlock.setName(anotherIntegerBlock.getName())
        newIntegerArrayBlock.setValue(anotherIntegerBlock.getValue())

        return Pair(newIntegerArrayBlock, SUCCESS.id)
    }


    fun assignIntegerArrayBlock(arrayName:String, arrayValue: String): Int {
        val arrayElements = arrayValue.split(",").map { it.trim() }

        var newIntegerArrayBlock: IntegerArrayBlock? = null
        if(arrayElements.size >= 1) {
            if(arrayElements.size > 1) {
                val (resultingArray, getError) = getIntegerArrayBlockByElements(arrayName, arrayElements)
                if(getError != SUCCESS.id) return getError
                newIntegerArrayBlock = resultingArray
            }
            else {
                val (resultingArray, getError) = getIntegerArrayBlockByArray(arrayName, arrayElements[0])
                if(getError != SUCCESS.id) return getError
                newIntegerArrayBlock = resultingArray
            }
        }
        else return INVALID_ASSIGNMENT_ARRAY.id

        context.setVar(newIntegerArrayBlock.getName(), newIntegerArrayBlock)
        newVarBlock = newIntegerArrayBlock.getCopy()

        return SUCCESS.id
    }

    private fun getIntegerArrayBlockByElement(arrayName: String, arrayIndex: String, arrayElementValue: String): Pair<IntegerArrayBlock, Int> {
        val newIntegerArrayBlock = IntegerArrayBlock("", mutableListOf<Int>())

        val name = arrayName.trim()
        val validateNameError = validateNameVariable(name)
        if(validateNameError != SUCCESS.id) return Pair(newIntegerArrayBlock, validateNameError)

        val integerArrayBlock = context.getVar(name) ?: return Pair(newIntegerArrayBlock, VARIABLE_DOES_NOT_EXIST.id)
        if(!(integerArrayBlock is IntegerArrayBlock)) return Pair(newIntegerArrayBlock, ASSIGNING_DIFFERENT_TYPES.id)

        val (index, calculationIndexError) = calculationArithmeticExpression(arrayIndex)
        if(calculationIndexError != SUCCESS.id) return Pair(newIntegerArrayBlock, calculationIndexError)
        else if((index < 0) || (index >= integerArrayBlock.getValue().size)) return Pair(newIntegerArrayBlock, INVALID_ARRAY_INDEX.id)

        val (elementValue, calculationElementValue) = calculationArithmeticExpression(arrayElementValue)
        if(calculationElementValue != SUCCESS.id) return Pair(newIntegerArrayBlock, calculationElementValue)

        val arrayValue = integerArrayBlock.getValue()
        arrayValue[index] = elementValue

        newIntegerArrayBlock.setName(name)
        newIntegerArrayBlock.setValue(arrayValue)

        return Pair(newIntegerArrayBlock, SUCCESS.id)
    }

    fun assignElementIntegerArrayBlock(arrayName:String, arrayIndex:String, arrayElementValue:String): Int {
        val (newIntegerArrayBlock, getError) = getIntegerArrayBlockByElement(arrayName, arrayIndex, arrayElementValue)
        if(getError != SUCCESS.id) return getError

        context.setVar(newIntegerArrayBlock.getName(), newIntegerArrayBlock)
        newVarBlock = newIntegerArrayBlock.getCopy()

        return SUCCESS.id
    }

    
    override fun run(): Int {
        return SUCCESS.id
    }

}