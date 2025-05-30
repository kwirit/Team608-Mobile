package com.example.scratchinterpretermobile.View.Widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListOfIfOperators(selectedOperator: MutableState<String>): String {
    val operators = listOf<String>("==", "!=", "<", ">", "<=", ">=")
    val expanded = remember { mutableStateOf(false) }

    Box(modifier = Modifier.width(85.dp)) {
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
    }


    return selectedOperator.value
}