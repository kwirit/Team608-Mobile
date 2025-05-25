package com.example.scratchinterpretermobile.View.Dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun BoxDialog(showState: MutableState<Boolean>,onConfirmDialog: () -> Unit,content:@Composable () -> Unit){
    CustomDialog(showState){
        Box(modifier = Modifier.fillMaxSize()){
            content()
            Button(modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp),onClick = {
                onConfirmDialog()
                showState.value = false
            }) {
                Text(text = "OK")
            }
        }
    }
}