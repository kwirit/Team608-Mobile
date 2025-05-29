package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Controller.Utils.parseCardToInstructionBoxes
import com.example.scratchinterpretermobile.Model.ConditionBlock
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Widgets.InnerCreationButton
import com.example.scratchinterpretermobile.View.Widgets.ListOfIfOperators
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField
import com.example.scratchinterpretermobile.View.Widgets.VerticalReorderList

class IfBox(externalBoxes: MutableList<ProgramBox>) : ProgramBox(externalBoxes) {
    val ifBoxes = mutableStateListOf<ProgramBox>()
    val elseBoxes = mutableStateListOf<ProgramBox>()
    override val value = ConditionBlock();
    val showInnerBoxesState = mutableStateOf(false)
    var leftOperand by mutableStateOf("")
    var rightOperand by mutableStateOf("")
    var operator = mutableStateOf("Выбрать оператор")
    var currentIsIf = mutableStateOf(true)

    @Composable
    override fun render(){
        BaseBox(name = "Условие", showState,
            onConfirmButton = {
                code = value.processInput(leftOperand, rightOperand,operator.value, parseCardToInstructionBoxes(ifBoxes),parseCardToInstructionBoxes(elseBoxes))
        },
            dialogContent = {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().align(Alignment.End).padding(20.dp), horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = { currentIsIf.value = true },modifier = Modifier.padding(end = 8.dp)) {
                            Text(text = "if")
                        }
                        Button(onClick = { currentIsIf.value = false },modifier = Modifier.padding(end = 8.dp)) {
                            Text(text = "else")
                        }
                        InnerCreationButton(showInnerBoxesState)
                    }

                    if (currentIsIf.value) {
                        value.setElseBlock(parseCardToInstructionBoxes(elseBoxes))
                        value.addThenScopeInContext()
                        IfScreen()
                    } else {
                        value.setThenBlock(parseCardToInstructionBoxes(ifBoxes))
                        value.addElseScopeInContext()
                        ElseScreen()
                    }
                }
        }, onDelete = {
            value.removeBlock()
            externalBoxes.removeAll { it.id == id }}, dialogModifier = Modifier.height(800.dp)) {
            Column(Modifier.fillMaxHeight().width(230.dp)){
                if(code == 0){
                    Text(text = rightOperand + " " + operator.value + " " + leftOperand)
                }
                else{
                    Text(text = ErrorStore.get(code)!!.description, lineHeight = 12.sp, fontSize = 8.sp)
                    Text(text = ErrorStore.get(code)!!.category, lineHeight = 12.sp, fontSize = 8.sp)
                    Text(text = ErrorStore.get(code)!!.title, lineHeight = 12.sp, fontSize = 8.sp)
                }
            }
        }
    }
    @Composable
    fun IfScreen(){
        Row(modifier = Modifier.fillMaxWidth()){
            VariableTextField(onValueChange = {newText->
                leftOperand = newText
            },value = leftOperand,modifier = Modifier.weight(1f))
            ListOfIfOperators(operator)
            VariableTextField(onValueChange = {newText->
                rightOperand = newText
            },value = rightOperand,modifier = Modifier.weight(1f))
        }
        VerticalReorderList(ifBoxes)
        if(showInnerBoxesState.value){
            CreateBoxesDialog(showInnerBoxesState,ifBoxes)
        }
    }

    @Composable
    fun ElseScreen(){
        VerticalReorderList(elseBoxes)
        if(showInnerBoxesState.value){
            CreateBoxesDialog(showInnerBoxesState,elseBoxes)
        }
    }

}