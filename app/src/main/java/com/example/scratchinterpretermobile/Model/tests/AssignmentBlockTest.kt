package com.example.scratchinterpretermobile.Model.tests

import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.UIContext

fun main() {
    val scope = UIContext.peekScope()

    val initBlock = InitBlock()
    initBlock.assembleIntegerBlock("var")
    initBlock.run()

    val assignmentBlock = AssignmentBlock()
    assignmentBlock.assembleIntegerBlock("var", "3")
    assignmentBlock.run()

    assignmentBlock.assembleIntegerBlock("var", "7")
    assignmentBlock.run()

    //
    assignmentBlock.assembleIntegerArrayBlock("ar", "1,2,3,4,5")
    assignmentBlock.run()

    //
    initBlock.assembleIntegerArrayBlock("ar", "5")
    initBlock.run()

    assignmentBlock.assembleIntegerArrayBlock("ar", "1,2,3,4,5")
    assignmentBlock.run()

    assignmentBlock.assembleElementIntegerArrayBlock("ar", "3", "-3")
    assignmentBlock.run()
}