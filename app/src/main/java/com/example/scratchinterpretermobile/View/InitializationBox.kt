package com.example.scratchinterpretermobile.View

import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class InitializationBox : ProgramBox() {
    @Composable
    override fun render(){
        BaseBox(name = "Инициализация") {
        }
    }
}