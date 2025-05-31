package com.example.scratchinterpretermobile.View.Widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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

@Composable
fun InnerCreationButton(showState: MutableState<Boolean>, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Button(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(50.dp), onClick = {
                showState.value = true
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_add),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
