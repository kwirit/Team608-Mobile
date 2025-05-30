package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Controller.Utils.parseCardToInstructionBoxes
import com.example.scratchinterpretermobile.Model.ConditionBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Widgets.InnerCreationButton
import com.example.scratchinterpretermobile.View.Widgets.ListOfIfOperators
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField
import com.example.scratchinterpretermobile.View.Widgets.VerticalReorderList

class IfBox(externalBoxes: MutableList<ProgramBox>) : ProgramBox(externalBoxes) {
    val ifBoxes = mutableStateListOf<ProgramBox>()
    val elseBoxes = mutableStateListOf<ProgramBox>()
    override val value = ConditionBlock(UIContext);
    val showInnerBoxesState = mutableStateOf(false)
    var arithmeticField by mutableStateOf("")
    var currentIsIf = mutableStateOf(true)

    @Composable
    override fun render() {
        BaseBox(
            name = stringResource(R.string.condition), showState,
            onConfirmButton = {
                value.setTrueScript(parseCardToInstructionBoxes(ifBoxes))
                value.setFalseScript(parseCardToInstructionBoxes(elseBoxes))
                code = value.assembleBlock(arithmeticField)
            },
            dialogContent = {
                value.addTrueScopeInContext()
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End)
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { currentIsIf.value = true },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(text = "if")
                        }
                        Button(
                            onClick = { currentIsIf.value = false },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(text = "else")
                        }
                        InnerCreationButton(showInnerBoxesState)
                    }

                    if (currentIsIf.value) {
                        value.setFalseScript(parseCardToInstructionBoxes(elseBoxes))
                        value.addTrueScopeInContext()
                        IfScreen()
                    } else {
                        value.setTrueScript(parseCardToInstructionBoxes(ifBoxes))
                        value.addFalseScopeInContext()
                        ElseScreen()
                    }
                }
            }, onCloseDialog = {

            },
            onDelete = {
                value.removeBlock()
                externalBoxes.removeAll { it.id == id }
            }, dialogModifier = Modifier.height(800.dp)
        ) {
            Column(Modifier
                .fillMaxHeight()
                .width(210.dp)) {
                if (code == 0) {
                    Text(text = arithmeticField)
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
    fun MiniBlocks() {
        LazyColumn(modifier = Modifier.height(60.dp)) {
            items(ifBoxes) {
                item ->
                Box(modifier = Modifier.height(30.dp).fillMaxWidth()){
                    item.render()
                }
            }
        }
    }

    @Composable
    fun IfScreen() {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            VariableTextField(onValueChange = { newText ->
                arithmeticField = newText
            }, value = arithmeticField, modifier = Modifier)
        }
        VerticalReorderList(ifBoxes)
        if (showInnerBoxesState.value) {
            CreateBoxesDialog(showInnerBoxesState, ifBoxes)
        }
    }

    @Composable
    fun ElseScreen() {
        VerticalReorderList(elseBoxes)
        if (showInnerBoxesState.value) {
            CreateBoxesDialog(showInnerBoxesState, elseBoxes)
        }
    }

}