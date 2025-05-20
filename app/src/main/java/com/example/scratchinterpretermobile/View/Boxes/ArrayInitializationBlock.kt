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

class ArrayInitializationBlock : ProgramBox() {
    override val value = InitBlock()
    var arrayName by mutableStateOf("")
    var arraySize by mutableStateOf("")
    var code by mutableIntStateOf(104)

    @Composable
    override fun render(){
        BaseBox(name = "Инициализация массива", showState,
            onConfirmButton = {
                code = this.value.processInput(arrayName, arraySize)
            },
            dialogContent = {
                VariableTextField(onValueChange = { newArrayName ->
                    arrayName = newArrayName
                }, value = arrayName)
                VariableTextField(onValueChange = { newArraySize ->
                    arraySize = newArraySize
                }, value = arraySize)
                Text(text = ErrorStore.get(code)!!.title)
            }) {
        }
    }
}