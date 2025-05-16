package com.example.scratchinterpretermobile.View

import androidx.compose.runtime.Composable
import com.example.scratchinterpretermobile.Model.InstructionBlock
import java.util.UUID

abstract class ProgramBox() {
    val id: String = UUID.randomUUID().toString()
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