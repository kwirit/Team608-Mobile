package com.example.scratchinterpretermobile.Model

fun main() {
    val scope = UIContext.peekScope()


    val initBlock1 = InitBlock()
    val assignmentBlock = AssignmentBlock()
    val loopBlock = LoopBlock()

    initBlock1.initIntegerBlock("a")
    assignmentBlock.assignIntegerBlock("a", "a+1")

    loopBlock.processInput("a", "10", "<", mutableListOf(assignmentBlock))

}
