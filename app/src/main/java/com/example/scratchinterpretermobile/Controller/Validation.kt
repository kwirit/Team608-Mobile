package com.example.scratchinterpretermobile.Controller

import com.example.scratchinterpretermobile.Controller.Error.Error
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.Containers

fun validateNameVariable(input: String): Error? {
    val nameVariable = input.trim()

    if (nameVariable.isEmpty()) {
        return ErrorStore.get(104)
    }

    if (" " in nameVariable) {
        return ErrorStore.get(102)
    }

    val regex = Regex("^[a-zA-Z_][a-zA-Z0-9_]*$")
    if (!regex.matches(nameVariable)) {
        val firstChar = nameVariable.first()
        if (!firstChar.isLetter() && firstChar != '_') {
            return ErrorStore.get(101)
        }
        return ErrorStore.get(103)
    }

    return ErrorStore.get(0)
}



