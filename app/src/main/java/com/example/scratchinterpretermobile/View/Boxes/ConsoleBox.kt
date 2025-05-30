package com.example.scratchinterpretermobile.View.Boxes

import android.R.attr.text
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.scratchinterpretermobile.Model.InitBlock
import com.example.scratchinterpretermobile.Model.PrintBlock
import com.example.scratchinterpretermobile.Model.UIContext
import com.example.scratchinterpretermobile.View.BaseStructure.BaseBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.Widgets.VariableTextField

class ConsoleBox(externalBoxes: MutableList<ProgramBox>) : ProgramBox(externalBoxes) {
    override val value = PrintBlock(UIContext);
    var arithmeticField by mutableStateOf("")
    var result by mutableStateOf("");
    @Composable
    override fun render(){
        BaseBox(name = "Вывод", showState,
            onConfirmButton = {
                value.updateOutput(arithmeticField)
                value.run()
                result = value.consoleOutput
        },
            dialogContent = {
                VariableTextField(onValueChange = {newText ->
                    arithmeticField = newText;
                }, value = arithmeticField)
                Text(text = result)
        },
            onDelete = {
                value.removeBlock()
                externalBoxes.removeAll { it.id == id }
            }) {
            Text(text = result)
        }
    }
}