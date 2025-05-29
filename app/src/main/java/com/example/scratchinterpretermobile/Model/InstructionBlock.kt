package com.example.scratchinterpretermobile.Model

interface InstructionBlock{
    var context: Context
    var runResult:Int
    fun removeBlock()
    fun run(): Int
}