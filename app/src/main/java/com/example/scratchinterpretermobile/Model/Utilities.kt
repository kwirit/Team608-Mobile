package com.example.scratchinterpretermobile.Model

class Stack<T> {
    private val elements = mutableListOf<T>()

    fun push(item: T) {
        elements.add(item)
    }

    fun pop(): T? {
        if (isEmpty()) return null
        return elements.removeAt(elements.size - 1)
    }

    fun peek(): T? = elements.lastOrNull()

    fun isEmpty(): Boolean = elements.isEmpty()

    fun size(): Int = elements.size
}