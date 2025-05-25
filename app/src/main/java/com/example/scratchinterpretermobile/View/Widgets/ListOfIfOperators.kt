package com.example.scratchinterpretermobile.View.Widgets

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ListOfIfOperators(): String {
    val operators = listOf<String>("==","!=","<",">","<=",">=",)
    val selectedOperator = remember { mutableStateOf("Выберите оператор сравнения") }
    val expanded = remember { mutableStateOf(false) }

    TextButton(onClick = { expanded.value = true }) {
        Text(text = selectedOperator.value)
    }

    DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
        operators.forEach { operator ->
            DropdownMenuItem(
                onClick = {
                    selectedOperator.value = operator
                    expanded.value = false
                },
                text = { Text(operator) }
            )
        }
    }

    return selectedOperator.value
}