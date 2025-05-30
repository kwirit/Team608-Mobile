package com.example.scratchinterpretermobile.Controller.Error

data class Error(
    val id: Int,
    val title: String,
    val description: String,
    val category: String
)

// 00
val SUCCESS = Error(
    0,
    "success",
    "success",
    "systemic"
)

val CONTEXT_IS_NULL = Error (
    1,
    "error",
    "Context is null",
    "systemic"
)

val CLASS_DOESNT_NOT_EXIST = Error(
    2,
    "The class does not exist",
    "The class does not exist",
    "systemic"
)

val RUNTIME_ERROR = Error(
    3,
    "Runtime error",
    "The instruction block does nothing",
    "systemic"
)

//100
val INVALID_VARIABLE_START = Error(
    101,
    "Invalid start of variable name",
    "The variable name must start with an English letter or an underscore.",
    "naming"
)
val VARIABLE_HAS_SPACE = Error(
    102,
    "Space in variable name",
    "The variable name must not contain spaces",
    "naming"
)
val INVALID_CHARACTERS = Error(
    103,
    "Invalid character in variable name",
    "The variable must contain english letters underscores and numbers.",
    "naming"
)
val EMPTY_NAME = Error(
    104,
    "Empty variable name",
    "The variable name cannot be empty.",
    "naming"
)

val INCORRECT_ARRAY_ELEMENT_NAME = Error(
    105,
    "Invalid array name",
    "The array element name is invalid.",
    "naming"
)

//200

val REINITIALIZE_VARIABLE = Error (
    201,
    "Reinitialize variable",
    "The variable is already declared in the context",
    "initialization"
)


val INITIALIZATION_ERROR = Error(
    202,
    "Initialization error",
    "The initialization of variable is incorrect.",
    "initialization"
)

val ASSIGNING_DIFFERENT_TYPES = Error(
    203,
    "Assigning different types",
    "Assigning different data types is prohibited.",
    "initialization"
)

// 300

val INCORRECT_ARITHMETIC_EXPRESSION = Error(
    301,
    "Invalid arithmetic expression",
    "Invalid notation of arithmetic expression.",
    "arithmeticExpression"
)

val DIVISION_BY_ZERO = Error(
    302,
    "Division by zero",
    "An attempt was made to divide by zero during evaluation.",
    "arithmetic"
)

val EMPTY_ARITHMETIC = Error(
    303,
    "Empty arithmetic",
    "Empty arithmetic",
    "arithmetic"
)

//400
val UNMATCHED_PARENTHESES = Error(
    406,
    "Unmatched parentheses",
    "The expression contains unmatched or mismatched parentheses.",
    "Check that all opening '(' and closing ')' parentheses are properly paired."
)

val INVALID_ARRAY_ACCESS = Error(
    407,
    "Invalid array access",
    "The array name or index expression is invalid.",
    "array"
)

val ARRAY_NOT_FOUND = Error(
    408,
    "Array not found",
    "The requested array does not exist in the current context.",
    "array"
)
val ARRAY_BOUNDS_ERROR = Error(
    409,
    "Array index out of bounds",
    "The index used to access the array is out of its valid range.",
    "array"
)
val ARRAY_EXPECTED = Error(
    410,
    "Array expected",
    "A variable used as an array is not declared as an array.",
    "array"
)

val ARRAY_INVALID_ELEMENT = Error(
    411,
    "Invalid array element type",
    "The array contains elements of an unexpected type.",
    "array"
)

val MULTIPLE_INITIALIZATION = Error(
    412,
    "Multiple initialization error",
    "Multiple initialization of arrays is not allowed",
    "array"
)
val INVALID_ARRAY_LENGTH = Error(
    413,
    "Invalid array length",
    "The length of the array must be a natural number.",
    "array"
)

// 500
val VARIABLE_IS_NOT_INITIALIZED = Error(
    500,
    "The variable is not initialized",
    "The variable does not exist in the context",
    "assignment"
)

