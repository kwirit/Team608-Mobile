package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.runtime.Composable
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox

class ConsoleBox: ProgramBox() {
    override val value = InitBlock();
    @Composable
    override fun render(){
        BaseBox(name = "Вывод", showState,
            onConfirmButton = {

        },
            dialogContent = {

        }) {
        }
    }
}