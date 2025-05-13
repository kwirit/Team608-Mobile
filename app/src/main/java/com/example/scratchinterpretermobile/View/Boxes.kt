package com.example.scratchinterpretermobile.View

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.Model.Variable

@Composable
fun InitializationBox(){
    BaseCard(name = "Инициализация") {
        TextField(modifier =  Modifier.size(80.dp),onValueChange = {}, value = "text")
    }
}
@Composable
fun AssigningBox(variables: MutableList<Variable>){
    BaseCard(name = "Присваивание") {
        ListOfVar(variables)
    }
}
@Composable
fun IfBox(){
    BaseCard(name = "Условие") {

    }
}
@Composable
fun ConsoleBox(){
    BaseCard(name = "Вывод") {

    }
}