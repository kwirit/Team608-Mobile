package com.example.scratchinterpretermobile.View.Widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.R


@Composable
fun ListOfTypes(selectedOperator: MutableState<String>): String {
    val operators = listOf<String>("Int", "String", "Boolean")
    val expanded = remember { mutableStateOf(false) }

    Box(modifier = Modifier) {
        TextButton(onClick = { expanded.value = true }, modifier = Modifier) {
            Text(
                text = if (selectedOperator.value == "") stringResource(R.string.select_operator) else selectedOperator.value,
                fontSize = 16.sp,
                lineHeight = 10.sp
            )
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