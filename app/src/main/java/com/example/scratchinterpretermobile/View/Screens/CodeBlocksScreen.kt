package com.example.scratchinterpretermobile.View.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.ViewModels.MainViewModel
import com.example.scratchinterpretermobile.View.Widgets.VerticalReorderList


@Composable
fun CodeBlocksScreen(viewModel: MainViewModel) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 10.dp)) {
        VerticalReorderList(viewModel.boxes)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer(alpha = 0.7f)
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
                .size(54.dp),
            onClick = {
                viewModel.showBoxesState.value = true
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_add),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

