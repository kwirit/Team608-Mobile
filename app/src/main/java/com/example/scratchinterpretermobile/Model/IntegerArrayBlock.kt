package com.example.scratchinterpretermobile.Model

class IntegerArrayBlock(
    override var name: String,
    private var arrayValue: List<Int>
) : VarBlock() {

    override var value: Any
        get() = arrayValue
        set(value) {
            arrayValue = value as List<Int>
        }
}
