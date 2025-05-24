package com.example.scratchinterpretermobile.Model

class IntegerBlock(
    private var name:String,
    private var value:Int
): VarBlock<Int> {

    override fun getName(): String {
        return name
    }

    override fun setName(newName: String) {
        name = newName
    }

    override fun getValue(): Int {
        return value
    }

    override fun setValue(newValue: Int) {
        value = newValue
    }

    fun getCopy(): IntegerBlock {
        val originalName = name
        val originalValue = value

        return IntegerBlock(originalName, originalValue)
    }

}