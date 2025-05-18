package com.example.scratchinterpretermobile.Controller


import com.example.scratchinterpretermobile.Controller.Error.ARRAY_BOUNDS_ERROR
import com.example.scratchinterpretermobile.Controller.Error.ARRAY_EXPECTED
import com.example.scratchinterpretermobile.Controller.Error.ARRAY_INVALID_ELEMENT
import com.example.scratchinterpretermobile.Controller.Error.ARRAY_NOT_FOUND
import com.example.scratchinterpretermobile.Controller.Error.DIVISION_BY_ZERO
import com.example.scratchinterpretermobile.Controller.Error.EMPTY_NAME
import com.example.scratchinterpretermobile.Controller.Error.INCORRECT_ARRAY_ELEMENT_NAME
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_ACCESS
import com.example.scratchinterpretermobile.Controller.Error.INVALID_CHARACTERS
import com.example.scratchinterpretermobile.Controller.Error.INVALID_VARIABLE_START
import com.example.scratchinterpretermobile.Controller.Error.UNMATCHED_PARENTHESES
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_HAS_SPACE
import com.example.scratchinterpretermobile.Model.Stack
import com.example.scratchinterpretermobile.Model.mainContext
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock



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
 * Проверяет, является ли строка корректным именем массива с индексом.
 *
 * @param input строка, содержащая потенциальное обращение к элементу массива
 * @return Int - код ошибки (0 - если строка корректна)
 */
fun validateArrayName(input: String): Int {
    return processArrayAccess(input)
}

fun validateConst(input: String): Int {
    var regex =  Regex("""\d+""")
    if (regex.matches(input.trim())) return 0
    else return INVALID_VARIABLE_START.id
}

fun processArrayAccess(element: String): Int {
    val regexName = Regex("^([a-zA-Z_][a-zA-Z0-9_]*)")
    val regexIndex = Regex("\\[(.*?)\\]")

    val matchName = regexName.find(element) ?: return INVALID_ARRAY_ACCESS.id
    val matchIndex = regexIndex.find(element) ?:return INVALID_ARRAY_ACCESS.id

    val arrayName = matchName.groups[1]?.value ?: return INVALID_ARRAY_ACCESS.id
    val indexExpr = matchIndex.groups[1]?.value ?: return INVALID_ARRAY_ACCESS.id

    if (validateNameVariable(arrayName) != 0) {
        return validateNameVariable(arrayName)
    }

    val (indexValue, indexError) = calculationArithmeticExpression(indexExpr)
    if (indexError != 0) {
        return indexError
    }

    val arrayBlock = mainContext.getVar(arrayName) ?: return ARRAY_NOT_FOUND.id

    if (arrayBlock !is IntegerArrayBlock) return ARRAY_EXPECTED.id

    val array = arrayBlock.value as? List<Int> ?: return ARRAY_INVALID_ELEMENT.id

    if (indexValue < 0 || indexValue >= array.size) return ARRAY_BOUNDS_ERROR.id

    return 0
}

fun getElementFromString(input: String): MutableList<String> {
    val trimmedInput = input.trim()

    val elements = mutableListOf<String>()

    val operators = "+-*%/()"

    var currentToken = StringBuilder()

    var flagArray = false
    for (symbol in trimmedInput) {
        if (symbol == ' ') continue

        if (symbol == '[') flagArray = true;
        if (symbol == ']') {
            flagArray = false;
            currentToken.append(symbol)
            elements.add(currentToken.toString())
            currentToken.clear()
            continue
        }

        if (flagArray) {
            currentToken.append(symbol)
            continue;
        }

        if (currentToken.isNotEmpty() && (symbol.isDigit() || symbol.isLetter() || symbol == '_')) {
            currentToken.append(symbol)
        } else if (currentToken.isEmpty() && (symbol.isDigit() || symbol.isLetter() || symbol == '_')) {
            currentToken = StringBuilder().append(symbol)
        } else if (symbol in operators) {
            if (currentToken.isNotEmpty()) {
                elements.add(currentToken.toString())
                currentToken.clear()
            }
            elements.add(symbol.toString())
        }
    }

    if (currentToken.isNotEmpty()) {
        elements.add(currentToken.toString())
    }

    return elements
}

