package com.example.scratchinterpretermobile.View.Bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.ViewModels.MainViewModel
import com.example.scratchinterpretermobile.ui.theme.LightOrange
import com.example.scratchinterpretermobile.ui.theme.Orange

@Composable
fun BottomBar(viewModel: MainViewModel,modifier: Modifier = Modifier){
    Box(modifier = modifier.height(60.dp).background(color = Orange)){
        Row(Modifier.fillMaxSize().background(color = Orange), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically){
            Button(modifier = Modifier.size(48.dp),onClick = {
                viewModel.screenState.value=0
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightOrange,
                    contentColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(painter = painterResource(R.drawable.home), contentDescription = null, modifier = Modifier.size(50.dp))
            }
            Button(modifier = Modifier.size(48.dp),onClick = {
                viewModel.screenState.value=0
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightOrange,
                    contentColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(painter = painterResource(R.drawable.home), contentDescription = null, modifier = Modifier.size(50.dp))
            }
            Button(modifier = Modifier.size(48.dp),onClick = {
                viewModel.screenState.value=0
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightOrange,
                    contentColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(painter = painterResource(R.drawable.home), contentDescription = null, modifier = Modifier.size(50.dp))
            }
            Button(modifier = Modifier.size(48.dp),onClick = {
                viewModel.screenState.value=1
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightOrange,
                    contentColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(painter = painterResource(R.drawable.console), contentDescription = null, modifier = Modifier.size(50.dp))
            }
        }
    }

}