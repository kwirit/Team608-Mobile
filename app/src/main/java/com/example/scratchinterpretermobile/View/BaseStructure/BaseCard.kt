package com.example.scratchinterpretermobile.View.BaseStructure

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.ui.theme.Blue

@Composable
fun BaseCard(name: String,onClick: () -> Unit = {},content:@Composable () -> Unit){
    Card(Modifier.fillMaxWidth().height(90.dp).padding(5.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Blue),
    ) {
        Box(Modifier.fillMaxSize(),contentAlignment = Alignment.Center){
            Column {Text(text = name, textAlign = TextAlign.Center)
            }
        }
    }
}