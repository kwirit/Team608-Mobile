package com.example.scratchinterpretermobile.View.Widgets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.scratchinterpretermobile.View.Boxes.ProgramBox
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun VerticalReorderList(boxes: MutableList<ProgramBox>) {
    val data by rememberUpdatedState(newValue = boxes)
    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data.apply {
            add(to.index, removeAt(from.index))
        }
    })

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
            .fillMaxSize()
    ) {
        items(data, key = { it.id }) { item ->
            ReorderableItem(state, key = item.id) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 30.dp else 0.dp)
                val scale = animateFloatAsState(if (isDragging) 1.05f else 1f)

                Column(
                    modifier = Modifier
                        .shadow(elevation.value)
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                        }
                        .fillMaxSize()
                ) {
                    Box(Modifier.fillMaxWidth()) {
                        item.render()
                    }
                }
            }
        }
    }
}