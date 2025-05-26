package com.example.scratchinterpretermobile.View.BaseStructure

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.Dialogs.BoxDialog
import com.example.scratchinterpretermobile.View.Dialogs.CreateBoxesDialog
import com.example.scratchinterpretermobile.View.Dialogs.CustomDialog

@Composable
fun BaseBox(name: String, showState: MutableState<Boolean>, dialogModifier: Modifier = Modifier, onConfirmButton:() -> Unit, dialogContent:@Composable () -> Unit, boxContent:@Composable () -> Unit){
    Card(Modifier.fillMaxWidth().heightIn(min = 80.dp).padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Box(Modifier.heightIn(min = 80.dp).fillMaxWidth().padding(end = 10.dp)){
            Button(onClick = {
                showState.value = true
            }, modifier = Modifier.size(60.dp).align(Alignment.CenterEnd), shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(painter = painterResource(R.drawable.settings_icon), contentDescription = null,modifier = Modifier.size(35.dp))
            }
            Column(modifier = Modifier.padding(7.dp)) {
                Text(text = name)
                Box (modifier = Modifier.fillMaxHeight()){
                    boxContent()
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
    if(showState.value){
        BoxDialog(showState, modifier = dialogModifier,onConfirmButton){
            Box(modifier = Modifier.fillMaxSize()){
                dialogContent()
            }
        }
    }
}