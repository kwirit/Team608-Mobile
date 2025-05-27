package com.example.scratchinterpretermobile.Model

fun main() {
    val scope = UIContext.peekScope()

    val initBlock1 = InitBlock()
    val initBlock2 = InitBlock()
    val assignmentBlock = AssignmentBlock()

    initBlock1.initIntegerBlock("a,b,c")
    initBlock2.initIntegerArrayBlock("ar", "3")

    assignmentBlock.assignElementIntegerArrayBlock("ar", "0", "1")
    assignmentBlock.assignElementIntegerArrayBlock("ar", "1", "2")
    assignmentBlock.assignElementIntegerArrayBlock("ar", "2", "3")

    assignmentBlock.assignIntegerBlock("a", "7")
}
