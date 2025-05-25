package com.example.scratchinterpretermobile.Model

class StringBlock(
    private var name:String,
    private var value: String
) : VarBlock<String> {
    override fun getName(): String {
        return this.name
    }

    override fun setName(newName: String) {
        this.name = newName
    }

    override fun getValue(): String {
        return this.value
    }

    override fun setValue(newValue: String) {
        this.value = newValue
    }

    fun getCopy(): StringBlock {
        val originalName = name
        val originalValue = value

        return StringBlock(originalName, originalValue)
    }
}