package com.example.scratchinterpretermobile.Controller

import com.example.scratchinterpretermobile.Controller.Error.Error
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.Containers
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


fun getElementFromString(input: String): MutableList<String> {
    val trimmedInput = input.trim()

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
            currentToken.append(symbol)
        }
    }

    if (currentToken.isNotEmpty()) {
        elements.add(currentToken.toString())
    }

    return elements
}


fun transferPrefixToPostfix(elements: MutableList<String>): MutableList<String> {
    var postfix = mutableListOf<String>()
    var stack = Stack<String>()
    var priority = mapOf(
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
            element != "(" && element !in operators && element != ")" -> {
                postfix.add(element)
            }

            element == "(" -> {
                stack.push(element)
            }

            element == ")" -> {
                while (!stack.isEmpty() && stack.peek() != "(") {
                    postfix.add(stack.pop()!!)
                }
                if (!stack.isEmpty()) stack.pop()
            }

            element in operators -> {
                while (!stack.isEmpty()
                    && stack.peek() != "("
                    && priority[element]!! <= priority[stack.peek()]!!
                ) {
                    postfix.add(stack.pop()!!)
                }
                stack.push(element)
            }
        }
    }
    while (!stack.isEmpty()) {
        postfix.add(stack.pop()!!)
    }

    return postfix
}

fun findVariableInNameSpace(name: String): Int? {
    for (namespace: HashMap<String, VarBlock> in context) {
        if (namespace.containsKey(name)) {
            return (namespace[name]?.value ?: -1) as Int?;
        }
    }
    return -1
}

fun calculationPrefix(postfix: MutableList<String>): Int {
    var stack = Stack<Int>()

    for (element in postfix) {
        if (element in "+-*%/") {
            if (stack.size() >= 2) {
                var first = stack.pop()!!
                var second = stack.pop()!!
                when {
                    element == "-" -> {
                        stack.push(second - first)
                    }

                    element == "+" -> {
                        stack.push(second + first)
                    }

                    element == "*" -> {
                        stack.push(second * first)
                    }

                    element == "%" -> {
                        stack.push(second % first)
                    }

                    element == "/" -> {
                        stack.push(second / first)
                    }
                }
                continue
            } else
                return -1
        }

        var value = element.toIntOrNull()
        if (value == null) {
            value = findVariableInNameSpace(element)
        }
        stack.push(value!!)
    }
    return stack.pop()!!;
}

