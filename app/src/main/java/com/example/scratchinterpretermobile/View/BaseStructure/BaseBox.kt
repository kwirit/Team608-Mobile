package com.example.scratchinterpretermobile.View.BaseStructure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Dialogs.CustomDialog
import com.example.scratchinterpretermobile.ui.theme.Blue

@Composable
fun BaseBox(name: String,showState: MutableState<Boolean>,content:@Composable () -> Unit){
    Card(Modifier.fillMaxWidth().height(100.dp).padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = Blue),
    ) {
        Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.Center){
            Button(onClick = {showState.value = true}) {
                Text(text = name)
            }
        }
    }
    if(showState.value){
        CreateBoxesDialog(showState){
            content()
        }
    }
}