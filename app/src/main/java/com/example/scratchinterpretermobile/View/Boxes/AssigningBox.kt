package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.VarBlock
import com.example.scratchinterpretermobile.Model.Variable
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.Widgets.ListOfVar
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

class AssigningBox: ProgramBox() {
    override val value = InitBlock();
    val checkVariableState = mutableStateOf(true)
    val checkArrayState = mutableStateOf(false)
    @Composable
    override fun render(){
        BaseBox(name = "Присваивание", showState,
            onConfirmButton = {

        },
            dialogContent = {
                Row{
                    val selectedVariable = ListOfVar()

                    if (selectedVariable.value is IntegerBlock) {
                        VariableAssignment()
                    }
                    if(selectedVariable.value is IntegerArrayBlock){
                        val arrayBlock = selectedVariable.value as IntegerArrayBlock
                        ArrayAssignment(arrayBlock)
                    }
                }
        }) {
        }
    }
    @Composable
    fun VariableAssignment(){
        VariableTextField(onValueChange = {newText -> }, "")
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
        VariableTextField(onValueChange = {newText -> }, "")
        VariableTextField(onValueChange = {newText -> }, "")
    }
    @Composable
    fun AllElementsInput(arrayBlock: IntegerArrayBlock){
        val value = (arrayBlock.value as MutableList<Int>).size
        Text(text = "$value")
    }

}