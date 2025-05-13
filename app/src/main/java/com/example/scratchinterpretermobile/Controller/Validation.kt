package com.example.scratchinterpretermobile.Controller


import com.example.scratchinterpretermobile.Controller.Error.Error
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.Stack
import com.example.scratchinterpretermobile.Model.VarBlock
import com.example.scratchinterpretermobile.Model.Context
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock

fun validateNameVariable(input: String): Int {
    val nameVariable = input.trim()

    if (nameVariable.isEmpty()) {
        return 104
    }

    if (" " in nameVariable) {
        return 102
    }

    val regex = Regex("^[a-zA-Z_][a-zA-Z0-9_]*$")

    if (!regex.matches(nameVariable)) {
        val firstChar = nameVariable.first()
        if (!firstChar.isLetter() && firstChar != '_') {
            return 101
        }
        return 103
    }

    return 0
}
fun validateArrayName(input: String): Int {
    val nameVariable = input.trim()
    val patterns = mapOf(
        "const" to Regex("\\d+").pattern,
        "value" to Regex("[1-9]\\d*").pattern,
        "var" to Regex("[a-zA-Z]\\w*").pattern,
        "spaces" to Regex("\\s*").pattern
    )

    val simpleArray = """
    ${patterns["var"]}$$
    (${patterns["var"]}|${patterns["const"]})
    $$
    """.trim().replace(Regex("\\s+"), "")

    val arithmeticInArray = """
    ( 
      (${simpleArray}|${patterns["var"]}|${patterns["const"]})${patterns["spaces"]}
      (
        ${patterns["spaces"]}[-+*/]${patterns["spaces"]}
        (${simpleArray}|${patterns["var"]}|${patterns["const"]})
      )*
    )
    """.trim().replace(Regex("\\s+"), "")

    val array = """
    ( 
      ${patterns["var"]}$$
      ( ${arithmeticInArray} | ${patterns["var"]} | ${patterns["const"]} | 
        ${patterns["var"]}$$(${patterns["var"]} | ${patterns["const"]})\$$ 
      )
      $$
    )
    """.trim().replace(Regex("\\s+"), "")

    return when {
        Regex("^$simpleArray\$").matches(nameVariable) -> 0
        Regex("^$array\$").matches(nameVariable) -> 0
        else -> 105
    }
}



fun getElementArray(input: String): Pair<Int, Int> {
    val trimmedInput = input.trim()

    if (validateArrayName(trimmedInput) != 0) {
        return Pair(-1, 105)
    }

    val arrayNameRegex = Regex("^([a-zA-Z_]\\w*)\$$[^$$]*\$$")
    val arrayNameMatch = arrayNameRegex.find(trimmedInput) ?: return Pair(-1, 105)
    val arrayName = arrayNameMatch.groupValues[1]

    val indexRegex = Regex("$$$([^$$]+)\$$")
    val indexMatch = indexRegex.find(trimmedInput) ?: return Pair(-1, 107) // Ошибка: не найден индекс
    val indexExpr = indexMatch.groupValues[1]

    val (indexValue, indexError) = calculationArithmeticExpression(indexExpr)
    if (indexError != 0) {
        return Pair(-1, indexError)
    }

    val varBlock = Context.getVar(arrayName) ?: return Pair(-1, 108) // Ошибка: переменная не найдена

    return when (varBlock) {
        is IntegerArrayBlock -> {
            val array = varBlock.value as? List<Int>
                ?: return Pair(-1, 111) // Ошибка: значение не является списком Int

            if (indexValue in array.indices) {
                Pair(array[indexValue], 0)
            } else {
                Pair(-1, 109) // Ошибка: выход за границы массива
            }
        }

        is IntegerBlock -> {
            Pair(-1, 110) // Ошибка: ожидался массив, а найдено число
        }

        else -> {
            Pair(-1, 111) // Ошибка: неизвестный тип переменной
        }
    }
}

fun getElementFromString(input: String): MutableList<String> {
    val trimmedInput = "0" + input.trim()

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

        if (flagArray) currentToken.append(symbol);

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
        when {
            element in operators -> {
                while (!stack.isEmpty() && stack.peek() != "(" && priority[element]!! <= priority[stack.peek()]!!) {
                    postfix.add(stack.pop()!!)
                }
                stack.push(element)
            }
            element == "(" -> stack.push(element)

            element == ")" -> {
                while (!stack.isEmpty() && stack.peek() != "(") {
                    postfix.add(stack.pop()!!)
                }
                if (stack.isEmpty()) return Pair(mutableListOf(), 106)
                stack.pop()
            }

            else -> {
                if (validateArrayName(element) == 0) {
                    val arrayNameRegex = Regex("^([a-zA-Z_]\\w*)\$$([^$$]+)\$$")
                    val match = arrayNameRegex.find(element) ?: return Pair(mutableListOf(), 107)

                    val arrayName = match.groupValues[1]
                    val indexExpr = match.groupValues[2]

                    val (indexValue, indexError) = calculationArithmeticExpression(indexExpr)
                    if (indexError != 0) return Pair(mutableListOf(), indexError)

                    val varBlock = Context.getVar(arrayName) ?: return Pair(mutableListOf(), 108)
                    if (varBlock !is IntegerArrayBlock) return Pair(mutableListOf(), 110)

                    val array = varBlock.value as? List<Int> ?: return Pair(mutableListOf(), 111)
                    if (indexValue !in array.indices) return Pair(mutableListOf(), 109)

                    postfix.add(array[indexValue].toString())
                } else {
                    val value = element.toIntOrNull()
                    if (value != null) {
                        postfix.add(value.toString())
                    } else {
                        val error = validateNameVariable(element)
                        if (error != 0) return Pair(mutableListOf(), error)

                        val varBlock = Context.getVar(element) ?: return Pair(mutableListOf(), 108)
                        if (varBlock !is IntegerBlock) return Pair(mutableListOf(), 110)

                        postfix.add((varBlock.value as Int).toString())
                    }
                }
            }
        }
    }

    while (!stack.isEmpty()) {
        if (stack.peek() == "(") return Pair(mutableListOf(), 106)
        postfix.add(stack.pop()!!)
    }

    return Pair(postfix, 0)
}

fun calculationPostfix(postfix: MutableList<String>): Int {
    val stack = Stack<Int>()

    for (element in postfix) {
        if (element in "+-*%/") {
            val first = stack.pop()!!
            val second = stack.pop()!!
            var result = 0
            when (element) {
                "-" -> result = second - first
                "+" -> result = second + first
                "*" -> result = second * first
                "%" -> result = second % first
                "/" -> result = second / first
            }
            stack.push(result)

        }

        val value = element.toIntOrNull()
        stack.push(value!!)
    }
    return stack.pop()!!;
}


fun calculationArithmeticExpression(input: String): Pair<Int, Int> {
    val (elements, error) = transferPrefixToPostfix(getElementFromString(input))

    if (error != 0) return Pair(-1, error)
    return Pair(calculationPostfix(elements), 0)
}
