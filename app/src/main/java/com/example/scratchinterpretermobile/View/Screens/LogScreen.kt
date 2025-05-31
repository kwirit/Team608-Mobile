package com.example.scratchinterpretermobile.View.Screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.scratchinterpretermobile.Model.outputList
import com.example.scratchinterpretermobile.View.ViewModels.MainViewModel

@Composable
fun LogScreen(viewModel: MainViewModel) {
    if (outputList.isEmpty()) {
        Text(text = "This is log...", color = MaterialTheme.colorScheme.onSurface)
    } else {
        LazyColumn {
            items(outputList) { item ->
                Text(text = item, color = MaterialTheme.colorScheme.onSurface)
            }
        }
    }
}
