package com.example.scratchinterpretermobile.Model

fun main() {
    val scope = UIContext.peekScope()

    val initBlock = InitBlock()
    initBlock.initIntegerBlock("var")


    //-------------------
    val loopBlock = LoopBlock()
        //-------------------
        val conditionBlock = ConditionBlock()
        val assingnment1Block = AssignmentBlock()
        assingnment1Block.assignIntegerBlock("var", "99")

        val listBlocksToConditions = mutableListOf<InstructionBlock>()
        listBlocksToConditions.add(assingnment1Block)
        conditionBlock.processInput("var","3","==",listBlocksToConditions, mutableListOf())
        //-------------------
    val assignmentBlock = AssignmentBlock()
    assignmentBlock.assignIntegerBlock("var", "var+1")

    val listBlocksToLoop = mutableListOf<InstructionBlock>()
    listBlocksToLoop.add(conditionBlock)
    listBlocksToLoop.add(assignmentBlock)
    loopBlock.processInput("var","10","<", listBlocksToLoop)
    //-------------------

    loopBlock.removeBlock()
    val error = loopBlock.run()
}
