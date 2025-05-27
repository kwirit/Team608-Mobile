package com.example.scratchinterpretermobile.Model

fun main() {
    val scope = UIContext.peekScope()

    val initBlock1 = InitBlock()
    val assignmentBlock1 = AssignmentBlock()
    initBlock1.initIntegerBlock("a,b,c")
    assignmentBlock1.assignIntegerBlock("a", "7") // a=7 b=0 c=0

    // Вложенные блоки
    val instructionBlocks:MutableList<InstructionBlock> = mutableListOf()
    val initBlock2 = InitBlock()
    val assignmentBlock2 = AssignmentBlock()
    val assignmentBlock3 = AssignmentBlock()

    initBlock2.initIntegerBlock("d")
    assignmentBlock2.assignIntegerBlock("d", "10")
    assignmentBlock3.assignIntegerBlock("a", "9")

    instructionBlocks.add(initBlock2)
    instructionBlocks.add(assignmentBlock2)
    instructionBlocks.add(assignmentBlock3)

    val conditionBlock1 = ConditionBlock()
    conditionBlock1.processInput("a", "100","<", instructionBlocks, mutableListOf()) // a=7 b=0 c=0
}
