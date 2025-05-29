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
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_HAS_SPACE
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

/**
 * Проверяет, синтаксическую корректность элемента массива
 * @param input строка, которую проверяем
 * @return Int - код ошибки (0 - в случае успеха)
 */
fun validateSyntaxArrayName(input: String): Int {
    if (input.trim().isEmpty()) return EMPTY_NAME.id
    val regex = Regex(
        """([a-zA-Z_]\w*)\[\s*([-+*\/%]?\s*(?:[a-zA-Z_]\w*|\d+|\([^()\r\n]*\))\s*(?:[-+*\/%]\s*(?:[a-zA-Z_]\w*|\d+|\([^()\r\n]*\))\s*)*)?\]"""
    )
    if (!regex.containsMatchIn(input.trim()))
        return INCORRECT_ARRAY_ELEMENT_NAME.id
    return 0
}

/**
 * Проверяет, является ли строка корректным именем массива с индексом.
 *
 * @param input строка, содержащая потенциальное обращение к элементу массива
 * @return Int - код ошибки (0 - если строка корректна)
 */
fun validateArrayName(input: String): Int {
    return processArrayAccess(input).second
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
fun processArrayAccess(element: String): Pair<Int, Int> {
    val regexName = Regex("^([a-zA-Z_][a-zA-Z0-9_]*)")
    val regexIndex = Regex("\\[(.*?)\\]")

    val matchName = regexName.find(element) ?: return Pair(-1, INVALID_ARRAY_ACCESS.id)
    val matchIndex = regexIndex.find(element) ?:return Pair(-1, INVALID_ARRAY_ACCESS.id)

    val arrayName = matchName.groups[1]?.value ?: return Pair(-1, INVALID_ARRAY_ACCESS.id)
    val indexExpr = matchIndex.groups[1]?.value ?: return Pair(-1, INVALID_ARRAY_ACCESS.id)

    if (validateNameVariable(arrayName) != 0) {
        return Pair(-1, validateNameVariable(arrayName))
    }

    val (indexValue, indexError) = calculationArithmeticExpression(indexExpr)
    if (indexError != 0) {
        return Pair(-1, indexError)
    }

    val arrayBlock = UIContext.getVar(arrayName) ?: return Pair(-1, ARRAY_NOT_FOUND.id)

    if (arrayBlock !is IntegerArrayBlock) return Pair(-1, ARRAY_EXPECTED.id)

    val array = arrayBlock.getValue()

    if (indexValue < 0 || indexValue >= array.size) return Pair(-1, ARRAY_BOUNDS_ERROR.id)

    return Pair(array[indexValue], 0)
}