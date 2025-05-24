package com.example.scratchinterpretermobile.Model

abstract class InstructionBlock{
    protected var context: Context = mainContext

    fun setContext(newContext: Context) {
        context = newContext
    }

    abstract fun run(): Int
}