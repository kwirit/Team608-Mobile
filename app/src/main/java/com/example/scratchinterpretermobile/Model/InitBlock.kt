package com.example.scratchinterpretermobile.Model

import com.example.scratchinterpretermobile.Controller.calculationPostfix
import com.example.scratchinterpretermobile.Controller.getElementFromString
import com.example.scratchinterpretermobile.Controller.transferPrefixToPostfix
import com.example.scratchinterpretermobile.Controller.validateNameVariable

class InitBlock : InstructionBlock() {
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

            val scope = Context.peekScope() ?: return 1
            if(Context.getVar(name) != null) {
                return 201
            }

            when {
                match.groups[2] == null -> {
                    val newIntegerBlock = IntegerBlock(name, 0)
                    newVariables.add(newIntegerBlock)
                    scope[newIntegerBlock.name] = newIntegerBlock
                }
                else -> {
                    //Проверить на корректность арифметического выражения
                    // Посчитать арифметическое выражение
                    val prefixTokens = getElementFromString(word)

                    val (postfixTokens, errorTransfer) = transferPrefixToPostfix(prefixTokens)
                    if(errorTransfer != 0) return errorTransfer

                    val (arifmeticResult, arifmeticError) = calculationPostfix(postfixTokens)
                    if(arifmeticError != 0) return arifmeticError

                    val newIntegerArrayBlock = IntegerArrayBlock(name, List<Int>(arifmeticResult){0})
                    newVariables.add(newIntegerArrayBlock)
                    scope[newIntegerArrayBlock.name] = newIntegerArrayBlock
                }
            }
        }

        return 0
    }

    override fun run(): Int {
        for(newVar in newVariables) {
            var scope = Context.peekScope()
            scope!!.put(newVar.name, newVar)
        }
        
        return 0
    }
}