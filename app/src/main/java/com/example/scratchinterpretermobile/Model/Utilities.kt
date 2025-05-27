package com.example.scratchinterpretermobile.Model

var UIContext = Context()
//var interpreterContext = Context()

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

fun rollbackActions(instructionBlocks:MutableList<InstructionBlock>) {
    for(instructionBlock in instructionBlocks) {
        instructionBlock.removeBlock()
    }

    return
}

fun rollActions(instructionBlocks: MutableList<InstructionBlock>) {
    for(instructionBlock in instructionBlocks) {
        instructionBlock.run()
    }

    return
}