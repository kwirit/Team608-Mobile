package com.example.scratchinterpretermobile.Model

abstract class InstructionBlock{
    var context: Context = UIContext
    abstract fun run(): Int
}