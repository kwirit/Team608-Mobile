package com.example.scratchinterpretermobile.View.Widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.R

@Composable
fun ListOfIfOperators(selectedOperator: MutableState<String>): String {
    val operators = listOf<String>("==", "!=", "<", ">", "<=", ">=")
    val expanded = remember { mutableStateOf(false) }

    Box(modifier = Modifier.size(60.dp)) {
        TextButton(shape = CircleShape,onClick = { expanded.value = true }, modifier = Modifier.fillMaxSize().border(width = 2.dp,color = MaterialTheme.colorScheme.primaryContainer,shape = CircleShape)) {
            Text(text = if (selectedOperator.value == "") stringResource(R.string.select_operator) else selectedOperator.value, fontSize = 16.sp,lineHeight = 10.sp)
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