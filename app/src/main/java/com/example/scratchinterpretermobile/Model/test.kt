package com.example.scratchinterpretermobile.Model

fun main() {
    val initBlock = InitBlock()
    val assignmentBlock = AssignmentBlock()

    initBlock.processInput("ar", "10")
    assignmentBlock.processInput("ar", "1,2,3,4,5,6,7,8,9,10")
}