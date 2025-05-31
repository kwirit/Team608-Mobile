package com.example.scratchinterpretermobile.View.Widgets

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun VariableTextField(
    onValueChange: (String) -> Unit,
    value: String,
    modifier: Modifier = Modifier
) {
    TextField(modifier = modifier.wrapContentWidth(), onValueChange = onValueChange, value = value)
}