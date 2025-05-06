package com.example.scratchinterpretermobile.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import com.example.scratchinterpretermobile.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.window.DialogProperties
import com.example.scratchinterpretermobile.Model.Variable
import com.example.scratchinterpretermobile.ui.theme.Blue
import com.example.scratchinterpretermobile.ui.theme.LightOrange
import com.example.scratchinterpretermobile.ui.theme.Orange


@Preview
@Composable
fun MainScreen(){

    val showBoxesState = remember { mutableStateOf(true) }

    if(showBoxesState.value == true){
        ShowListOfBoxes(showBoxesState)
    }

    Column {
        TopBar(showBoxesState)
        Column ( Modifier.weight(1f)){

        }

        BottomBar()
    }

}

@Composable
fun ShowListOfBoxes(showBoxesState: MutableState<Boolean>){
    val list = mutableListOf<Variable>()
    list.add(Variable("AAAA",2))
    list.add(Variable("ABA",25))
    list.add(Variable("AASDASDA",4))
    list.add(Variable("AASDASDAAA",21))
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false),
        onDismissRequest = {showBoxesState.value = false},
    ) {
        Column(Modifier.width(320.dp).height(600.dp).padding(20.dp).background(color = Color.White, shape = RoundedCornerShape(20.dp))) {
            ListOfVar(list)
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
        Button(modifier = Modifier.padding(top = 18.dp, end = 20.dp).size(60.dp),onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = LightOrange,
                contentColor = Color.Green),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(painter = painterResource(R.drawable.play_button), contentDescription = null, modifier = Modifier.size(32.dp))
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

@Composable
fun BaseCard(name: String,content:@Composable () -> Unit,){
    Card(Modifier.fillMaxWidth().height(100.dp).padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Blue),
    ) {
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.TopCenter){
            Row{
                Text(text = name)
                content
            }
        }
    }
}

@Composable
fun ListOfVar(variables: MutableList<Variable>){
    val expanded = remember { mutableStateOf(false) }
    TextButton(onClick = {expanded.value = true}) { Text("ЖМИ")}
    DropdownMenu(expanded = expanded.value,onDismissRequest = {expanded.value = false}) {
        variables.forEach {
            variable ->
            DropdownMenuItem(onClick = {expanded.value = false}, text = {Text(text = "12312")})
        }
    }
}

