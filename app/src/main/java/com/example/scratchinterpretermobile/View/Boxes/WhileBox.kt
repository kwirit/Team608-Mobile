package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.scratchinterpretermobile.Controller.Utils.parseCardToInstructionBoxes
import com.example.scratchinterpretermobile.Model.ConditionBlock
import com.example.scratchinterpretermobile.Model.LoopBlock
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Widgets.InnerCreationButton
import com.example.scratchinterpretermobile.View.Widgets.ListOfIfOperators
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField
import com.example.scratchinterpretermobile.View.Widgets.VerticalReorderList

class WhileBox: ProgramBox() {
    val boxes = mutableStateListOf<ProgramBox>()
    val showInnerBoxesState = mutableStateOf(false)
    var leftOperand by mutableStateOf("")
    var rightOperand by mutableStateOf("")
    var operator by mutableStateOf("")
    override val value = LoopBlock();
    @Composable
    override fun render(){
        BaseBox(name = "Условие", showState,
            onConfirmButton = {
                value.processInput(leftOperand,rightOperand,operator)
            },
            dialogContent = {
                Column {
                    InnerCreationButton(showInnerBoxesState, modifier = Modifier.fillMaxWidth())
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                        VariableTextField(onValueChange = {newText->
                            leftOperand = newText
                        },value = leftOperand)
                        operator = ListOfIfOperators()
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