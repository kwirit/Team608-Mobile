package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.СonvertationTypeBlock
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Widgets.ListOfTypes
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

class TypeConversionBlock(externalBoxes: MutableList<ProgramBox>) : ProgramBox(externalBoxes) {
    override val value = СonvertationTypeBlock(UIContext)
    var input1 = mutableStateOf("")
    var input2 = mutableStateOf("")
    var selectedOperator1 = mutableStateOf("")
    var selectedOperator2 = mutableStateOf("")

    @Composable
    override fun render() {
        BaseBox(
            name = stringResource(R.string.type_conversation), showState,
            onConfirmButton = {
                value.assembleBlock(
                    selectedOperator2.value,
                    input2.value,
                    selectedOperator1.value,
                    input1.value
                )
            }, dialogContent = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Column {
                            ListOfTypes(selectedOperator1)
                            Text(
                                text = stringResource(R.string.in_fi_ex),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            VariableTextField(onValueChange = { newText ->
                                input1.value = newText
                            }, value = input1.value)
                        }
                        Spacer(modifier = Modifier.padding(top = 16.dp))
                        Column {
                            ListOfTypes(selectedOperator2)
                            Text(
                                text = stringResource(R.string.in_se_ex),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            VariableTextField(
                                onValueChange = { newText ->
                                    input2.value = newText
                                }, value = input2.value
                            )
                        }
                    }
                }
            }, onDelete = {
                value.removeBlock()
                externalBoxes.removeAll { it.id == id }
            }) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .width(210.dp),
            ) {
                if (code == 0) {
                    if(input1.value!="" && input2.value!=""){
                        Text(text = selectedOperator1.value + ":" + input1.value, color = MaterialTheme.colorScheme.onSurface)
                        Text(text = selectedOperator2.value + ":" + input2.value, color = MaterialTheme.colorScheme.onSurface)
                    }
                } else {
                    Text(
                        text = ErrorStore.get(code)!!.description,
                        lineHeight = 12.sp,
                        fontSize = 8.sp, color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = ErrorStore.get(code)!!.category,
                        lineHeight = 12.sp,
                        fontSize = 8.sp, color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = ErrorStore.get(code)!!.title,
                        lineHeight = 12.sp,
                        fontSize = 8.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}