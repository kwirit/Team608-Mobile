package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.scratchinterpretermobile.Model.StringBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.СonvertationTypeBlock
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
    BaseBox(name = "Приведение типов", showState,
        onConfirmButton = {
            value.assembleBlock(selectedOperator2.value,input2.value,selectedOperator1.value,input1.value)
    }, dialogContent = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ListOfTypes(selectedOperator1)
                VariableTextField(onValueChange = {
                    newText ->
                    input1.value = newText
                },value = input1.value)
                ListOfTypes(selectedOperator2)
                VariableTextField(onValueChange = {
                    newText ->
                    input2.value = newText
                }, value = input2.value
                )

            }
        }
    }, onDelete = {
            value.removeBlock()
            externalBoxes.removeAll { it.id == id }
        }) {

    }
    }
}