package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

class VariableInitializationBox : ProgramBox() {
    override val value = InitBlock();
    var text by mutableStateOf("")
    var code by mutableIntStateOf(104)

    @Composable
    override fun render(){
        BaseBox(name = "Инициализация переменной", showState,
            onConfirmButton = {
                code = this.value.initIntegerBlock(text)
        },
            dialogContent = {
                VariableTextField(onValueChange = { newText ->
                text = newText
            }, value = text)
            Text(text = ErrorStore.get(code)!!.title)
        }) {
        }
    }
}