package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Controller.validateNameVariable

class AssignmentBlock : InstructionBlock() {
    private val newVariables = mutableListOf<VarBlock>()

    fun processInput(usersInput: String): Int {
        val words = usersInput.split(",").map { it.trim() }
        val regexVariable = Regex("([\\w]+)(?: *\\[(.+)\\])?")

        // влидация
        words.firstOrNull { validateNameVariable(it) != 0 }?.let {
            return validateNameVariable(it)
        }


        words.forEach { word ->
            val match = regexVariable.find(word) ?: return 202

            val name = match.groups[1]?.value?.takeIf { it.isNotEmpty() }
                ?: return 202

            if (Context.hasKey(name)) {
                return 201
            }

            val scope = Context.peekScope() ?: return 1

            when {
                match.groups[2] == null -> {
                    val newIntegerBlock = IntegerBlock(name, 0)
                    newVariables.add(newIntegerBlock)
                    scope[newIntegerBlock.name] = newIntegerBlock
                }
                else -> {
                    //Проверить на корректность арифметического выражения
                    // Посчитать арифметическое выражение

                    val newIntegerArray = IntegerArrayBlock(name, IntArray(arraySize).toList())
                    newVariables.add(newIntegerArray)
                    scope[newIntegerArray.name] = newIntegerArray
                }
            }
        }

        return 0
    }

    override fun run(): Int {
        // Реализация выполнения
        return 0
    }
}