package com.example.scratchinterpretermobile.View

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun DragElement(content:@Composable () -> Unit){
    Box(modifier = Modifier.fillMaxSize()){
        content()
    }
}