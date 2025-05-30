package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox

class TypeConversionBlock(externalBoxes: MutableList<ProgramBox>) : ProgramBox(externalBoxes) {
//    override val value =
    @Composable
    override fun render() {
    BaseBox(name = "Приведение типов", showState,
        onConfirmButton = {

    }, dialogContent = {
        Box(modifier = Modifier.fillMaxSize()){
            Column() {

            }
        }
    }) {

    }
    }
}