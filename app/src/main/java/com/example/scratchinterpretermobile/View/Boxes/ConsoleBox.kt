package com.example.scratchinterpretermobile.View.Boxes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.scratchinterpretermobile.Model.PrintBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

class ConsoleBox(externalBoxes: MutableList<ProgramBox>) : ProgramBox(externalBoxes) {
    override val value = PrintBlock(UIContext);
    var arithmeticField by mutableStateOf("")
    var result by mutableStateOf("");

    @Composable
    override fun render() {
        BaseBox(
            name = stringResource(R.string.output), showState,
            onConfirmButton = {
                value.updateOutput(arithmeticField)
                value.run()
                result = value.consoleOutput
            },
            dialogContent = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Введите выражение:", color = MaterialTheme.colorScheme.onSurface)
                    VariableTextField(onValueChange = { newText ->
                        arithmeticField = newText;
                    }, value = arithmeticField)
                }
            },
            onDelete = {
                value.removeBlock()
                externalBoxes.removeAll { it.id == id }
            }) {
        }
    }
}