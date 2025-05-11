package com.example.scratchinterpretermobile.Model

class InitBlock: InstructionBlock() {
    var variables = ArrayList<VarBlock>() // Переменные которые будут добавлены в контекст

    fun processInput(usersInput:String): Int {
        //
        return 0
    }

    override fun run(): Int {
        //
        return 0
    }
}