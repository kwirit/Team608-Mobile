package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Widgets.ListOfTypes
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

class VariableInitializationBox(externalBoxes: MutableList<ProgramBox>) :
    ProgramBox(externalBoxes) {
    override val value = InitBlock(UIContext);
    var text by mutableStateOf("")
    var selectedType = mutableStateOf("Int")

    @Composable
    override fun render() {
        BaseBox(
            name = stringResource(R.string.var_init), showState,
            onConfirmButton = {
                if(selectedType.value == "Int"){
                    code = this.value.assembleIntegerBlock(text)
                }
                else if(selectedType.value == "String"){
                    code = this.value.assembleStringBlock(text)
                }
                else if(selectedType.value == "Boolean"){
                    code = this.value.assembleBooleanBlock(text)
                }
                value.run()
            },
            dialogContent = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column (Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                        Box(modifier = Modifier){
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = stringResource(R.string.select_type)+ ":",color = MaterialTheme.colorScheme.onSurface)
                                ListOfTypes(selectedType)
                            }
                        }
                        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                            VariableTextField(onValueChange = { newText ->
                                text = newText
                            }, value = text)
                        }
                    }
                }
            }, onDelete = {
                value.removeBlock()
                externalBoxes.removeAll { it.id == id }
            }) {
            Column(Modifier
                .fillMaxHeight()
                .width(210.dp)) {
                if (code == 0) {
                    Text(text = text,color = MaterialTheme.colorScheme.onSurface)
                } else {
                    Text(
                        text = ErrorStore.get(code)!!.description,
                        lineHeight = 12.sp,
                        fontSize = 8.sp,color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = ErrorStore.get(code)!!.category,
                        lineHeight = 12.sp,
                        fontSize = 8.sp,color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(text = ErrorStore.get(code)!!.title, lineHeight = 12.sp, fontSize = 8.sp,color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}