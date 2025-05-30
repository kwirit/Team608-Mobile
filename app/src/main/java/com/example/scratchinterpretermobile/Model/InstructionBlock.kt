package com.example.scratchinterpretermobile.Model


interface InstructionBlock{
    var context: Context
    fun removeBlock()
    fun run(): Int
}