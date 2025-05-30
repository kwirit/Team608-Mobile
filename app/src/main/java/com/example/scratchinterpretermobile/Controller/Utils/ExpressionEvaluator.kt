package com.example.scratchinterpretermobile.Controller.Utils


import com.example.scratchinterpretermobile.Controller.Error.ARRAY_EXPECTED
import com.example.scratchinterpretermobile.Controller.Error.ARRAY_NOT_FOUND
import com.example.scratchinterpretermobile.Controller.Error.DIVISION_BY_ZERO
import com.example.scratchinterpretermobile.Controller.Error.EMPTY_ARITHMETIC
import com.example.scratchinterpretermobile.Controller.Error.INVALID_ARRAY_ACCESS
import com.example.scratchinterpretermobile.Controller.Error.INVALID_BOOLEAN
import com.example.scratchinterpretermobile.Controller.Error.INVALID_CHARACTERS
import com.example.scratchinterpretermobile.Controller.Error.INVALID_VARIABLE_START
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Error.TYPE_MISMATCH
import com.example.scratchinterpretermobile.Controller.Error.UNMATCHED_PARENTHESES
import com.example.scratchinterpretermobile.Model.BooleanBlock
import com.example.scratchinterpretermobile.Model.Context
import com.example.scratchinterpretermobile.Model.Stack
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.StringBlock
import kotlin.collections.get

/**
 * Преобразует выражение из инфиксной формы в постфиксную нотацию.
 * @param elements список токенов исходного выражения
 * @return Pair<MutableList<String>, Int> - постфиксное выражение и код ошибки (0 - в случае успеха)
 */
