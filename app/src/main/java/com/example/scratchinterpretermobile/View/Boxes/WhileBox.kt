package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.scratchinterpretermobile.Model.LoopBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Widgets.InnerCreationButton
import com.example.scratchinterpretermobile.View.Widgets.ListOfIfOperators
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField
import com.example.scratchinterpretermobile.View.Widgets.VerticalReorderList
import com.example.scratchinterpretermobile.ui.theme.innerText

class WhileBox(externalBoxes: MutableList<ProgramBox>) : ProgramBox(externalBoxes) {
    val boxes = mutableStateListOf<ProgramBox>()
    val showInnerBoxesState = mutableStateOf(false)
    var arithmeticField by mutableStateOf("")
    override val value = LoopBlock(UIContext);

    @Composable
    override fun render() {
        BaseBox(
            name = stringResource(R.string.cycle), showState,
            onConfirmButton = {
                value.setScript(parseCardToInstructionBoxes(boxes))
                code = value.assembleBlock(arithmeticField)
            },
            dialogContent = {
                value.addScopeToContext()
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End)
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        InnerCreationButton(showInnerBoxesState, modifier = Modifier.fillMaxWidth())
                    }
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        VariableTextField(onValueChange = { newText ->
                            arithmeticField = newText
                        }, value = arithmeticField, modifier = Modifier)
                    }
                    VerticalReorderList(boxes)
                    if (showInnerBoxesState.value) {
                        CreateBoxesDialog(showInnerBoxesState, boxes)
                    }
                }
            }, onCloseDialog = {
            }, onDelete = {
                value.removeBlock()
                externalBoxes.removeAll { it.id == id }
            }) {
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
}