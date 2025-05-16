package com.example.scratchinterpretermobile.View

import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class InitializationBox : ProgramBox() {
    @Composable
    override fun render(){
        var text by remember { mutableStateOf("") }
        BaseBox(name = "Инициализация") {
            TextField(modifier =  Modifier.size(80.dp),onValueChange = {
                newText ->
                text = newText
            }, value = text)
        }
    }
}