package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Model.AssignmentBlock
import com.example.scratchinterpretermobile.Model.BooleanBlock
import com.example.scratchinterpretermobile.Model.IntegerArrayBlock
import com.example.scratchinterpretermobile.Model.IntegerBlock
import com.example.scratchinterpretermobile.Model.StringBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.Model.VarBlock
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Widgets.ListOfVar
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField
import com.example.scratchinterpretermobile.View.Widgets.ListOfTypes
import com.example.scratchinterpretermobile.ui.theme.innerText

class AssigningBox(externalBoxes: MutableList<ProgramBox>) : ProgramBox(externalBoxes) {

    override val value = AssignmentBlock(UIContext)
    private val checkVariableState = mutableStateOf(true)
    private val checkArrayState = mutableStateOf(false)
    private val arrayListField = mutableStateListOf<String>()
    private var arithmeticField by mutableStateOf("")
    private var arrayIndex by mutableStateOf("")
    private val state = mutableIntStateOf(-1)
    private val selectedVariable = mutableStateOf<VarBlock<*>?>(null)

    @Composable
    override fun render() {
        BaseBox(
            name = stringResource(R.string.assign),
            showState,
            onConfirmButton = handleConfirmButton,
            dialogContent = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        renderVariableSelector()
                        renderDialogContent()
                    }
                }
            },
            onDelete = {
                value.removeBlock()
                externalBoxes.removeAll { it.id == id }
            }
        ) {
            renderResultDisplay()
        }
    }

    private @Composable fun renderVariableSelector() {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ListOfVar(selectedVariable)
            if (selectedVariable.value is IntegerArrayBlock) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    ArrayAssignment()
                }
            }
        }
    }

    private @Composable fun renderDialogContent() {
        when {
            selectedVariable.value is IntegerBlock -> renderIntegerBlockInputs()
            selectedVariable.value is IntegerArrayBlock -> {
                if (checkVariableState.value) renderArraySingleElementInputs()
                else renderArrayAllElementsInputs()
            }
            selectedVariable.value is StringBlock -> renderStringBlockInputs()
            selectedVariable.value is BooleanBlock -> renderBooleanBlockInputs()
        }
    }

    private @Composable fun renderIntegerBlockInputs() {
        state.value = 0
        Text(stringResource(R.string.value) + ":")
        VariableTextField(
            onValueChange = { arithmeticField = it },
            value = arithmeticField
        )
    }
    private @Composable fun renderStringBlockInputs() {
        state.value = 3
        Text(stringResource(R.string.value) + ":")
        VariableTextField(
            onValueChange = { arithmeticField = it },
            value = arithmeticField
        )
    }
    private @Composable fun renderBooleanBlockInputs() {
        state.value = 4
        Text(stringResource(R.string.value) + ":")
        VariableTextField(
            onValueChange = { arithmeticField = it },
            value = arithmeticField
        )
    }

    private @Composable fun renderArraySingleElementInputs() {
        state.value = 1
        Text(stringResource(R.string.index) + ":")
        VariableTextField(
            onValueChange = { arrayIndex = it },
            value = arrayIndex
        )
        Text(stringResource(R.string.value) + ":")
        VariableTextField(
            onValueChange = { arithmeticField = it },
            value = arithmeticField
        )
    }

    private @Composable fun renderArrayAllElementsInputs() {
        state.value = 2
        val arraySize = (selectedVariable.value as? IntegerArrayBlock)?.getValue()?.size ?: 0
        if (arrayListField.size != arraySize) {
            arrayListField.clear()
            repeat(arraySize) {
                arrayListField.add("")
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(0.9f)
        ) {
            items(arraySize) { index ->
                Column {
                    Text(stringResource(R.string.element) + " $index")
                    VariableTextField(
                        onValueChange = { newText ->
                            arrayListField[index] = newText
                        },
                        value = arrayListField[index]
                    )
                }
            }
        }
    }

    private val handleConfirmButton: () -> Unit = {
        when (state.value) {
            0 -> code = value.assembleIntegerBlock(selectedVariable.value!!.getName(), arithmeticField)
            1 -> {
                if (arrayIndex.isEmpty()) {
                    code = value.assembleIntegerArrayBlock(selectedVariable.value!!.getName(), arithmeticField)
                } else {
                    code = value.assembleElementIntegerArrayBlock(
                        selectedVariable.value!!.getName(),
                        arrayIndex,
                        arithmeticField
                    )
                }
            }
            2 -> {
                for ((index, field) in arrayListField.withIndex()) {
                    code = value.assembleElementIntegerArrayBlock(
                        selectedVariable.value!!.getName(),
                        index.toString(),
                        field
                    )
                    value.run()
                }
            }
            3 -> {}
            4 -> {}
        }
    }

    @Composable
    private fun ArrayAssignment() {
        Row(modifier = Modifier.padding(start = 40.dp)) {
            Column(modifier = Modifier.width(80.dp)) {
                Text(stringResource(R.string.single_element))
                Checkbox(
                    checked = checkVariableState.value,
                    onCheckedChange = {
                        checkVariableState.value = it
                        if (it) checkArrayState.value = false
                    }
                )
            }
            Column(modifier = Modifier.width(80.dp)) {
                Text(stringResource(R.string.all_elements))
                Checkbox(
                    checked = checkArrayState.value,
                    onCheckedChange = {
                        checkArrayState.value = it
                        if (it) checkVariableState.value = false
                    }
                )
            }
        }
    }

    @Composable
    private fun renderResultDisplay() {
        Column(
            Modifier
                .fillMaxHeight()
                .width(230.dp)
        ) {
            if (code == 0) {
                when (state.value) {
                    0 -> Text(text = "${selectedVariable.value!!.getName()} = $arithmeticField")
                    1 -> Text(text = "${selectedVariable.value!!.getName()}[$arrayIndex] = $arithmeticField")
                    2 -> {
                        for ((index, field) in arrayListField.withIndex()) {
                            Text(text = "$index: ${field.ifBlank { "0" }}")
                        }
                    }
                }
            } else {
                val error = ErrorStore.get(code)!!
                Text(text = error.description, lineHeight = 12.sp, fontSize = 8.sp)
                Text(text = error.category, lineHeight = 12.sp, fontSize = 8.sp)
                Text(text = error.title, lineHeight = 12.sp, fontSize = 8.sp)
            }
        }
    }
}