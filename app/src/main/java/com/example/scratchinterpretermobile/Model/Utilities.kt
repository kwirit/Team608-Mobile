package com.example.scratchinterpretermobile.Model

//object Context {
//    var context = Stack<HashMap<String, VarBlock>>()
//
//    fun hasKey(key:String): Boolean {
//        var result = false
//        for(scope in context) {
//            if(scope.containsKey(key)) {
//                result = true
//                break
//            }
//        }
//
//        return result
//    }
//}

object Context {
    private val context = Stack<HashMap<String, VarBlock>>()

    fun getVar(key: String): VarBlock? {
        for (scope in context) {
            if (scope.containsKey(key)) {
                return scope[key]
            }
        }
        return null
    }

    fun pushScope(scope: HashMap<String, VarBlock> = HashMap()) = context.push(scope)
    fun popScope(): HashMap<String, VarBlock>? = context.pop()
    fun peekScope(): HashMap<String, VarBlock>? = context.peek()
}


class Stack<T> : Iterable<T> {
    private val elements = mutableListOf<T>()

    fun push(item: T) = elements.add(item)
    fun pop(): T? = if (isEmpty()) null else elements.removeAt(elements.size - 1)
    fun peek(): T? = elements.lastOrNull()
    fun isEmpty() = elements.isEmpty()
    fun size() = elements.size
    fun clear() = elements.clear()

    // Возвращает первый элемент стека (дно стека)
    fun top(): T? = elements.firstOrNull()

    // Итерация
    override fun iterator(): Iterator<T> = elements.iterator()

    // Дополнительные методы итерации
    fun forEach(action: (T) -> Unit) = elements.forEach(action)
    fun forEachReversed(action: (T) -> Unit) = elements.asReversed().forEach(action)

    // Преобразование
    fun toList(): List<T> = elements.toList()
    fun toReversedList(): List<T> = elements.asReversed()
}