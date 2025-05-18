package com.example.scratchinterpretermobile.View.Dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomDialog(showState: MutableState<Boolean>, content:@Composable () -> Unit){
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false),
        onDismissRequest = {showState.value = false},
    ) {
        Column(Modifier.width(320.dp).height(600.dp).padding(20.dp).background(color = Color.White, shape = RoundedCornerShape(20.dp))) {
            content()
        }
    }
}
