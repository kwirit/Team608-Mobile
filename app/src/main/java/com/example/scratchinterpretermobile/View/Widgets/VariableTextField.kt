package com.example.scratchinterpretermobile.View.Widgets

import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.Model.Variable


@Composable
fun VariableTextField(onValueChange: (String) -> Unit, value: String){
    TextField(modifier = Modifier.size(80.dp), onValueChange = onValueChange, value = value)
}