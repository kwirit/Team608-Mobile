package com.example.scratchinterpretermobile.View

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.scratchinterpretermobile.Model.InstructionBlock

class MainViewModel :ViewModel() {

    var isCurrentlyDragging by mutableStateOf(false)
        private set

    var items by mutableStateOf(emptyList<InstructionBlock>())
        private set

    var addedPersons = mutableStateListOf<InstructionBlock>()
        private set

    fun startDragging(){
        isCurrentlyDragging = true
    }
    fun stopDragging(){
        isCurrentlyDragging = false
    }

    fun addPerson(personUiItem: InstructionBlock){
        println("Added Person")
        addedPersons.add(personUiItem)
    }

}