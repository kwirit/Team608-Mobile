package com.example.scratchinterpretermobile.View.Widgets

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.scratchinterpretermobile.Model.VarBlock
import com.example.scratchinterpretermobile.Model.mainContext

@Composable
fun ListOfVar(): MutableState<VarBlock<*>?> {
    val variables = mainContext.GetListVarBlock()
    val selectedVariable = remember { mutableStateOf<VarBlock<*>?>(null) }
    val expanded = remember { mutableStateOf(false) }

    TextButton(onClick = { expanded.value = true }) {
        Text(text = selectedVariable.value?.getName() ?: "Выберите")
    }

    DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
        variables.forEach { varBlock ->
            DropdownMenuItem(
                onClick = {
                    selectedVariable.value = varBlock
                    expanded.value = false
                },
                text = { Text(varBlock.getName()) }
            )
        }
    }

    return selectedVariable
}