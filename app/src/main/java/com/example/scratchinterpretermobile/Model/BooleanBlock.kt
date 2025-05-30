package com.example.scratchinterpretermobile.Model

class BooleanBlock(
    private var name: String,
    private var value: Boolean
) : VarBlock<Boolean> {
    override fun getName(): String {
        return this.name
    }

    override fun setName(newName: String) {
        this.name = newName
    }

    override fun getValue(): Boolean {
        return this.value
    }

    override fun setValue(newValue: Boolean) {
        this.value = newValue
    }

    override fun getCopy(): BooleanBlock {
        val originalName = name
        val originalValue = value

        return BooleanBlock(originalName, originalValue)
    }
}