package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

class ArrayInitializationBox(externalBoxes: MutableList<ProgramBox>) : ProgramBox(externalBoxes) {
    override val value = InitBlock()
    var arrayName by mutableStateOf("")
    var arraySize by mutableStateOf("")

    @Composable
    override fun render(){
        BaseBox(name = "Инициализация массива", showState,
            onConfirmButton = {
                code = this.value.initIntegerArrayBlock(arrayName, arraySize)
            },
            dialogContent = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.align(Alignment.Center)){
                        Column {
                            Text(text = "Введите название массива:")
                            VariableTextField(onValueChange = { newArrayName ->
                                arrayName = newArrayName
                            }, value = arrayName)
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(text = "Введите длину массива:")
                            VariableTextField(onValueChange = { newArraySize ->
                                arraySize = newArraySize
                            }, value = arraySize)
                        }
                    }
                }
            },
            onDelete = {
                value.removeBlock()
                externalBoxes.removeAll { it.id == id }
            }) {
            if(code == 0){
                Text(text = arrayName + ": " + arraySize)
            }
            else{
                Column {
                    Text(text = ErrorStore.get(code)!!.description, lineHeight = 12.sp, fontSize = 8.sp)
                    Text(text = ErrorStore.get(code)!!.category, lineHeight = 12.sp, fontSize = 8.sp)
                    Text(text = ErrorStore.get(code)!!.title, lineHeight = 12.sp, fontSize = 8.sp)
                }
            }
        }
    }
}