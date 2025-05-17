package com.example.scratchinterpretermobile.View.Dialogs

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState


@Composable
fun CreateBoxesDialog(showState: MutableState<Boolean>,content:@Composable () -> Unit){
    CustomDialog(showState){
        content()
        Button(onClick = {showState.value = false}) {
            Text(text = "OK")
        }
    }
}