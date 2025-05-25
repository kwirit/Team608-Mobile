package com.example.scratchinterpretermobile.Model

interface VarBlock<T>{
    fun getName(): String
    fun setName(newName: String)
    fun getValue(): T
    fun setValue(newValue: T)
    fun getCopy(): VarBlock<T>
}