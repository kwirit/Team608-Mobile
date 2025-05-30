package com.example.scratchinterpretermobile.Model

import androidx.compose.runtime.mutableStateListOf

class Context {
    private val context = Stack<HashMap<String, VarBlock<*>>>().apply {
        push(HashMap())
    }

    /**
     * Проверяет тип переменной
     * @param key строка, которая является именем переменной
     * @return Boolean - true (переменная принадлежит Int)
     */
    fun isInt(key: String): Boolean {
        val value = getVar(key)
        if (value is IntegerBlock)
            return true;
        return false
    }
    /**
     * Проверяет тип переменной
     * @param key строка, которая является именем переменной
     * @return Boolean - true (переменная принадлежит IntArray)
     */
    fun isArrayInt(key: String): Boolean {
        val value = getVar(key)
        if (value is IntegerArrayBlock)
            return true;
        return false
    }

    fun getVar(key: String): VarBlock<*>? {
        for (scope in context) {
            if (scope.containsKey(key)) {
                return scope[key]
            }
        }
        return null
    }
    fun setVar(key: String, varBlock: VarBlock<*>) {
        for (scope in context) {
            if (scope.containsKey(key)) {
                scope[key] = varBlock
                return
            }
        }
        // Или добавляем в верхний скоуп, если не нашли
        context.peek()?.set(key, varBlock)
    }

    fun removeVar(key: String) {
        for(scope in context) {
            if(scope.containsKey(key)) {
                scope.remove(key)
                break
            }
        }

        return;
    }

    fun pushScope(scope: HashMap<String, VarBlock<*>> = HashMap()) = context.push(scope)
    fun popScope(): HashMap<String, VarBlock<*>>? = context.pop()
    fun peekScope(): HashMap<String, VarBlock<*>>? = context.peek()
    fun removeScope(scope: HashMap<String, VarBlock<*>>) {
        for (item in context.toReversedList()) {
            context.pop()
            if (item == scope) {
                break
            }
        }
    }

    fun clear() = context.clear()

    fun getListOfIntVariable(): MutableList<String> {
        val result = mutableListOf<String>()
        for (scope in context) {
            for ((key, value) in scope) {
                if (value is IntegerBlock) {
                    result.add(key)
                }
            }
        }
        return result
    }
    fun getListOfIntArrayVariable(): MutableList<String> {
        val result = mutableListOf<String>()
        for (scope in context) {
            for ((key, value) in scope) {
                if (value is IntegerArrayBlock) {
                    result.add(key)
                }
            }
        }
        return result
    }

    fun GetListVarBlock(): MutableList<VarBlock<*>> {
        val result = mutableListOf<VarBlock<*>>();
        for(scope in context) {
            for((key, value ) in scope) {
                result.add(value)
            }
        }

        return result
    }
}