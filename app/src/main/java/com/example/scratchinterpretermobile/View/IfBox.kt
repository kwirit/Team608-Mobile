package com.example.scratchinterpretermobile.View

import androidx.compose.runtime.Composable
import com.example.scratchinterpretermobile.Model.InitBlock

class IfBox: ProgramBox() {
    override val value = InitBlock();
    @Composable
    override fun render(){
        BaseBox(name = "Условие") {
        }
    }
}