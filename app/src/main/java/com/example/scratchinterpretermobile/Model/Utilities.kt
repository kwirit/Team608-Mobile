package com.example.scratchinterpretermobile.Model

var mainContext = Context()
var interpreterContext = Context()

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


/*
    -1 - ошибка
    0 - IntegerBlock
    1 - IntegerArrayBlock
 */
fun GetTypeVarBlock(nameVarBlock:String): Int {
    val varBlock = mainContext.getVar(nameVarBlock);

    if(varBlock is IntegerBlock) return 0
    else if(varBlock is IntegerArrayBlock) return 1
    else return -1
}