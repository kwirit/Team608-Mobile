package com.example.scratchinterpretermobile.Controller


import com.example.scratchinterpretermobile.Controller.Error.ARRAY_BOUNDS_ERROR
import com.example.scratchinterpretermobile.Controller.Error.ARRAY_EXPECTED
import com.example.scratchinterpretermobile.Controller.Error.ARRAY_NOT_FOUND
import com.example.scratchinterpretermobile.Controller.Error.DIVISION_BY_ZERO
import com.example.scratchinterpretermobile.Controller.Error.EMPTY_ARITHMETIC
import com.example.scratchinterpretermobile.Controller.Error.EMPTY_NAME
import com.example.scratchinterpretermobile.Controller.Error.INCORRECT_ARRAY_ELEMENT_NAME
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_ACCESS
import com.example.scratchinterpretermobile.Controller.Error.INVALID_CHARACTERS
import com.example.scratchinterpretermobile.Controller.Error.INVALID_CHARACTERS_IN_STRING
import com.example.scratchinterpretermobile.Controller.Error.INVALID_FORMAT
import com.example.scratchinterpretermobile.Controller.Error.INVALID_VARIABLE_START
import com.example.scratchinterpretermobile.Controller.Error.UNMATCHED_PARENTHESES
import com.example.scratchinterpretermobile.Controller.Error.VARIABLE_HAS_SPACE
import com.example.scratchinterpretermobile.Model.Stack
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.StringBlock

/**
 * Разделяет строку на элементы по указанному разделителю, игнорируя разделители внутри строк (в кавычках).
 *
 * @param input исходная строка для разбиения
 * @param separator символ-разделитель (по умолчанию — запятая)
 * @return MutableList<String> список обработанных и разбитых элементов
 */
fun parserSplit(input: String, separator: Char = ',') : MutableList<String> {
    val trimmedInput = input.trim()

    val elements = mutableListOf<String>()

    var currentToken = StringBuilder()

    var flagString = false

    for (symbol in trimmedInput) {
        if (symbol == separator && !flagString) {
            elements.add(currentToken.toString().trim())
            currentToken.clear()
            continue
        }
        if (symbol == '\"') flagString = !flagString
        currentToken.append(symbol)
    }

    if (currentToken.toString().trim().isNotEmpty()) {
        elements.add(currentToken.toString())
    }

    return elements
}

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

/**
 * Разбивает арифметическое/строковое выражение на токены, сохраняя структуру.
 * Учитывает строки, операторы и доступ к массивам.
 *
 * @param input исходное выражение
 * @return MutableList<String> список токенов
 */
