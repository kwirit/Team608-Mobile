package com.example.scratchinterpretermobile.Controller.Utils

import com.example.scratchinterpretermobile.Controller.Error.ARRAY_BOUNDS_ERROR
import com.example.scratchinterpretermobile.Controller.Error.ARRAY_EXPECTED
import com.example.scratchinterpretermobile.Controller.Error.ARRAY_NOT_FOUND
import com.example.scratchinterpretermobile.Controller.Error.EMPTY_NAME
import com.example.scratchinterpretermobile.Controller.Error.INCORRECT_ARRAY_ELEMENT_NAME
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_ACCESS
import com.example.scratchinterpretermobile.Controller.Error.INVALID_CHARACTERS
import com.example.scratchinterpretermobile.Controller.Error.INVALID_CHARACTERS_IN_STRING
import com.example.scratchinterpretermobile.Controller.Error.INVALID_FORMAT
import com.example.scratchinterpretermobile.Controller.Error.INVALID_VARIABLE_START
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Error.UNMATCHED_PARENTHESES
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_HAS_SPACE
import com.example.scratchinterpretermobile.Model.Context
import com.example.scratchinterpretermobile.Model.Stack
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.UIContext

/**
 * Проверяет корректность имени переменной.
 *
 * @param input строка, которую проверяем как имя переменной
 * @return Int - код ошибки (0 - если имя корректно)
 */
fun validateNameVariable(input: String): Int {
    val nameVariable = input.trim()

    if (nameVariable.isEmpty()) {
        return EMPTY_NAME.id
    }

    if (" " in nameVariable) {
        return VARIABLE_HAS_SPACE.id
    }

    val regex = Regex("^[a-zA-Z_][a-zA-Z0-9_]*$")

    if (!regex.matches(nameVariable)) {
        val firstChar = nameVariable.first()
        if (!firstChar.isLetter() && firstChar != '_') {
            return INVALID_VARIABLE_START.id
        }
        return INVALID_CHARACTERS.id
    }

    return 0
}


fun validateAllBrackets(input: String): Int {
    val stack = Stack<Char>()
    val matchingBrackets = mapOf(
        ')' to '(',
        ']' to '['
    )

    for (char in input) {
        when (char) {
            '(', '[' -> stack.push(char)
            ')', ']' -> {
                if (stack.isEmpty()) return UNMATCHED_PARENTHESES.id
                val top = stack.pop()
                if (matchingBrackets[char] != top) return UNMATCHED_PARENTHESES.id
            }
        }
    }

    if (!stack.isEmpty()) return UNMATCHED_PARENTHESES.id
    return 0
}

/**
 * Проверяет, синтаксическую корректность элемента массива
 * @param input строка, которую проверяем
 * @return Int - код ошибки (0 - в случае успеха)
 */
fun validateSyntaxArrayName(input: String): Int {
    val element = input.trim()
    if (element.isEmpty()) return EMPTY_NAME.id

    val openIndex = element.indexOf('[')
    val closeIndex = element.lastIndexOf(']')
    if (openIndex == -1 || closeIndex == -1 || openIndex > closeIndex) return UNMATCHED_PARENTHESES.id

    val arrayName = element.substring(0, openIndex).trim()
    val indexExpr = element.substring(openIndex + 1, closeIndex).trim()

    if (validateNameVariable(arrayName) != SUCCESS.id) {
        return validateNameVariable(arrayName)
    }
    if (validateAllBrackets(indexExpr) != SUCCESS.id){
        return UNMATCHED_PARENTHESES.id
    }

    return SUCCESS.id
}

/**
 * Проверяет, является ли строка целочисленной константой.
 *
 * @param input строка для проверки
 * @return Int код ошибки (0 — если число корректно)
 */
fun validateConst(input: String): Int {
    var regex =  Regex("""\d+""")
    if (regex.matches(input.trim())) return 0
    else return INVALID_VARIABLE_START.id
}

/**
 * Проверяет, является ли строка корректной строкой в двойных кавычках.
 *
 * @param input строка для проверки
 * @return Int код ошибки (0 — если строка корректна)
 */
fun validateString(input: String): Int {
    val trimmedInput = input.trim()
    if (trimmedInput.isEmpty()) return EMPTY_NAME.id
    if (!trimmedInput.startsWith("\"") || !trimmedInput.endsWith("\"")) {
        return INVALID_FORMAT.id
    }

    val contentInsideQuotes = trimmedInput.drop(1).dropLast(1)

    val regex = """[^"]*""".toRegex()
    if (regex.matches(contentInsideQuotes)) {
        return 0
    }

    return INVALID_CHARACTERS_IN_STRING.id
}

/**
 * Обрабатывает обращение к элементу массива и возвращает его значение и возможную ошибку.
 *
 * Например: "arr[2]" → значение 3, если arr = [1, 2, 3]
 *
 * @param element строка с обращением к элементу массива
 * @return Pair<Int, Int> пара: значение элемента и код ошибки
 */
fun processArrayAccess(input: String, context: Context = UIContext): Pair<Int, Int> {
    val element = input.trim()
    if (input.isEmpty()) return Pair(-1, EMPTY_NAME.id)

    val openIndex = input.indexOf('[')
    val closeIndex = input.lastIndexOf(']')
    if (openIndex == -1 || closeIndex == -1 || openIndex > closeIndex) return Pair(-1, UNMATCHED_PARENTHESES.id)

    val arrayName = element.substring(0, openIndex).trim()
    val indexExpr = element.substring(openIndex + 1, closeIndex).trim()

    if (validateNameVariable(arrayName) != 0) {
        return Pair(-1, validateNameVariable(arrayName))
    }

    val (indexValue, indexError) = calculationArithmeticExpression(indexExpr)
    if (indexError != SUCCESS.id) {
        return Pair(-1, indexError)
    }

    val arrayBlock = context.getVar(arrayName) ?: return Pair(-1, ARRAY_NOT_FOUND.id)

    if (arrayBlock !is IntegerArrayBlock) return Pair(-1, ARRAY_EXPECTED.id)

    val array = arrayBlock.getValue()

    if (indexValue < 0 || indexValue >= array.size) return Pair(-1, ARRAY_BOUNDS_ERROR.id)

    return Pair(array[indexValue], SUCCESS.id)
}