val INVALID_ASSIGNMENT_INTEGER = Error(
    501,
    "Incorrect assignment to integer variable",
    "A syntax error has occurred",
    "assignment"
)

val INVALID_ASSIGNMENT_ARRAY = Error(
    502,
    "Does not match the array size",
    "The number of assigned values does not match the size of the array",
    "assignment"
)

val INVALID_ARRAY_ELEMENT_ASSIGNMENT = Error(
    503,
    "Invalid array element assignment",
    "Syntax error assigning array element.",
    "assignment"
)

val INVALID_ARRAY_INDEX = Error(
    504,
    "Invalid array index",
    "The element index is out of array bounds",
    "assignment"
)

val ASSIGNMENT_ERROR = Error(
    505,
    "Assignment error",
    "The block does not assign anything",
    "assignment"
)

// 600
val VARIABLE_NOT_FOUND = Error(
    601,
    "Variable not found",
    "The requested variable was not found in the current scope",
    "variable"
)

val INVALID_CHARACTERS_IN_STRING = Error(
    701,
    "Invalid characters in string",
    "The string contains disallowed or unsupported characters.",
    "string"
)
val INVALID_FORMAT = Error(
    702,
    "Invalid string format",
    "The string is not enclosed in double quotes or has incorrect structure.",
    "string"
)
val NO_COMPARISON_OPERATOR_SELECTED = Error(
    801,
    "No comparison operator selected",
    "A comparison operator must be selected for this operation.",
    "comparison"
)
val TYPE_MISMATCH = Error(
    901,
    "Type mismatch",
    "The types of the operands are not compatible for this operation.",
    "type"
)
val INVALID_BOOLEAN = Error(
    1001,
    "Invalid boolean value",
    "A value was expected to be a valid boolean ('true' or 'false'), but it was not.",
    "boolean"
)


object ErrorStore {
    private val errorMap = mapOf(
        0 to SUCCESS,
        1 to CONTEXT_IS_NULL,
//        2 to CLASS_DOESNT_NOT_EXIST,
        3 to RUNTIME_ERROR,

        101 to INVALID_VARIABLE_START,
        102 to VARIABLE_HAS_SPACE,
        103 to INVALID_CHARACTERS,
        104 to EMPTY_NAME,
        105 to INCORRECT_ARRAY_ELEMENT_NAME,

        201 to REINITIALIZE_VARIABLE,
        202 to INITIALIZATION_ERROR,
        203 to ASSIGNING_DIFFERENT_TYPES,

        301 to INCORRECT_ARITHMETIC_EXPRESSION,
        302 to DIVISION_BY_ZERO,
        303 to EMPTY_ARITHMETIC,

        406 to UNMATCHED_PARENTHESES,
        407 to INVALID_ARRAY_ACCESS,
        408 to ARRAY_NOT_FOUND,
        409 to ARRAY_BOUNDS_ERROR,
        410 to ARRAY_EXPECTED,
        411 to ARRAY_INVALID_ELEMENT,
        412 to MULTIPLE_INITIALIZATION,
        413 to INVALID_ARRAY_LENGTH,

        500 to VARIABLE_IS_NOT_INITIALIZED,
//        501 to INVALID_ASSIGNMENT_INTEGER,
        502 to INVALID_ASSIGNMENT_ARRAY,
//        503 to INVALID_ARRAY_ELEMENT_ASSIGNMENT
        504 to INVALID_ARRAY_INDEX,
        505 to ASSIGNMENT_ERROR,

        601 to VARIABLE_NOT_FOUND,

        701 to INVALID_CHARACTERS_IN_STRING,
        702 to INVALID_FORMAT,

        801 to NO_COMPARISON_OPERATOR_SELECTED,

        901 to TYPE_MISMATCH,

        1001 to INVALID_BOOLEAN,
    )

    fun get(id: Int): Error? = errorMap[id]
}