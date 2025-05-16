package com.example.scratchinterpretermobile.View

import androidx.compose.runtime.Composable
import com.example.scratchinterpretermobile.Model.InitBlock

class ConsoleBox: ProgramBox() {
    override val value = InitBlock();
    @Composable
    override fun render(){
        BaseBox(name = "Вывод") {
        }
    }
}