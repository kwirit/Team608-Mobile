package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.calculationArithmeticExpression

fun test() {
    val initBlock = InitBlock()
    val assignmentBlock = AssignmentBlock()

    initBlock.processInput("ar", "10")
    assignmentBlock.processInput("ar", "1,2,3,4,5,6,7,8,9,10")
}


fun main() {
    val printBlock = PrintBlock();
    printBlock.processInput("x")
    val scope = hashMapOf<String, VarBlock<*>>()

    scope["x"] = IntegerBlock("x", 10)
    scope["y"] = IntegerBlock("y", 5)
    scope["z"] = IntegerBlock("z", 1)
    scope["a"] = IntegerArrayBlock("a", mutableListOf(1, 2, 3, 4, 5))

    interpreterContext.pushScope(scope)

    printBlock.run();
}
