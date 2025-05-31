package com.example.scratchinterpretermobile.View.Cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.scratchinterpretermobile.View.BaseStructure.BaseCard

@Composable
fun TypeConversionCard(
    onAdd: () -> Unit,
    showBoxesState: MutableState<Boolean> = mutableStateOf(false)
) {
    BaseCard(name = "Приведение", onClick = {
        showBoxesState.value = false
        onAdd()
    }) {
    }
}