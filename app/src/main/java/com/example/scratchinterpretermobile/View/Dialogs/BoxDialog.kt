package com.example.scratchinterpretermobile.View.Dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.R


@Composable
fun BoxDialog(showState: MutableState<Boolean>,modifier: Modifier,onCloseDialog: () -> Unit = {},onConfirmDialog: () -> Unit,content:@Composable () -> Unit){
    CustomDialog(showState, modifier = modifier){
        Box(modifier = Modifier.fillMaxSize()){
            Button(modifier = Modifier.align(Alignment.TopStart).padding(20.dp).size(50.dp),onClick = {
                onCloseDialog()
                showState.value = false},
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(painter = painterResource(R.drawable.arrow), contentDescription = null, modifier = Modifier.size(30.dp))
            }
            content()
            Button(modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp),onClick = {
                onConfirmDialog()
                onCloseDialog()
                showState.value = false
            }) {
                Text(text = "OK")
            }
        }
    }
}