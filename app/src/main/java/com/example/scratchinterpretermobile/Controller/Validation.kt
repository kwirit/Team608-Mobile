package com.example.scratchinterpretermobile.Controller

import com.example.scratchinterpretermobile.Controller.Error.Error
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.Containers
import com.example.scratchinterpretermobile.Model.Stack

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
    var isVar = false;

    for (symbol in trimmedInput) {
        if (symbol == ' ') continue

        if (currentToken.isNotEmpty() && (symbol.isDigit() || symbol.isLetter() || symbol == '_')) {
            currentToken.append(symbol)
        } else if (currentToken.isEmpty() && (symbol.isDigit() || symbol.isLetter() || symbol == '_')){
            currentToken = StringBuilder().append(symbol)
        }

        else if (symbol in operators) {
            if (currentToken.isNotEmpty()) {
                elements.add(currentToken.toString())
                currentToken.clear()
            }
            elements.add(symbol.toString())
        } else {
            currentToken.append(symbol);
        }
    }

    if (currentToken.isNotEmpty()) {
        elements.add(currentToken.toString())
    }

    return elements
}

