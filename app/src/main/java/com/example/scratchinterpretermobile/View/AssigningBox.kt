package com.example.scratchinterpretermobile.View

import androidx.compose.runtime.Composable
import com.example.scratchinterpretermobile.Model.Variable

class AssigningBox: ProgramBox() {
    val list = mutableListOf<Variable>()
    @Composable
    override fun render(){
        BaseBox(name = "Присваивание") {
        }
    }
}