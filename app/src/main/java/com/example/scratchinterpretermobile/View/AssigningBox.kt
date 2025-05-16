package com.example.scratchinterpretermobile.View

import androidx.compose.runtime.Composable
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.Variable

class AssigningBox: ProgramBox() {
    override val value = InitBlock();
    val list = mutableListOf<Variable>()
    @Composable
    override fun render(){
        BaseBox(name = "Присваивание") {
        }
    }
}