package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.VarBlock
import com.example.scratchinterpretermobile.Model.Variable
import com.example.scratchinterpretermobile.Model.mainContext
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.Widgets.ListOfVar
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

class AssigningBox: ProgramBox() {
    override val value = AssignmentBlock();
    val checkVariableState = mutableStateOf(true)
    val checkArrayState = mutableStateOf(false)
    var arrayListField = mutableStateListOf<String>()
    var arithmeticField by mutableStateOf("")
    var arrayIndex by mutableStateOf("")
    var code by mutableIntStateOf(104)
    val isVar = mutableStateOf(false)
    val isIndexArray = mutableStateOf(false)
    val isArray = mutableStateOf(false)
    var selectedVariable = mutableStateOf<VarBlock<*>?>(null)
    @Composable
    override fun render(){
        BaseBox(name = "Присваивание", showState,
            onConfirmButton = {
                if(isVar.value){
                    value.assignIntegerBlock(selectedVariable.value!!.getName(),arithmeticField)
                }
                else if(isIndexArray.value){
                    if(arrayIndex == ""){
                        value.assignIntegerArrayBlock(selectedVariable.value!!.getName(),arithmeticField)
                    }
                    else{
                        value.assignElementIntegerArrayBlock(selectedVariable.value!!.getName(),arrayIndex,arithmeticField)
                    }
                }
                else if(isArray.value){
                    for((index,field) in arrayListField.withIndex()){
                        value.assignElementIntegerArrayBlock(selectedVariable.value!!.getName(),index.toString(),field)
                    }
                }
        },
            dialogContent = {
                Row{
                    selectedVariable = ListOfVar()
                    if (selectedVariable.value is IntegerBlock) {
                        arithmeticField = ""
                        VariableAssignment()
                    }
                    if(selectedVariable.value is IntegerArrayBlock){
                        arithmeticField = ""
                        val arrayBlock = selectedVariable.value as IntegerArrayBlock
                        ArrayAssignment(arrayBlock)
                    }
                }
        }) {
        }
    }
    @Composable
    fun VariableAssignment(){
        VariableTextField(onValueChange = {newText ->
            arithmeticField = newText;
        }, value = arithmeticField)
    }
    @Composable
    fun ArrayAssignment(arrayBlock: IntegerArrayBlock){
        Checkbox(
            checked = checkVariableState.value,
            onCheckedChange = {
                checkVariableState.value = it
                if (it) checkArrayState.value = false
            }
        )
        Checkbox(
            checked = checkArrayState.value,
            onCheckedChange = {
                checkArrayState.value = it
                if (it) checkVariableState.value = false
            }
        )
        if(checkVariableState.value){
            SingleElementInput()
        }
        if(checkArrayState.value){
            AllElementsInput(arrayBlock)
        }
    }
    @Composable
    fun SingleElementInput(){
        VariableTextField(onValueChange = {newText ->
            arrayIndex = newText;
        }, value = arrayIndex)
        VariableTextField(onValueChange = {newText ->
            arithmeticField = newText;
        }, value = arithmeticField)
    }
    @Composable
    fun AllElementsInput(arrayBlock: IntegerArrayBlock) {
        val arraySize = arrayBlock.getValue().size

        if (arrayListField.size != arraySize) {
            arrayListField.clear()
            repeat(arraySize) {
                arrayListField.add("")
            }
        }

        Column {
            repeat(arraySize) { index ->
                Text(text = "Элемент $index")
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