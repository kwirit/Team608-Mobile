@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.scratchinterpretermobile.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import com.example.scratchinterpretermobile.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.scratchinterpretermobile.ui.theme.LightOrange
import com.example.scratchinterpretermobile.ui.theme.Orange


@Preview
@Composable
fun MainScreen(){

    val showBoxesState = remember { mutableStateOf(false) }

    if(showBoxesState.value == true){
        ShowListOfBoxes(showBoxesState)
    }

    Column {
        TopBar(showBoxesState)
        Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.CenterEnd){

        }
        BottomBar()
    }


}

@Composable
fun ShowListOfBoxes(showBoxesState: MutableState<Boolean>){
    Dialog(
        onDismissRequest = {showBoxesState.value = false},
        ) {
        Box(Modifier.padding(16.dp).background(color = Color.Gray)){
            Column {
                Surface(Modifier.padding(8.dp).fillMaxWidth()) {
                    Text(text = "Инициализация переменной", fontSize = 30.sp)
                }
                Surface(Modifier.padding(8.dp).fillMaxWidth()) {
                    Text(text = "1", fontSize = 30.sp)
                }
                Surface(Modifier.padding(8.dp).fillMaxWidth()) {
                    Text(text = "1", fontSize = 40.sp)
                }
                Surface(Modifier.padding(8.dp).fillMaxWidth()) {
                    Text(text = "1", fontSize = 40.sp)
                }
                Surface(Modifier.padding(8.dp).fillMaxWidth()) {
                    Text(text = "1", fontSize = 40.sp)
                }
            }
        }
    }
}

@Composable
fun TopBar(showBoxesState: MutableState<Boolean>){
    Row(Modifier.fillMaxWidth().height(100.dp).background(color = Orange), horizontalArrangement = Arrangement.End){
        Button(modifier = Modifier.padding(top = 18.dp, end = 20.dp).size(60.dp),onClick = {
            showBoxesState.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = LightOrange,
                contentColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(painter = painterResource(R.drawable.baseline_add), contentDescription = null, modifier = Modifier.size(50.dp))
        }
        Button(modifier = Modifier.padding(top = 18.dp, end = 20.dp).size(60.dp),onClick = {
            showBoxesState.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = LightOrange,
                contentColor = Color.Green),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(painter = painterResource(R.drawable.play_button), contentDescription = null, modifier = Modifier.size(30.dp))
        }
    }
}

@Composable
fun BottomBar(){
    Row(Modifier.fillMaxWidth().height(100.dp).background(color = Orange), horizontalArrangement = Arrangement.Center){
        Button(modifier = Modifier.padding(top = 18.dp, end = 60.dp).size(60.dp),onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = LightOrange,
                contentColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
        }
        Button(modifier = Modifier.padding(top = 18.dp, start = 60.dp).size(60.dp),onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = LightOrange,
                contentColor = Color.Green),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
        }
    }
}
