package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

class VariableInitializationBox : ProgramBox() {
    override val value = InitBlock();
    var text by mutableStateOf("")
    var code by mutableIntStateOf(104)
    val name = "Объявление переменной"

    @Composable
    override fun render(){
        BaseBox(name = name, showState,
            onConfirmButton = {
                code = this.value.initIntegerBlock(text)
        },
            dialogContent = {
                Box(modifier = Modifier.fillMaxSize()){
                    Text(text = name, modifier = Modifier.align(Alignment.TopCenter).padding(top = 100.dp))
                    Column (modifier = Modifier.align(alignment = Alignment.Center)){
                        Text(text = "Введите название переменной:")
                        VariableTextField(onValueChange = { newText ->
                            text = newText
                        }, value = text)
                    }
                }
        }) {
            Column(Modifier.fillMaxHeight().width(230.dp)){
                if(code == 0){
                    Text(text = text)
                }
                else{
                    Text(text = ErrorStore.get(code)!!.description, lineHeight = 12.sp, fontSize = 8.sp)
                    Text(text = ErrorStore.get(code)!!.category, lineHeight = 12.sp, fontSize = 8.sp)
                    Text(text = ErrorStore.get(code)!!.title, lineHeight = 12.sp, fontSize = 8.sp)
                }
            }
        }
    }
}