package com.example.scratchinterpretermobile.View.BaseStructure

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scratchinterpretermobile.R
import com.example.scratchinterpretermobile.View.Dialogs.BoxDialog

@Composable
fun BaseBox(
    name: String,
    showState: MutableState<Boolean>,
    dialogModifier: Modifier = Modifier,
    onCloseDialog: () -> Unit = {},
    onConfirmButton: () -> Unit,
    dialogContent: @Composable () -> Unit,
    onDelete: () -> Unit = {},
    boxContent: @Composable () -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Box(
            Modifier
                .heightIn(min = 80.dp)
                .fillMaxWidth()
                .padding(end = 10.dp)
        ) {
            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                Button(
                    onClick = {
                        onDelete()
                    },
                    modifier = Modifier.size(60.dp),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_delete_outline_24),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }
                Button(
                    onClick = {
                        showState.value = true
                    },
                    modifier = Modifier.size(60.dp), shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.settings_icon),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(top = 10.dp, start = 15.dp)) {
                Text(
                    fontFamily = FontFamily(Font(R.font.akayakanadaka_regular)),
                    text = name,
                    fontSize = 13.sp
                )
                Box(modifier = Modifier.fillMaxHeight()) {
                    boxContent()
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
    if (showState.value) {
        BoxDialog(
            name,
            showState,
            modifier = dialogModifier,
            onCloseDialog = onCloseDialog,
            onConfirmButton
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                dialogContent()
            }
        }
    }
}