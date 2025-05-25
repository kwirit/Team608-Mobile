package com.example.scratchinterpretermobile.Model

interface InstructionBlock{
    var context: Context
    fun run(): Int
}

//abstract class InstructionBlock{
//    var context: Context = UIContext
//    abstract fun run(): Int
//}