package com.example.scratchinterpretermobile.View.Bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.ViewModels.MainViewModel

@Composable
fun TopBar(viewModel: MainViewModel,modifier: Modifier = Modifier){
    Box(modifier = modifier.height(60.dp).background(color = MaterialTheme.colorScheme.primaryContainer)){
        Text(text = "Pick me", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(top = 10.dp, start = 25.dp), color = Color.White)
        Row(modifier = Modifier.fillMaxSize().padding(end = 16.dp), horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.End), verticalAlignment = Alignment.CenterVertically){
            Button(modifier = Modifier.size(48.dp),onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = Color.Green),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(painter = painterResource(R.drawable.baseline_bug_report_24), contentDescription = null,modifier = Modifier.size(40.dp))
            }
            Button(modifier = Modifier.size(48.dp),onClick = {
                viewModel.run()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = Color.Green),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(painter = painterResource(R.drawable.play_button), contentDescription = null,modifier = Modifier.size(26.dp))
            }
        }
    }
}