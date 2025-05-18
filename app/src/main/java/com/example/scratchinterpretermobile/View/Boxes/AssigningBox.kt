package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.runtime.Composable
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.Variable
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox

class AssigningBox: ProgramBox() {
    override val value = InitBlock();
    val list = mutableListOf<Variable>()
    @Composable
    override fun render(){
        BaseBox(name = "Присваивание", showState,
            onConfirmButton = {

        },
            dialogContent = {

        }) {
        }
    }
}