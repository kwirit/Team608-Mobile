package com.example.scratchinterpretermobile.View.Dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.scratchinterpretermobile.Model.Variable
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.Cards.AssigningCard
import com.example.scratchinterpretermobile.View.Cards.ConsoleCard
import com.example.scratchinterpretermobile.View.Cards.IfCard
import com.example.scratchinterpretermobile.View.Cards.InitializationCard
import com.example.scratchinterpretermobile.View.MainViewModel

@Composable
fun CreateBoxesDialog(showBoxesState: MutableState<Boolean>, viewModel: MainViewModel, listOfBoxes: MutableList<ProgramBox>){
    val list = mutableListOf<Variable>()
    list.add(Variable("test1",1))
    list.add(Variable("test2",2))
    list.add(Variable("test3",3))
    list.add(Variable("test4",4))
    CustomDialog(showBoxesState){
        InitializationCard(listOfBoxes, showBoxesState)
        AssigningCard(listOfBoxes,showBoxesState,list)
        IfCard(listOfBoxes,showBoxesState)
        ConsoleCard(listOfBoxes,showBoxesState)
    }
}