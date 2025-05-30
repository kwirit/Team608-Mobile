package com.example.scratchinterpretermobile.View.ViewModels

import android.content.Context
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.scratchinterpretermobile.Controller.Error.ErrorStore
import com.example.scratchinterpretermobile.Controller.Error.SUCCESS
import com.example.scratchinterpretermobile.Controller.Utils.parseCardToInstructionBoxes
import com.example.scratchinterpretermobile.Model.InstructionBlock
import com.example.scratchinterpretermobile.Model.Interpreter
import com.example.scratchinterpretermobile.Model.outputList
import com.example.scratchinterpretermobile.View.Boxes.ArrayInitializationBox
import com.example.scratchinterpretermobile.View.Boxes.AssigningBox
import com.example.scratchinterpretermobile.View.Boxes.ConsoleBox
import com.example.scratchinterpretermobile.View.Boxes.IfBox
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import com.example.scratchinterpretermobile.View.Boxes.VariableInitializationBox

class MainViewModel : ViewModel() {
    val showBoxesState = mutableStateOf(false)
    val screenState = mutableIntStateOf(0)
    val saveDialogState = mutableStateOf(false)
    val boxes = mutableStateListOf<ProgramBox>()


    fun run() {
        outputList.clear()

        val instructionList: MutableList<InstructionBlock> = parseCardToInstructionBoxes(boxes)
        val interpreter: Interpreter =
            Interpreter(com.example.scratchinterpretermobile.Model.Context())
        interpreter.setScript(instructionList)
        val runResult = interpreter.run()
        if (runResult != SUCCESS.id) {
            val error = ErrorStore.get(runResult)

            val line = "\t\n--------------------\n"
            val errorTitle = "\tTitle: " + error!!.title + "\n"
            val errorID = "\tID: " + error.id + "\n"
            val errorDescription = "\tDescription: " + error.description + "\n"
            val errorCategory = "\tCategory: " + error.category

            val errorInfo = line + errorTitle + errorID + errorDescription + errorCategory + line

            outputList.add(errorInfo)
        }
    }
}
