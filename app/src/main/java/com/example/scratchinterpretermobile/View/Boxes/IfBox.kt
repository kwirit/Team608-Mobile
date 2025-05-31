package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Controller.Utils.parseCardToInstructionBoxes
import com.example.scratchinterpretermobile.Model.ConditionBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Widgets.InnerCreationButton
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
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 100.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
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
                    InnerCreationButton(
                        showInnerBoxesState,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 100.dp, bottom = 23.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomEnd)
                            .padding(20.dp),
                    ) {
                        Button(
                            onClick = { currentIsIf.value = true },
                            modifier = Modifier
                                .height(48.dp)
                                .width(60.dp),
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Text(text = "if")
                        }
                        Button(
                            onClick = { currentIsIf.value = false },
                            modifier = Modifier
                                .height(48.dp)
                                .width(65.dp)
                                .padding(start = 8.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(text = "else")
                        }
                    }
                }
            },
            onDelete = {
                value.removeBlock()
                externalBoxes.removeAll { it.id == id }
            }, dialogModifier = Modifier.height(800.dp)
        ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .width(210.dp),
            ) {
                if (code == 0) {
                    Text(text = arithmeticField, color = MaterialTheme.colorScheme.onSurface)
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


    @Composable
    fun IfScreen() {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.input_logical_expression)+":",
                color = MaterialTheme.colorScheme.onSurface
            )
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