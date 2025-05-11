package com.example.scratchinterpretermobile.Model

class IntegerBlock(
    override var name: String,
    private var intValue: Int
) : VarBlock() {
    override var value: Any
        get() = intValue
        set(value) {
            intValue = value as Int
        }
}