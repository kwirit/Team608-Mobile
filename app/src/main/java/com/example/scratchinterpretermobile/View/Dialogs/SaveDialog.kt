package com.example.scratchinterpretermobile.View.Dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.View.ViewModels.MainViewModel
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

@Composable
fun SaveDialog(viewModel: MainViewModel){
    val text = remember {mutableStateOf("")}
    CustomDialog(viewModel.saveDialogState) {
        Column{
            VariableTextField(onValueChange = {
                    newText ->
                text.value = newText
            }, value = text.value)
            Button(modifier = Modifier.padding(20.dp),onClick = {

            }) {

            }
        }
    }
}