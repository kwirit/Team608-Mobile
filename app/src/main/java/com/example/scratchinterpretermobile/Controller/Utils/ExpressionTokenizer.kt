package com.example.scratchinterpretermobile.Controller.Utils

/**
 * Разделяет строку на элементы по указанному разделителю, игнорируя разделители внутри строк (в кавычках).
 *
 * @param input исходная строка для разбиения
 * @param separator символ-разделитель (по умолчанию — запятая)
 * @return MutableList<String> список обработанных и разбитых элементов
 */
fun parserSplit(input: String, separator: Char = ','): MutableList<String> {
    val trimmedInput = input.trim()

    val elements = mutableListOf<String>()

    var currentToken = StringBuilder()

    var flagString = false

    for (symbol in trimmedInput) {
        if (symbol == separator && !flagString) {
            elements.add(currentToken.toString().trim())
            currentToken.clear()
            continue
        }
        if (symbol == '\"') flagString = !flagString
        currentToken.append(symbol)
    }

    if (currentToken.toString().trim().isNotEmpty()) {
        elements.add(currentToken.toString())
    }

    return elements
}

/**
 * Разбивает арифметическое/строковое выражение на токены, сохраняя структуру.
 * Учитывает строки, операторы и доступ к массивам.
 *
 * @param input исходное выражение
 * @return MutableList<String> список токенов
 */
fun getElementFromString(input: String): MutableList<String> {
    val trimmedInput = input.trim()

    var operators = "+-*%/()!=<>&|"
    val elements = mutableListOf<String>()

    var currentToken = StringBuilder()

    var flagString = false
    var pendingCloseParenthesis = false

    var arrayNestingLevel = 0
    var lastElement: String? = null

    var index = 0;
    while (index < trimmedInput.length) {
        val symbol = trimmedInput[index]
        when {
            symbol == ' ' && !flagString -> {
                index++
                continue
            }
            symbol == '\"' -> flagString = !flagString
            symbol == '[' && !flagString -> arrayNestingLevel++
            symbol == ']' && !flagString -> arrayNestingLevel--
        }
        when {
            flagString || arrayNestingLevel > 0 -> {
                currentToken.append(symbol)
                lastElement = currentToken.toString()
            }

            symbol == '-' && (lastElement == null || lastElement in operators) -> {
                elements += listOf("(", "0", "-")
                lastElement = "-"
                pendingCloseParenthesis = true
            }

            symbol in operators -> {
                if (currentToken.isNotEmpty()) {
                    elements.add(currentToken.toString())
                    if (pendingCloseParenthesis) {
                        elements.add(")")
                        pendingCloseParenthesis = false
                    }
                    currentToken.clear()
                }
                when (symbol) {
                    '>', '<', '=', '!' -> {
                        if (index + 1 < trimmedInput.length && trimmedInput[index + 1] == '=') {
                            elements.add("$symbol=")
                            lastElement = "$symbol="
                            index++
                        } else {
                            elements.add("$symbol")
                            lastElement = "$symbol"
                        }
                    }

                    '&' -> {
                        if (index + 1 < trimmedInput.length && trimmedInput[index + 1] == '&') {
                            elements.add("&&")
                            lastElement = "&&"
                            index++
                        } else {
                            elements.add("&")
                            lastElement = "&"
                        }
                    }

                    '|' -> {
                        if (index + 1 < trimmedInput.length && trimmedInput[index + 1] == '|') {
                            elements.add("||")
                            lastElement = "||"
                            index++
                        } else {
                            elements.add("|")
                            lastElement = "|"
                        }
                    }
                    else -> {
                        elements.add(symbol.toString())
                    }
                }
            }

            else -> {
                currentToken.append(symbol)
                lastElement = currentToken.toString()
            }
        }
        index++
    }

    if (currentToken.isNotEmpty()) {
        elements.add(currentToken.toString())
    }
    if (pendingCloseParenthesis) {
        elements.add(")")
    }

    return elements
}