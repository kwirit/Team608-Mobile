package com.example.scratchinterpretermobile.View

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.scratchinterpretermobile.Model.InstructionBlock
import com.example.scratchinterpretermobile.View.Boxes.ArrayInitializationBox
import com.example.scratchinterpretermobile.View.Boxes.AssigningBox
import com.example.scratchinterpretermobile.View.Boxes.ConsoleBox
import com.example.scratchinterpretermobile.View.Boxes.IfBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.Boxes.VariableInitializationBox

class MainViewModel :ViewModel() {
    val boxes = mutableStateListOf<ProgramBox>()

    fun addAssigningBox() {
        boxes.add(AssigningBox())
    }

    fun addIfBox() {
        boxes.add(IfBox())
    }

    fun addConsoleBox() {
        boxes.add(ConsoleBox())
    }

    fun addVariableInitBox() {
        boxes.add(VariableInitializationBox())
    }

    fun addArrayInitBox() {
        boxes.add(ArrayInitializationBox())
    }

    fun parseCardToInstructionBoxes(list: MutableList<ProgramBox>): MutableList<InstructionBlock>{
        var instructionList: MutableList<InstructionBlock> = mutableListOf()
        for (card in list){
            instructionList.add(card.value)
        }
        return instructionList
    }

    fun run(){
        val instructionList: MutableList<InstructionBlock> = parseCardToInstructionBoxes(boxes)
        Log.d("MainViewModel", "Instruction list size: ${instructionList.size}")

        for ((index, instruction) in instructionList.withIndex()) {
            Log.d("MainViewModel", "Instruction[$index] = $instruction")
        }
    }

}