package com.example.scratchinterpretermobile.Model

fun main() {
    val scope = UIContext.peekScope()

    val initBlock = InitBlock()
    val assignmentBlock = AssignmentBlock()

    initBlock.initIntegerBlock("var")
    assignmentBlock.assignIntegerBlock("var", "var + 1")

    assignmentBlock.removeBlock()

    assignmentBlock.run()
    assignmentBlock.run()
}
