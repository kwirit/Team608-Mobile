package com.example.scratchinterpretermobile.View

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.InitBlock

class InitializationBox : ProgramBox() {
    override val value = InitBlock();
    var text by mutableStateOf("")
    var code by mutableIntStateOf(0)

    @Composable
    override fun render(){
        BaseBox(name = "Инициализация") {
            TextField(modifier =  Modifier.size(80.dp),onValueChange = {
                newText ->
                code = this.value.processInput(text)
                text = newText
            }, value = text)
            Text(text = ErrorStore.get(code)!!.title)
        }
    }
}