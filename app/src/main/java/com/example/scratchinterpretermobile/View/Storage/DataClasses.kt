package com.example.scratchinterpretermobile.View.Storage

import com.example.scratchinterpretermobile.View.Boxes.ArrayInitializationBox
import com.example.scratchinterpretermobile.View.Boxes.AssigningBox
import com.example.scratchinterpretermobile.View.Boxes.ConsoleBox
import com.example.scratchinterpretermobile.View.Boxes.IfBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.Boxes.VariableInitializationBox
import com.google.gson.annotations.SerializedName

data class SavedProject(
    val name: String,
    val boxLists: List<List<ProgramBox>>
)

data class SavedProjectDTO(
    @SerializedName("name") val name: String,
    @SerializedName("boxLists") val boxLists: List<List<ProgramBoxDTO>>
)

fun SavedProject.toDTO(): SavedProjectDTO {
    return SavedProjectDTO(
        name = name,
        boxLists = boxLists.map { list -> list.map { it.toDTO() } }
    )
}


sealed class ProgramBoxDTO {
    abstract val id: String
    abstract val type: String
}

data class VariableInitializationBoxDTO(
    @SerializedName("id") override val id: String,
    @SerializedName("text") val text: String = ""
) : ProgramBoxDTO() {
    override val type: String = "VariableInitialization"
}

data class AssigningBoxDTO(
    @SerializedName("id") override val id: String,
    @SerializedName("selectedVariableName") val selectedVariableName: String = "",
    @SerializedName("arithmeticField") val arithmeticField: String = "",
    @SerializedName("arrayIndex") val arrayIndex: String = "",
    @SerializedName("arrayListField") val arrayListField: List<String> = emptyList()
) : ProgramBoxDTO() {
    override val type: String = "Assigning"
}

data class IfBoxDTO(
    @SerializedName("id") override val id: String,
    @SerializedName("leftOperand") val leftOperand: String = "",
    @SerializedName("rightOperand") val rightOperand: String = "",
    @SerializedName("operator") val operator: String = "",
    @SerializedName("ifBoxes") val ifBoxes: List<ProgramBoxDTO> = emptyList(),
    @SerializedName("elseBoxes") val elseBoxes: List<ProgramBoxDTO> = emptyList()
) : ProgramBoxDTO() {
    override val type: String = "If"
}

data class ConsoleBoxDTO(
    @SerializedName("id") override val id: String,
    @SerializedName("arithmeticField") val arithmeticField: String = "",
    @SerializedName("result") val result: String = ""
) : ProgramBoxDTO() {
    override val type: String = "Console"
}

data class ArrayInitializationBoxDTO(
    @SerializedName("id") override val id: String,
    @SerializedName("arrayName") val arrayName: String = "",
    @SerializedName("arraySize") val arraySize: String = ""
) : ProgramBoxDTO() {
    override val type: String = "ArrayInitialization"
}

fun ProgramBox.toDTO(): ProgramBoxDTO = when (this) {
    is VariableInitializationBox -> VariableInitializationBoxDTO(id, text)
    is AssigningBox -> AssigningBoxDTO(
        id,
        selectedVariable.value?.getName() ?: "",
        arithmeticField,
        arrayIndex,
        arrayListField.toList()
    )
    is IfBox -> IfBoxDTO(
        id,
        leftOperand,
        rightOperand,
        operator.value,
        ifBoxes.map { it.toDTO() },
        elseBoxes.map { it.toDTO() }
    )
    is ConsoleBox -> ConsoleBoxDTO(id, arithmeticField, result)
    is ArrayInitializationBox -> ArrayInitializationBoxDTO(id, arrayName, arraySize)
    else -> throw IllegalArgumentException("Unknown box type")
}