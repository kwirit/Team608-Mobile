package com.example.scratchinterpretermobile.View.Cards

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.BaseStructure.BaseCard

@Composable
fun AssigningCard(
    onAdd: () -> Unit,
    showBoxesState: MutableState<Boolean> = mutableStateOf(false)
) {
    BaseCard(name = stringResource(R.string.assign), onClick = {
        onAdd()
        showBoxesState.value = false
    }) {
    }
}