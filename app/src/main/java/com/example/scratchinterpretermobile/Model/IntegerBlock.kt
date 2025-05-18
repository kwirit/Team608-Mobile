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

fun getCopyIntegerBlock(varBlock: IntegerBlock): IntegerBlock {
    val name = varBlock.name
    val original = varBlock.value as Int

    val copyBlock = IntegerBlock(name, original)

    return copyBlock
}