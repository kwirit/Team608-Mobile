package com.example.scratchinterpretermobile.Controller.Error

data class Error(
    val id: Int,
    val title: String,
    val description: String,
    val category: String
)

var SUCCESS = Error(
    0,
    "success",
    "success",
    "systemic"
)

var INVALID_VARIABLE_START = Error(
    101,
    "Invalid start of variable name",
    "The variable name must start with an English letter or an underscore.",
    "naming"
)
var VARIABLE_HAS_SPACE = Error(
    102,
    "Space in variable name",
    "The variable name must not contain spaces",
    "naming"
)
var INVALID_CHARACTERS = Error(
    103,
    "Invalid character in variable name",
    "The variable must contain english letters underscores and numbers.",
    "naming"
)
var EMPTY_NAME = Error(
    104,
    "Empty variable name",
    "The variable name cannot be empty.",
    "naming"
)
var INCORRECT_ARRAY_ELEMENT_NAME = Error(
    105,
    "Invalid array name",
    "The array element name is invalid.",
    "nameing"
)

var INITIALIZATION_ERROR = Error(
    202,
    "Initialization error",
    "The initialization of variable is incorrect.",
    "initialization"
)
var INCORRECT_ARITHMETIC_EXPRESSION = Error(
    301,
    "Invalid arithmetic expression",
    "Invalid notation of arithmetic expression.",
    "arithmeticExpression"
)


object ErrorStore {
    private val errorMap = mapOf(
        0 to SUCCESS,
        101 to INVALID_VARIABLE_START,
        102 to VARIABLE_HAS_SPACE,
        103 to INVALID_CHARACTERS,
        104 to EMPTY_NAME,
        105 to INCORRECT_ARRAY_ELEMENT_NAME,
        202 to INITIALIZATION_ERROR,
        301 to INCORRECT_ARITHMETIC_EXPRESSION
    )

    fun get(id: Int): Error ? = errorMap[id]
}