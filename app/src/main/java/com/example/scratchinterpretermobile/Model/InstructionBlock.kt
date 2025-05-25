package com.example.scratchinterpretermobile.Model

abstract class InstructionBlock{
    var context: Context = mainContext
    abstract fun run(): Int
}