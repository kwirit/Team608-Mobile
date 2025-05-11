package com.example.scratchinterpretermobile.Controller

import com.example.scratchinterpretermobile.Controller.Error.Error
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore

fun validateNameVariable(input: String): Error? {
    val nameVariable = input.trim()

    if (" " in nameVariable) {
        return ErrorStore.get(102);
    }

    val regex = Regex("^[a-zA-Z_][a-zA-Z0-9_]*$")

    if (!regex.matches(nameVariable)) {
        if (!nameVariable.first().isLetter() && nameVariable.first() != '_') {
            return ErrorStore.get(101);
        }
        return ErrorStore.get(103);
    }

    return ErrorStore.get(0)
}