/**
 * Преобразует выражение из инфиксной формы в постфиксную нотацию.
 * @param elements список токенов исходного выражения
 * @return Pair<MutableList<String>, Int> - постфиксное выражение и код ошибки (0 - в случае успеха)
 */
fun transferPrefixToPostfix(elements: MutableList<String>): Pair<MutableList<String>, Int> {
    val postfix = mutableListOf<String>()
    val stack = Stack<String>()
    val priority = mapOf(
        "%" to 2,
        "/" to 2,
        "*" to 2,
        "+" to 1,
        "-" to 1,
        "(" to 0
    )
    val operators = "+-*%/"

    for (element in elements) {
        when (element) {
            in operators -> {
                while (!stack.isEmpty() && stack.peek() != "(" && priority[element]!! <= priority[stack.peek()]!!) {
                    postfix.add(stack.pop()!!)
                }
                stack.push(element)
            }

            "(" -> stack.push(element)

            ")" -> {
                while (!stack.isEmpty() && stack.peek() != "(") {
                    postfix.add(stack.pop()!!)
                }
                if (stack.isEmpty()) return Pair(mutableListOf(), UNMATCHED_PARENTHESES.id)
                stack.pop()
            }

            else -> {
                when {
                    validateConst(element) == 0 -> postfix.add(element)

                    validateNameVariable(element) == 0 && mainContext.getVar(element) != null -> {
                        val value = mainContext.getVar(element)
                        if (value is IntegerBlock) {
                            postfix.add(value.value.toString())
                        }
                        else {
                            return Pair(mutableListOf(), ARRAY_EXPECTED.id)
                        }
                    }

                    validateArrayName(element) == 0 -> {
                        val error = processArrayAccess(element)
                        if (error == 0) {
                            postfix.add(element)
                        }
                        if (error != 0) return Pair(mutableListOf(), ARRAY_NOT_FOUND.id)
                    }
                    else -> return Pair(mutableListOf(), INVALID_VARIABLE_START.id)
                }
            }
        }
    }

    while (!stack.isEmpty()) {
        if (stack.peek() == "(") return Pair(mutableListOf(), UNMATCHED_PARENTHESES.id)
        postfix.add(stack.pop()!!)
    }

    return Pair(postfix, 0)
}

/**
 * Выполняет вычисление выражения в постфиксной (обратной польской) нотации.
 *
 * @param postfix список токенов в постфиксной форме
 * @return Pair<Int, Int> - результат вычисления и код ошибки (0 - в случае успеха)
 */
fun calculationPostfix(postfix: MutableList<String>): Pair<Int, Int> {
    val stack = Stack<Int>()

    for (element in postfix) {
        if (element in "+-*%/") {
            val first = stack.pop() ?: return Pair(-1, INVALID_ARRAY_ACCESS.id)
            val second = stack.pop() ?: return Pair(-1, INVALID_ARRAY_ACCESS.id)
            if ((element == "/" || element == "%") && first == 0){
                return Pair(-1, DIVISION_BY_ZERO.id)
            }
            val result = when (element) {
                "-" -> second - first
                "+" -> second + first
                "*" -> second * first
                "%" -> second % first
                "/" -> second / first
                else -> -1
            }
            stack.push(result)

        }
        else {
            val value = element.toIntOrNull()
            stack.push(value!!)
        }
    }
    return Pair(stack.pop()!!, 0);
}


/**
 * Вычисляет значение арифметического выражения в инфиксной нотации.
 * Например: "a % b + 4 * (10 - 3 + arr[i + 2])".
 * @param input строка с арифметическим выражением
 * @return Pair<Int, Int> - результат и код ошибки (0 - в случае успеха)
 */
fun calculationArithmeticExpression(input: String): Pair<Int, Int> {
    val (elements, error) = transferPrefixToPostfix(getElementFromString(input))
    if (error != 0) return Pair(-1, error)
    val (result, errorCalculation) = calculationPostfix(elements)
    if (errorCalculation != 0) return Pair(-1, errorCalculation)
    return Pair(result, 0)
}
