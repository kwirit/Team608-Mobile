package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.ASSIGNING_DIFFERENT_TYPES
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_ACCESS
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_INDEX
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ASSIGNMENT_ARRAY
import com.example.scratchinterpretermobile.Controller.Error.RUNTIME_ERROR
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_IS_NOT_INITIALIZED
import com.example.scratchinterpretermobile.Controller.Utils.calculationArithmeticExpression
import com.example.scratchinterpretermobile.Controller.Utils.validateNameVariable




class AssignmentBlock(
    override var context: Context
): InstructionBlock {
    override var runResult: Int = RUNTIME_ERROR.id

//    override var context: Context = UIContext
    private var newVarBlock: VarBlock<*> = IntegerBlock("NONE", 0) // новый блок

    private var valueVarBlock:String = String()
    private var index:String = String()





    private fun setRunResult(codeError:Int): Int {
        runResult = codeError
        return codeError
    }

    // <-------------------- Присваивание целочисленной переменной -------------------->

    private fun getIntegerBlock(integerName: String, integerValue: String): Pair<IntegerBlock, Int> {
        val newIntegerBlock = IntegerBlock("", 0)

        val name = integerName.trim()
        val validateNameError = validateNameVariable(name)
        if(validateNameError != SUCCESS.id) return Pair(newIntegerBlock, validateNameError)

        val integerBlock = context.getVar(name) ?: return Pair(newIntegerBlock, VARIABLE_IS_NOT_INITIALIZED.id)

        if(!(integerBlock is IntegerBlock)) return Pair(newIntegerBlock, ASSIGNING_DIFFERENT_TYPES.id)

        val (value, calculationValueError) = calculationArithmeticExpression(integerValue, context)
        if(calculationValueError != SUCCESS.id) return Pair(newIntegerBlock, calculationValueError)

        newIntegerBlock.setName(name)
        newIntegerBlock.setValue(value)

        return Pair(newIntegerBlock, SUCCESS.id)
    }


    /**
     * Присваивает новое значение целочисленной переменной
     * @param integerName имя переменной
     * @param integerValue строковое выражение значения
     * @return код ошибки
     */
    fun assembleIntegerBlock(integerName:String, integerValue:String):Int {
        val (newIntegerBlock, getError) = getIntegerBlock(integerName, integerValue)
        if(getError != SUCCESS.id) setRunResult(getError)

        valueVarBlock = integerValue
        newVarBlock = newIntegerBlock

        return setRunResult(SUCCESS.id)
    }





    // <-------------------- Присваивание массива -------------------->

    // Присваивание массива поэлементно
    private fun getIntegerArrayBlockByElements(arrayName: String, arrayElements: List<String>): Pair<IntegerArrayBlock, Int> {
        val newIntegerArrayBlock = IntegerArrayBlock(arrayName, mutableListOf<Int>())

        val name = arrayName.trim()
        val validateNameError = validateNameVariable(arrayName)
        if(validateNameError != SUCCESS.id) return Pair(newIntegerArrayBlock, validateNameError)

        val arrayBlock = context.getVar(arrayName) ?: return Pair(newIntegerArrayBlock, VARIABLE_IS_NOT_INITIALIZED.id)

        if(!(arrayBlock is IntegerArrayBlock)) return Pair(newIntegerArrayBlock, ASSIGNING_DIFFERENT_TYPES.id)

        if(arrayElements.size != arrayBlock.getValue().size) return Pair(newIntegerArrayBlock, INVALID_ARRAY_ACCESS.id)

        val value = mutableListOf<Int>()
        for(arrayElement in arrayElements) {
            val (arrayElementValue, calculationValueError) = calculationArithmeticExpression(arrayElement, context)
            if(calculationValueError != SUCCESS.id) return Pair(newIntegerArrayBlock, calculationValueError)

            value.add(arrayElementValue)
        }

        newIntegerArrayBlock.setName(name)
        newIntegerArrayBlock.setValue(value)

        return Pair(newIntegerArrayBlock, SUCCESS.id)
    }

    // Присваивание массива к другому массиву
    private fun getIntegerArrayBlockByArray(arrayName: String, anotherArrayName: String): Pair<IntegerArrayBlock, Int> {
        val newIntegerArrayBlock = IntegerArrayBlock("", mutableListOf<Int>())

        // первый массив
        val name = arrayName.trim()
        val validateNameError = validateNameVariable(name)
        if(validateNameError != SUCCESS.id) return Pair(newIntegerArrayBlock, validateNameError)
        val integerBlock = context.getVar(name) ?: return Pair(newIntegerArrayBlock, VARIABLE_IS_NOT_INITIALIZED.id)

        // второй массив
        val anotherName = anotherArrayName.trim()
        val validateAnotherNameError = validateNameVariable(anotherName)
        if(validateAnotherNameError != SUCCESS.id) return Pair(newIntegerArrayBlock, validateAnotherNameError)
        val anotherIntegerBlock = context.getVar(anotherName) ?: return Pair(newIntegerArrayBlock, VARIABLE_IS_NOT_INITIALIZED.id)

        if(!(integerBlock is IntegerArrayBlock) || !(anotherIntegerBlock is IntegerArrayBlock)) {
            return Pair(newIntegerArrayBlock, ASSIGNING_DIFFERENT_TYPES.id)
        }

        newIntegerArrayBlock.setName(anotherIntegerBlock.getName())
        newIntegerArrayBlock.setValue(anotherIntegerBlock.getValue())

        return Pair(newIntegerArrayBlock, SUCCESS.id)
    }


    /**
     * Присваивает новое значение массиву
     * @param arrayName имя массива
     * @param arrayValue строка значений(через запятую) || имя другого массива
     * @return код ошибки
     */
    fun assembleIntegerArrayBlock(arrayName:String, arrayValue: String): Int {
        removeBlock()

        val arrayElements = arrayValue.split(",").map { it.trim() }

        val newIntegerArrayBlock: IntegerArrayBlock
        if(arrayElements.isNotEmpty()) {
            if(arrayElements.size > 1) {
                val (resultingArray, getError) = getIntegerArrayBlockByElements(arrayName, arrayElements)
                if(getError != SUCCESS.id) return setRunResult(getError)
                newIntegerArrayBlock = resultingArray
            }
            else {
                val (resultingArray, getError) = getIntegerArrayBlockByArray(arrayName, arrayElements[0])
                if(getError != SUCCESS.id) return setRunResult(getError)
                newIntegerArrayBlock = resultingArray
            }
        }
        else return setRunResult(INVALID_ASSIGNMENT_ARRAY.id)

        newVarBlock = newIntegerArrayBlock
        valueVarBlock = arrayValue

        return setRunResult(SUCCESS.id)
    }





    // <-------------------- Присваивание элемента массива -------------------->

    private fun getIntegerArrayBlockByElement(arrayName: String, arrayIndex: String, arrayElementValue: String): Pair<IntegerArrayBlock, Int> {
        val newIntegerArrayBlock = IntegerArrayBlock("", mutableListOf<Int>())

        val name = arrayName.trim()
        val validateNameError = validateNameVariable(name)
        if(validateNameError != SUCCESS.id) return Pair(newIntegerArrayBlock, validateNameError)

        val integerArrayBlock = context.getVar(name) ?: return Pair(newIntegerArrayBlock, VARIABLE_IS_NOT_INITIALIZED.id)
        if(!(integerArrayBlock is IntegerArrayBlock)) return Pair(newIntegerArrayBlock, ASSIGNING_DIFFERENT_TYPES.id)

        val (index, calculationIndexError) = calculationArithmeticExpression(arrayIndex, context)
        if(calculationIndexError != SUCCESS.id) return Pair(newIntegerArrayBlock, calculationIndexError)
        else if((index < 0) || (index >= integerArrayBlock.getValue().size)) return Pair(newIntegerArrayBlock, INVALID_ARRAY_INDEX.id)

        val (elementValue, calculationElementValue) = calculationArithmeticExpression(arrayElementValue, context)
        if(calculationElementValue != SUCCESS.id) return Pair(newIntegerArrayBlock, calculationElementValue)

        val arrayValue = integerArrayBlock.getValue().toMutableList()
        arrayValue[index] = elementValue

        newIntegerArrayBlock.setName(name)
        newIntegerArrayBlock.setValue(arrayValue)

        return Pair(newIntegerArrayBlock, SUCCESS.id)
    }


    /**
     * Присваивает новое значение элементу массива
     * @param arrayName имя массива
     * @param arrayIndex индекс изменяемого элемента
     * @param arrayElementValue новое значение элемента
     * @return код ошибки
     */
    fun assembleElementIntegerArrayBlock(arrayName:String, arrayIndex:String, arrayElementValue:String): Int {
        removeBlock()

        val (newIntegerArrayBlock, getError) = getIntegerArrayBlockByElement(arrayName, arrayIndex, arrayElementValue)
        if(getError != SUCCESS.id) setRunResult(getError)

        newVarBlock = newIntegerArrayBlock.getCopy()
        index = arrayIndex
        valueVarBlock = arrayElementValue

        return setRunResult(SUCCESS.id)
    }

    override fun removeBlock() {
        runResult = RUNTIME_ERROR.id

        newVarBlock = IntegerBlock("NONE", 0)
        valueVarBlock = String()
        index = String()

        return
    }

    override fun run(): Int {
        if(runResult != SUCCESS.id) return runResult

        if(newVarBlock is IntegerBlock) assembleIntegerBlock(newVarBlock.getName(), valueVarBlock)
        else if(newVarBlock is IntegerArrayBlock) {
            if(index.isEmpty()) assembleIntegerArrayBlock(newVarBlock.getName(), valueVarBlock)
            else assembleElementIntegerArrayBlock(newVarBlock.getName(), index, valueVarBlock)
        }
        else return ASSIGNING_DIFFERENT_TYPES.id

        context.setVar(newVarBlock.getName(), newVarBlock)


        return SUCCESS.id
    }

}