fun transferArithmeticPrefixToPostfix(elements: MutableList<String>, context: Context): Pair<MutableList<String>, Int> {
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

                    validateNameVariable(element) == 0 && context.getVar(element) != null -> {

                        when (val value = context.getVar(element)) {
                            is IntegerBlock -> postfix.add(value.getValue().toString())
                            else -> return Pair(mutableListOf(), ARRAY_EXPECTED.id)
                        }
                    }

                    validateSyntaxArrayName(element) == 0 -> {
                        val (value, error) = processArrayAccess(element, context)
                        if (error != 0) return Pair(mutableListOf(), ARRAY_NOT_FOUND.id)
                        postfix.add(value.toString())
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
 * Вычисляет значение арифметического выражения в инфиксной нотации.
 * Например: "a % b + 4 * (10 - 3 + arr[i + 2])".
 * @param input строка с арифметическим выражением
 * @return Pair<Int, Int> - результат и код ошибки (0 - в случае успеха)
 */
fun calculationArithmeticExpression(input: String, context: Context): Pair<Int, Int> {
    val (elements, error) = transferArithmeticPrefixToPostfix(getElementFromString(input), context)
    if (error != 0) return Pair(-1, error)
    val (result, errorCalculation) = calculationPostfix(elements)
    if (errorCalculation != 0) return Pair(-1, errorCalculation)
    return Pair(result, 0)
}


/**
 * Преобразует инфиксное строковое выражение в постфиксную нотацию.
 *
 * @param elements список токенов исходного выражения
 * @return Pair<MutableList<Pair<String, String>>, Int> постфиксное выражение и код ошибки
 */
fun transferStringPrefixToPostfix(elements: MutableList<String>, context: Context): Pair<MutableList<Pair<String, String>>, Int>  {
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

                    validateNameVariable(element) == 0 && context.getVar(element) != null -> {
                        when (val value = context.getVar(element)) {
                            is IntegerBlock -> postfix.add(Pair(value.getValue().toString(), "Const"))
                            is StringBlock -> postfix.add(Pair(value.getValue(), "String"))
                            else -> return Pair(mutableListOf(), ARRAY_EXPECTED.id)
                        }
                    }
                    validateSyntaxArrayName(element) == 0 -> {
                        val (value, error) = processArrayAccess(element, context)
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
 * @param postfix Cписок токенов в постфиксной форме
 * @return Pair<String, Int> - Результат вычисления и код ошибки
 */
fun calculationStringPostfix(postfix: MutableList<Pair<String, String>>): Pair<String, Int> {

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
 * Вычисляет значение строкового выражения в инфиксной нотации.
 * Например: "\"Hello \" + name"
 *
 * @param input строка со строковым выражением
 * @return Pair<String, Int> результат и код ошибки
 */
fun calculationStringExpression(input: String, context: Context): Pair<String, Int> {
    val (elements, error) = transferStringPrefixToPostfix(getElementFromString(input.trim()), context)
    if (error != 0) return Pair("", error)
    val (result, errorCalculation) = calculationStringPostfix(elements)
    if (errorCalculation != 0) return Pair("", errorCalculation)
    return Pair(result, 0)
}

fun transferBooleanPrefixToPostfix(elements: MutableList<String>, context: Context): Pair<MutableList<Pair<String, String>>, Int>  {
    val postfix = mutableListOf<Pair<String, String>>()
    val stack = Stack<String>()
    val priority = mapOf(
        "%" to 4,
        "/" to 4,
        "*" to 4,

        "+" to 3,
        "-" to 3,

        "<" to 2,
        ">" to 2,
        "<=" to 2,
        ">=" to 2,
        "==" to 2,
        "!=" to 2,

        "&&" to 1,
        "||" to 1
    )
    val operators = "+-*%/>=<=!==&&||"

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
                    validateBoolean(element) == 0 -> postfix.add(Pair(element, "Boolean"))

                    validateNameVariable(element) == 0 && context.getVar(element) != null -> {
                        when (val value = context.getVar(element)) {
                            is IntegerBlock -> postfix.add(Pair(value.getValue().toString(), "Const"))
                            is BooleanBlock -> postfix.add(Pair(value.getValue().toString(), "Boolean"))
                            else -> return Pair(mutableListOf(), ARRAY_EXPECTED.id)
                        }
                    }
                    validateSyntaxArrayName(element) == 0 -> {
                        val (value, error) = processArrayAccess(element, context)
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
 * @param postfix Cписок токенов в постфиксной форме
 * @return Pair<String, Int> - Результат вычисления и код ошибки
 */
fun calculationBooleanPostfix(postfix: MutableList<Pair<String, String>>): Pair<Boolean, Int> {
    val stack = Stack<Pair<String, String>>()

    val comparisonOperators = setOf(">", "<", ">=", "<=", "!=", "==")
    val logicalOperators = setOf("&&", "||")

    for (element in postfix) {
        val (value, typeValue) = element

        if (typeValue == "Operator") {
            val firstOperand = stack.pop() ?: return Pair(false, INVALID_ARRAY_ACCESS.id)
            val secondOperand = stack.pop() ?: return Pair(false, INVALID_ARRAY_ACCESS.id)

            val (firstValue, firstType) = firstOperand
            val (secondValue, secondType) = secondOperand

            when {
                firstType == "Const" && secondType == "Const" -> {
                    val a = firstValue.toIntOrNull() ?: return Pair(false, INVALID_CHARACTERS.id)
                    val b = secondValue.toIntOrNull() ?: return Pair(false, INVALID_CHARACTERS.id)

                    when (value) {
                        "+" -> stack.push(Pair((b + a).toString(), "Const"))
                        "-" -> stack.push(Pair((b - a).toString(), "Const"))
                        "*" -> stack.push(Pair((b * a).toString(), "Const"))
                        "/" -> {
                            if (a != 0) stack.push(Pair((b / a).toString(), "Const"))
                            else return Pair(false, DIVISION_BY_ZERO.id)
                        }
                        "%" -> {
                            if (a != 0) stack.push(Pair((b % a).toString(), "Const"))
                            else return Pair(false, DIVISION_BY_ZERO.id)
                        }
                        in comparisonOperators -> {
                            val result = when (value) {
                                ">" -> b > a
                                "<" -> b < a
                                ">=" -> b >= a
                                "<=" -> b <= a
                                "==" -> b == a
                                "!=" -> b != a
                                else -> return Pair(false, INVALID_CHARACTERS.id)
                            }
                            stack.push(Pair(result.toString(), "Boolean"))
                        }
                        else -> return Pair(false, INVALID_CHARACTERS.id)
                    }
                }

                firstType == "Boolean" && secondType == "Boolean" && value in logicalOperators -> {
                    val a = firstValue.toBooleanStrictOrNull() ?: return Pair(false, INVALID_BOOLEAN.id)
                    val b = secondValue.toBooleanStrictOrNull() ?: return Pair(false, INVALID_BOOLEAN.id)

                    val result = when (value) {
                        "&&" -> b && a
                        "||" -> b || a
                        else -> return Pair(false, INVALID_CHARACTERS.id)
                    }
                    stack.push(Pair(result.toString(), "Boolean"))
                }

                else -> return Pair(false, TYPE_MISMATCH.id)
            }

        } else {
            stack.push(element)
        }
    }

    if (stack.isEmpty()) return Pair(false, EMPTY_ARITHMETIC.id)

    val result = stack.pop()

    return if (result!!.second == "Boolean") {
        val booleanResult = result.first.toBooleanStrictOrNull()
            ?: return Pair(false, INVALID_BOOLEAN.id)
        Pair(booleanResult, SUCCESS.id)
    } else {
        Pair(false, TYPE_MISMATCH.id)
    }
}

/**
 * Вычисляет значение строкового выражения в инфиксной нотации.
 * Например: "\"Hello \" + name"
 *
 * @param input строка со строковым выражением
 * @return Pair<String, Int> результат и код ошибки
 */
fun calculationBooleanExpression(input: String, context: Context): Pair<Boolean, Int> {
    val (elements, error) = transferBooleanPrefixToPostfix(getElementFromString(input.trim()), context)
    if (error != 0) return Pair(false, error)
    val (result, errorCalculation) = calculationBooleanPostfix(elements)
    if (errorCalculation != 0) return Pair(false, errorCalculation)
    return Pair(result, 0)
}