fun getElementFromString(
    input: String
): MutableList<String> {
    val trimmedInput = input.trim()

    var operators = "+-*%/()"
    val elements = mutableListOf<String>()

    var currentToken = StringBuilder()

    var flagArray = false
    var flagString = false

    for (symbol in trimmedInput) {
        if (symbol == ' ' && !flagString) continue
        if (symbol == '\"') flagString = !flagString;


        if (symbol == '[') flagArray = true
        if (symbol == ']') {
            flagArray = false;
            currentToken.append(symbol)
            elements.add(currentToken.toString())
            currentToken.clear()
            continue
        }

        if (flagArray || flagString) {
            currentToken.append(symbol)
            continue;
        }

        if (symbol in operators) {
            if (currentToken.isNotEmpty()) {
                elements.add(currentToken.toString())
                currentToken.clear()
            }
            elements.add(symbol.toString())
        } else if (currentToken.isNotEmpty()) {
            currentToken.append(symbol)
        } else {
            currentToken = StringBuilder().append(symbol)
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

                    validateNameVariable(element) == 0 && UIContext.getVar(element) != null -> {

                        when (val value = UIContext.getVar(element)) {
                            is IntegerBlock -> postfix.add(value.getValue().toString())
                            is StringBlock -> postfix.add(value.getValue())
                            else -> return Pair(mutableListOf(), ARRAY_EXPECTED.id)
                        }
                    }

                    validateSyntaxArrayName(element) == 0 -> {
                        val (value, error) = processArrayAccess(element)
                        if (error != 0) return Pair(mutableListOf(), ARRAY_NOT_FOUND.id)
                        postfix.add(value.toString())
                    }

                    validateString(element) == 0 -> {}
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
fun calculationPostfix(
    postfix: MutableList<String>,
    ): Pair<Int, Int> {

    val stack = Stack<Int>()
    val operators = "+-*%/()"

    for (element in postfix) {
        if (element in operators) {
            val first = stack.pop() ?: return Pair(-1, INVALID_ARRAY_ACCESS.id)
            val second = stack.pop() ?: return Pair(-1, INVALID_ARRAY_ACCESS.id)
            if ((element == "/" || element == "%") && element in operators && first == 0){
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

    if(stack.isEmpty()) return Pair(-1, EMPTY_ARITHMETIC.id)

    return Pair(stack.pop()!!, 0);
}

/**
 * Преобразует инфиксное строковое выражение в постфиксную нотацию.
 *
 * @param elements список токенов исходного выражения
 * @return Pair<MutableList<Pair<String, String>>, Int> постфиксное выражение и код ошибки
 */
fun transferStringPrefixToPostfix(elements: MutableList<String>): Pair<MutableList<Pair<String, String>>, Int> {
    val postfix = mutableListOf<Pair<String, String>>()
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
                    postfix.add(Pair(stack.pop()!!, "Operator"))
                }
                stack.push(element)
            }

            "(" -> stack.push(element)

            ")" -> {
                while (!stack.isEmpty() && stack.peek() != "(") {
                    postfix.add(Pair(stack.pop()!!, "Operator"))
                }
                if (stack.isEmpty()) return Pair(mutableListOf(), UNMATCHED_PARENTHESES.id)
                stack.pop()
            }

            else -> {
                when {
                    validateConst(element) == 0 -> postfix.add(Pair(element, "Const"))
                    validateString(element) == 0 -> postfix.add(Pair(element.substring(1, element.length - 1), "String"))

                    validateNameVariable(element) == 0 && UIContext.getVar(element) != null -> {
                        when (val value = UIContext.getVar(element)) {
                            is IntegerBlock -> postfix.add(Pair(value.getValue().toString(), "Const"))
                            is StringBlock -> postfix.add(Pair(value.getValue(), "String"))
                            else -> return Pair(mutableListOf(), ARRAY_EXPECTED.id)
                        }
                    }
                    validateSyntaxArrayName(element) == 0 -> {
                        val (value, error) = processArrayAccess(element)
                        if (error != 0) return Pair(mutableListOf(), ARRAY_NOT_FOUND.id)

                        postfix.add(Pair(value.toString(), "Const"))
                    }

                    else -> return Pair(mutableListOf(), INVALID_VARIABLE_START.id)
                }
            }
        }
    }

    while (!stack.isEmpty()) {
        if (stack.peek() == "(") return Pair(mutableListOf(), UNMATCHED_PARENTHESES.id)
        postfix.add(Pair(stack.pop()!!, "Operator"))
    }

    return Pair(postfix, 0)
}

/**
 * Вычисляет значение строкового выражения в постфиксной нотации.
 *
 * Поддерживает операции над строками и числами, такие как конкатенация и повторение.
 *
 * @param postfix список токенов в постфиксной форме
 * @return Pair<String, Int> результат вычисления и код ошибки
 */
fun calculationStringPostfix(
    postfix: MutableList<Pair<String, String>>
): Pair<String, Int> {

    val stack = Stack<Pair<String, String>>()

    for (element in postfix) {
        val (value, typeValue) = element

        if (typeValue == "Operator") {
            val firstOperand = stack.pop() ?: return Pair("", INVALID_ARRAY_ACCESS.id)
            val secondOperand = stack.pop() ?: return Pair("", INVALID_ARRAY_ACCESS.id)

            val (firstValue, firstType) = firstOperand
            val (secondValue, secondType) = secondOperand
            when {
            firstType == "Const" && secondType == "String" && value == "*" ->  {
                val intValue = firstValue.toIntOrNull() ?: return Pair("", INVALID_VARIABLE_START.id)
                val result = secondValue.repeat(intValue)
                stack.push(Pair(result.toString(), "String"))
            }
            firstType == "String" && secondType == "Const" && value == "*" -> {
                val intValue = secondValue.toIntOrNull() ?: return Pair("", INVALID_VARIABLE_START.id)
                val result = firstValue.repeat(intValue)
                stack.push(Pair(result, "String"))
            }
            firstType == "Const" && secondType == "Const" -> {
                val a = firstValue.toIntOrNull() ?: return Pair("", INVALID_VARIABLE_START.id)
                val b = secondValue.toIntOrNull() ?: return Pair("", INVALID_VARIABLE_START.id)

                val result = when (value) {
                    "+" -> b + a
                    "-" -> b - a
                    "*" -> b * a
                    "/" -> if (a != 0) b / a else return Pair("", DIVISION_BY_ZERO.id)
                    "%" -> if (a != 0) b % a else return Pair("", DIVISION_BY_ZERO.id)
                    else -> return Pair("", INVALID_CHARACTERS.id)
                }

                stack.push(Pair(result.toString(), "Const"))
            }
            firstType == "String" && secondType == "String" && value == "+" -> {
                val result = secondValue + firstValue
                stack.push(Pair(result, "String"))
            }

            else -> return Pair("", INVALID_CHARACTERS.id)
            }


        }
        else {
            stack.push(Pair(value, typeValue))
        }
    }

    if(stack.isEmpty()) return Pair("", EMPTY_ARITHMETIC.id)

    return Pair(stack.pop()!!.first, 0)
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

/**
 * Вычисляет значение строкового выражения в инфиксной нотации.
 * Например: "\"Hello \" + name"
 *
 * @param input строка со строковым выражением
 * @return Pair<String, Int> результат и код ошибки
 */
fun calculationStringExpression(input: String): Pair<String, Int> {
    val (elements, error) = transferStringPrefixToPostfix(getElementFromString(input.trim()))
    if (error != 0) return Pair("", error)
    val (result, errorCalculation) = calculationStringPostfix(elements)
    if (errorCalculation != 0) return Pair("", errorCalculation)
    return Pair(result, 0)
}
