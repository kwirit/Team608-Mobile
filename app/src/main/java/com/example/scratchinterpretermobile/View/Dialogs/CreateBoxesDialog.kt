package com.example.scratchinterpretermobile.View.Dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.scratchinterpretermobile.View.Boxes.ArrayInitializationBox
import com.example.scratchinterpretermobile.View.Boxes.AssigningBox
import com.example.scratchinterpretermobile.View.Boxes.ConsoleBox
import com.example.scratchinterpretermobile.View.Boxes.IfBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.Boxes.TypeConversionBlock
import com.example.scratchinterpretermobile.View.Boxes.VariableInitializationBox
import com.example.scratchinterpretermobile.View.Boxes.WhileBox
import com.example.scratchinterpretermobile.View.Cards.ArrayInitializationCard
import com.example.scratchinterpretermobile.View.Cards.AssigningCard
import com.example.scratchinterpretermobile.View.Cards.ConsoleCard
import com.example.scratchinterpretermobile.View.Cards.IfCard
import com.example.scratchinterpretermobile.View.Cards.TypeConversionCard
import com.example.scratchinterpretermobile.View.Cards.VariableInitializationCard
import com.example.scratchinterpretermobile.View.Cards.WhileCard

@Composable
fun CreateBoxesDialog(showBoxesState: MutableState<Boolean>, boxes: MutableList<ProgramBox>) {
    CustomDialog(showBoxesState) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            VariableInitializationCard(
                onAdd = { boxes.add(VariableInitializationBox(boxes)) },
                showBoxesState
            )
            AssigningCard(onAdd = { boxes.add(AssigningBox(boxes)) }, showBoxesState)
            IfCard(onAdd = { boxes.add(IfBox(boxes)) }, showBoxesState)
            WhileCard(onAdd = { boxes.add(WhileBox(boxes)) }, showBoxesState)
            ConsoleCard(onAdd = { boxes.add(ConsoleBox(boxes)) }, showBoxesState)
            ArrayInitializationCard(
                onAdd = { boxes.add(ArrayInitializationBox(boxes)) },
                showBoxesState
            )
            TypeConversionCard(onAdd = { boxes.add(TypeConversionBlock(boxes)) }, showBoxesState)
        }
    }
}