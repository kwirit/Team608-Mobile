package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.VarBlock
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Widgets.ListOfVar
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

class AssigningBox(externalBoxes: MutableList<ProgramBox>) : ProgramBox(externalBoxes) {
    override val value = AssignmentBlock(UIContext);
    val checkVariableState = mutableStateOf(true)
    val checkArrayState = mutableStateOf(false)
    val arrayListField = mutableStateListOf<String>()
    var arithmeticField by mutableStateOf("")
    var arrayIndex by mutableStateOf("")
    val state = mutableIntStateOf(-1)
    val selectedVariable = mutableStateOf<VarBlock<*>?>(null)

    @Composable
    override fun render() {
        BaseBox(
            name = stringResource(R.string.assign), showState,
            onConfirmButton = {
                if (state.value == 0) {
                    code = value.assembleIntegerBlock(
                        selectedVariable.value!!.getName(),
                        arithmeticField
                    )
                } else if (state.value == 1) {
                    if (arrayIndex == "") {
                        code = value.assembleIntegerArrayBlock(
                            selectedVariable.value!!.getName(),
                            arithmeticField
                        )
                    } else {
                        code = value.assembleElementIntegerArrayBlock(
                            selectedVariable.value!!.getName(),
                            arrayIndex,
                            arithmeticField
                        )
                    }
                } else if (state.value == 2) {
                    for ((index, field) in arrayListField.withIndex()) {
                        code = value.assembleElementIntegerArrayBlock(
                            selectedVariable.value!!.getName(),
                            index.toString(),
                            field
                        )
                        value.run()
                    }
                }
            },
            dialogContent = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ListOfVar(selectedVariable)

                            if (selectedVariable.value is IntegerArrayBlock) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    ArrayAssignment()
                                }

                            }
                        }

                        when {
                            selectedVariable.value is IntegerBlock -> {
                                state.value = 0
                                Text(stringResource(R.string.value) + ":")
                                VariableTextField(
                                    onValueChange = { arithmeticField = it },
                                    value = arithmeticField
                                )
                            }

                            selectedVariable.value is IntegerArrayBlock -> {
                                if (checkVariableState.value) {
                                    state.value = 1
                                    Text(stringResource(R.string.index) + ":")
                                    VariableTextField(
                                        onValueChange = { arrayIndex = it },
                                        value = arrayIndex
                                    )
                                    Text(stringResource(R.string.value) + ":")
                                    VariableTextField(
                                        onValueChange = { arithmeticField = it },
                                        value = arithmeticField
                                    )
                                } else {
                                    state.value = 2;
                                    val arraySize =
                                        (selectedVariable.value as? IntegerArrayBlock)?.getValue()?.size
                                            ?: 0

                                    if (arrayListField.size != arraySize) {
                                        arrayListField.clear()
                                        repeat(arraySize) {
                                            arrayListField.add("")
                                        }
                                    }

                                    LazyColumn(
                                        modifier = Modifier
                                            .padding(vertical = 16.dp)
                                            .fillMaxWidth(0.9f)
                                    ) {
                                        items(arraySize) { index ->
                                            Column {
                                                Text(stringResource(R.string.element) + " $index")
                                                VariableTextField(
                                                    onValueChange = { newText ->
                                                        arrayListField[index] = newText
                                                    },
                                                    value = arrayListField[index]
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }, onDelete = {
                value.removeBlock()
                externalBoxes.removeAll { it.id == id }
            }) {
            Column(Modifier
                .fillMaxHeight()
                .width(230.dp)) {
                if (code == 0) {
                    if (state.value == 0) {
                        Text(text = selectedVariable.value!!.getName() + " = " + arithmeticField)
                    } else if (state.value == 1) {
                        Text(text = selectedVariable.value!!.getName() + "[" + arrayIndex + "]" + " = " + arithmeticField)
                    } else if (state.value == 2) {
                        for ((index, field) in arrayListField.withIndex()) {
                            if(field.isBlank()){
                                Text(text = index.toString() + ": " + "0")
                            }
                            else{
                                Text(text = index.toString() + ": " + field)
                            }
                        }
                    }
                } else {
                    Text(
                        text = ErrorStore.get(code)!!.description,
                        lineHeight = 12.sp,
                        fontSize = 8.sp
                    )
                    Text(
                        text = ErrorStore.get(code)!!.category,
                        lineHeight = 12.sp,
                        fontSize = 8.sp
                    )
                    Text(text = ErrorStore.get(code)!!.title, lineHeight = 12.sp, fontSize = 8.sp)
                }
            }
        }
    }

    @Composable
    fun ArrayAssignment() {
        Row(modifier = Modifier.padding(start = 40.dp)) {
            Column(modifier = Modifier.width(80.dp)) {
                Text(stringResource(R.string.single_element))
                Checkbox(
                    checked = checkVariableState.value,
                    onCheckedChange = {
                        checkVariableState.value = it
                        if (it) checkArrayState.value = false
                    }
                )
            }
            Column(modifier = Modifier.width(80.dp)) {
                Text(stringResource(R.string.all_elements))
                Checkbox(
                    checked = checkArrayState.value,
                    onCheckedChange = {
                        checkArrayState.value = it
                        if (it) checkVariableState.value = false
                    }
                )
            }
        }
    }
}