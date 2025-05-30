package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.scratchinterpretermobile.Model.InstructionBlock
import java.util.UUID

abstract class ProgramBox(var externalBoxes: MutableList<ProgramBox>) {
    val showState = mutableStateOf(true)
    val id: String = UUID.randomUUID().toString()
    var code by mutableIntStateOf(104)
    abstract val value: InstructionBlock
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProgramBox) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
    @Composable
    abstract fun render()
}