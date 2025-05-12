package com.example.scratchinterpretermobile.Controller

import com.example.scratchinterpretermobile.Model.Stack
import com.example.scratchinterpretermobile.Model.Containers.context
import com.example.scratchinterpretermobile.Model.VarBlock

fun validateNameVariable(input: String): Int {
    val nameVariable = input.trim() // Удаление табуляции справа и слева

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


fun calculationArithmeticExpression(input: String): Pair<Int, Int> {
    val (elements, error) = getElementFromString(input)
    if (error != 0) return Pair(1, error)

    val (elementsPostfix, errorPostfix) = transferPrefixToPostfix(elements)
    if (errorPostfix != 0) return Pair(2, errorPostfix)

    return Pair(calculationPostfix(elementsPostfix), 0)
}

fun getElementFromString(input: String): Pair<MutableList<String>, Int> {
    val trimmedInput = "0" + input.trim()

    val elements = mutableListOf<String>()

    val operators = "+-*%/()"

    var currentToken = StringBuilder()

    for (symbol in trimmedInput) {
        if (symbol == ' ') continue

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
        } else {
            return Pair(mutableListOf(""), 103)
        }
    }

    if (currentToken.isNotEmpty()) {
        elements.add(currentToken.toString())
    }

    return Pair(elements, 0)
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

    var lastElement = ""
    for (element in elements) {
        when {
            (element != "(") && (element !in operators) && (element != ")") -> {
                val value = element.toIntOrNull()
                if (value == null) {
                    val error = validateNameVariable(element)
                    if (error != 0) return Pair(mutableListOf(""), error)
                }
                postfix.add(element)
            }

            element == "(" -> stack.push(element)

            element == ")" -> {
                while (!stack.isEmpty() && stack.peek() != "(") {
                    postfix.add(stack.pop()!!)
                }
                if (!stack.isEmpty()) stack.pop()
                else return Pair(mutableListOf(""), 106)
            }

            element in operators -> {
                if (lastElement == "(" || lastElement == "") {
                    return Pair(mutableListOf(""), 106)
                }
                while (!stack.isEmpty()
                    && stack.peek() != "("
                    && priority[element]!! <= priority[stack.peek()]!!
                ) {
                    postfix.add(stack.pop()!!)
                }
                stack.push(element)
            }
        }
        lastElement = element
    }

    while (!stack.isEmpty()) {
        if (stack.peek() == "(")
            return Pair(mutableListOf(""), 106)
        postfix.add(stack.pop()!!)
    }

    return Pair(postfix, 0)
}

fun findVariableInNameSpace(name: String): Int? {
    for (namespace: HashMap<String, VarBlock> in context) {
        if (namespace.containsKey(name)) {
            return (namespace[name]?.value ?: -1) as Int?;
        }
    }
    return -1
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

        var value = element.toIntOrNull()
        if (value == null) {
            value = findVariableInNameSpace(element)
        }
        stack.push(value!!)
    }
    return stack.pop()!!;
}

