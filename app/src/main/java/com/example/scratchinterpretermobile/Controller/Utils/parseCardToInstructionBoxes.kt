package com.example.scratchinterpretermobile.Controller.Utils

import com.example.scratchinterpretermobile.Model.InstructionBlock
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox

fun parseCardToInstructionBoxes(list: MutableList<ProgramBox>): MutableList<InstructionBlock>{
    var instructionList: MutableList<InstructionBlock> = mutableListOf()
    for (card in list){
        instructionList.add(card.value)
    }
    return instructionList
}