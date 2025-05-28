package com.example.scratchinterpretermobile.Model

fun main() {
    val scope = UIContext.peekScope()


    val initBlock1 = InitBlock()
    initBlock1.initIntegerArrayBlock("ar", "5")

    val initBlock2 = InitBlock()
    initBlock2.initIntegerBlock("i")

    val assignmentBlock1 = AssignmentBlock()
    val assignmentBlock2 = AssignmentBlock()
    val loopBlock = LoopBlock()

    assignmentBlock1.assignElementIntegerArrayBlock("ar","i","i")
    assignmentBlock2.assignIntegerBlock("i", "i + 1")

    val mutableList = mutableListOf<InstructionBlock>()
    mutableList.add(assignmentBlock1)
    mutableList.add(assignmentBlock2)

    loopBlock.processInput("i", "5", "<", mutableList)





//    val initBlock1 = InitBlock()
//    initBlock1.initIntegerArrayBlock("ar", "5")
//
//    val assignmentBlock1 = AssignmentBlock()
//    assignmentBlock1.assignIntegerArrayBlock("ar", "5,4,3,2,1")
//
//    val initBlock2 = InitBlock()
//    initBlock2.initIntegerBlock("i")
//
//    val loopBlock1 = LoopBlock()
//    // -------------------------
//    val initBlock3 = InitBlock()
//    initBlock3.initIntegerBlock("j")
//    val loopBlock2 = LoopBlock()
//        //--------------------------
//        val conditionBlock = ConditionBlock()
//            //--------------------------
//            val assignmentBlock2 = AssignmentBlock()
//            assignmentBlock2.assignElementIntegerArrayBlock("ar","j","ar[i]")
//
//            val conditionBlocks = mutableListOf<InstructionBlock>()
//            conditionBlocks.add(assignmentBlock2)
//            //--------------------------
//        val assignmentBlock3 = AssignmentBlock()
//        assignmentBlock3.assignIntegerBlock("j","j+1")
//
//        val loop2Blocks = mutableListOf<InstructionBlock>()
//        loop2Blocks.add(conditionBlock)
//        loop2Blocks.add(assignmentBlock3)
//        //--------------------------
//    val assignmentBlock4 = AssignmentBlock()
//    assignmentBlock4.assignIntegerBlock("i", "i+1")
//
//    val loop1Blocks = mutableListOf<InstructionBlock>()
//    loop1Blocks.add(initBlock3)
//    loop1Blocks.add(loopBlock2)
//    loop1Blocks.add(assignmentBlock4)
//    //--------------------------
//    loopBlock2.processInput("j","5","<",loop2Blocks)
//    loopBlock1.processInput("i","5","<",loop1Blocks)
}
