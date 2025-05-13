package com.example.scratchinterpretermobile.View

import com.example.scratchinterpretermobile.Model.Variable

sealed class DraggedBlock {
    object Initialization : DraggedBlock()
    data class Assigning(val variables: MutableList<Variable>) : DraggedBlock()
    object If : DraggedBlock()
    object Console : DraggedBlock()
}