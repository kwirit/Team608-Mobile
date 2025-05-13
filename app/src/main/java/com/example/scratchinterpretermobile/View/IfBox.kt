package com.example.scratchinterpretermobile.View

import androidx.compose.runtime.Composable

class IfBox: ProgramBox() {
    @Composable
    override fun render(){
        IfCard()
    }
}