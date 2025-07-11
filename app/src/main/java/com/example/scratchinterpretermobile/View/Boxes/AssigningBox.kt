package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    private @Composable
    fun renderVariableSelector() {
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

    private @Composable
    fun renderDialogContent() {
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

    private @Composable
    fun renderIntegerBlockInputs() {
        state.value = 0
        Text(stringResource(R.string.value) + ":", color = MaterialTheme.colorScheme.onSurface)
        VariableTextField(
            onValueChange = { arithmeticField = it },
            value = arithmeticField
        )
    }

    private @Composable
    fun renderStringBlockInputs() {
        state.value = 3
        Text(stringResource(R.string.value) + ":", color = MaterialTheme.colorScheme.onSurface)
        VariableTextField(
            onValueChange = { arithmeticField = it },
            value = arithmeticField
        )
    }

    private @Composable
    fun renderBooleanBlockInputs() {
        state.value = 4
        Text(stringResource(R.string.value) + ":", color = MaterialTheme.colorScheme.onSurface)
        VariableTextField(
            onValueChange = { arithmeticField = it },
            value = arithmeticField
        )
    }

    private @Composable
    fun renderArraySingleElementInputs() {
        state.value = 1
        Text(stringResource(R.string.index) + ":", color = MaterialTheme.colorScheme.onSurface)
        VariableTextField(
            onValueChange = { arrayIndex = it },
            value = arrayIndex
        )
        Text(stringResource(R.string.value) + ":", color = MaterialTheme.colorScheme.onSurface)
        VariableTextField(
            onValueChange = { arithmeticField = it },
            value = arithmeticField
        )
    }

    private @Composable
    fun renderArrayAllElementsInputs() {
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
                    Text(
                        stringResource(R.string.element) + " $index",
                        color = MaterialTheme.colorScheme.onSurface
                    )
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
            0 -> code =
                value.assembleIntegerBlock(selectedVariable.value!!.getName(), arithmeticField)

            1 -> {
                if (arrayIndex.isEmpty()) {
                    code = value.assembleIntegerArrayBlock(
                        selectedVariable.value!!.getName(),
                        arithmeticField
                    )
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
                    if (field.isEmpty()) continue
                    value.assembleElementIntegerArrayBlock(
                        selectedVariable.value!!.getName(),
                        index.toString(),
                        field
                    )
                    value.run()
                }
                val arrayBlock = selectedVariable.value
                if (arrayBlock is IntegerArrayBlock) {
                    val integerArrayBlock =
                        UIContext.getVar(arrayBlock.getName()) as IntegerArrayBlock
                    val arrayValueString: String = integerArrayBlock.getValue().joinToString(",")
                    value.assembleIntegerArrayBlock(
                        selectedVariable.value!!.getName(),
                        arrayValueString
                    )
                }
            }

            3 -> {
                code =
                    value.assembleStringBlock(selectedVariable.value!!.getName(), arithmeticField)
                value.run()
            }

            4 -> {
                code =
                    value.assembleBooleanBlock(selectedVariable.value!!.getName(), arithmeticField)
                value.run()
            }
        }
    }

    @Composable
    private fun ArrayAssignment() {
        Row(modifier = Modifier.padding(start = 40.dp, top = 50.dp)) {
            Column(modifier = Modifier.width(80.dp)) {
                Text(
                    stringResource(R.string.single_element),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Checkbox(
                    checked = checkVariableState.value,
                    onCheckedChange = {
                        checkVariableState.value = it
                        if (it) checkArrayState.value = false
                    }
                )
            }
            Column(modifier = Modifier.width(80.dp)) {
                Text(
                    stringResource(R.string.all_elements),
                    color = MaterialTheme.colorScheme.onSurface
                )
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
                    0 -> Text(
                        text = "${selectedVariable.value!!.getName()} = $arithmeticField",
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    1 -> Text(
                        text = "${selectedVariable.value!!.getName()}[$arrayIndex] = $arithmeticField",
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    2 -> {
                        for ((index, field) in arrayListField.withIndex()) {
                            Text(
                                text = "$index: ${field.ifBlank { "0" }}",
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            } else {
                val error = ErrorStore.get(code)!!
                Text(
                    text = error.description,
                    lineHeight = 12.sp,
                    fontSize = 8.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = error.category,
                    lineHeight = 12.sp,
                    fontSize = 8.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = error.title,
                    lineHeight = 12.sp,
                    fontSize = 8.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}