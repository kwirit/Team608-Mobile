package com.example.scratchinterpretermobile.View

import androidx.compose.runtime.Composable

class ConsoleBox: ProgramBox() {
    @Composable
    override fun render(){
        ConsoleCard()
    }
}