package com.example.scratchinterpretermobile.Model

class IntegerArrayBlock(
    override var name: String,
    private var arrayValue: MutableList<Int>
) : VarBlock() {

    override var value: Any
        get() = arrayValue
        set(value) {
            arrayValue = value as MutableList<Int>
        }
}

fun getCopyIntegerArrayBlock(varBlock: IntegerArrayBlock): IntegerArrayBlock {
    val name = varBlock.name
    val original = varBlock.value as MutableList<Int>

    val copyList = original.toMutableList()

    return IntegerArrayBlock(name, copyList)
}