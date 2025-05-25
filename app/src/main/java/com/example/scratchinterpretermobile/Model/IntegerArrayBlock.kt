package com.example.scratchinterpretermobile.Model

class IntegerArrayBlock(
    private var name:String,
    private var value:MutableList<Int>
): VarBlock<MutableList<Int>> {

    override fun getName(): String {
        return name
    }

    override fun setName(newName: String) {
        name = newName
    }

    override fun getValue(): MutableList<Int> {
        return value
    }

    override fun setValue(newValue: MutableList<Int>) {
        value = newValue
    }

    override fun getCopy(): IntegerArrayBlock {
        val originalName = name
        val originalValue = value

        return IntegerArrayBlock(originalName, originalValue.toMutableList())
    }

}