package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.Model.ConditionsBlock
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Widgets.InnerCreationButton
import com.example.scratchinterpretermobile.View.Widgets.VerticalReorderList

class IfBox: ProgramBox() {
    val boxes = mutableStateListOf<ProgramBox>()
    override val value = ConditionsBlock();
    val showInnerBoxesState = mutableStateOf(false)
    @Composable
    override fun render(){
        BaseBox(name = "Условие", showState,
            onConfirmButton = {

        },
            dialogContent = {
                Column {
                    InnerCreationButton(showInnerBoxesState)
                    VerticalReorderList(boxes)
                    if(showInnerBoxesState.value){
                        CreateBoxesDialog(showInnerBoxesState,boxes)
                    }
                }
        }) {
        }
    }
}