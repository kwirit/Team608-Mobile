package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.Model.ConditionsBlock
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Widgets.InnerCreationButton
import com.example.scratchinterpretermobile.View.Widgets.ListOfIfOperators
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField
import com.example.scratchinterpretermobile.View.Widgets.VerticalReorderList

class IfBox: ProgramBox() {
    val boxes = mutableStateListOf<ProgramBox>()
    override val value = ConditionsBlock();
    val showInnerBoxesState = mutableStateOf(false)
    var leftOperand by mutableStateOf("")
    var rightOperand by mutableStateOf("")

    @Composable
    override fun render(){
        BaseBox(name = "Условие", showState,
            onConfirmButton = {
        },
            dialogContent = {
                Column {
                    InnerCreationButton(showInnerBoxesState, modifier = Modifier.fillMaxWidth())
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                        VariableTextField(onValueChange = {newText->
                            leftOperand = newText
                        },value = leftOperand)
                        ListOfIfOperators()
                        VariableTextField(onValueChange = {newText->
                            rightOperand = newText
                        },value = rightOperand)
                    }
                    VerticalReorderList(boxes)
                    if(showInnerBoxesState.value){
                        CreateBoxesDialog(showInnerBoxesState,boxes)
                    }
                }
        }) {
        }
    }
}