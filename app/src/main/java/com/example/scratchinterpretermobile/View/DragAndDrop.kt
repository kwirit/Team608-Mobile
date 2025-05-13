//package com.example.scratchinterpretermobile.View
//
//import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScope
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.input.pointer.consumeAllChanges
//import androidx.compose.ui.input.pointer.consumePositionChange
//import androidx.compose.ui.input.pointer.pointerInput
//import androidx.compose.ui.layout.boundsInWindow
//import androidx.compose.ui.layout.onGloballyPositioned
//import androidx.compose.ui.unit.IntSize
//import androidx.compose.ui.unit.dp
//
//internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }
//
//@Composable
//fun DragableScreen(
//    modifier: Modifier = Modifier,
//    content: @Composable BoxScope.() -> Unit
//) {
//    val state = remember { DragTargetInfo() }
//    CompositionLocalProvider(LocalDragTargetInfo provides state) {
//        Box(modifier = modifier.fillMaxSize()) {
//            content()
//
//            if (state.isDragging) {
//                Box(
//                    modifier = Modifier
//                        .graphicsLayer {
//                            val offset = state.dragPosition + state.dragOffset
//                            translationX = offset.x - 15f
//                            translationY = offset.y - 15f
//                        }
//                ) {
//                    when (val block = state.draggedBlock) {
//                        is DraggedBlock.Initialization -> InitializationBox()
//                        is DraggedBlock.Assigning -> AssigningBox(variables = block.variables)
//                        is DraggedBlock.If -> IfBox()
//                        is DraggedBlock.Console -> ConsoleBox()
//                        null -> {}
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun <T> DragTarget(
//    modifier: Modifier = Modifier,
//    dataToDrop: T?,
//    blockType: DraggedBlock,
//    viewModel: MainViewModel,
//    showBoxesState: MutableState<Boolean>,
//    content: @Composable () -> Unit
//) {
//    val currentState = LocalDragTargetInfo.current
//    var currentPosition by remember { mutableStateOf(Offset.Zero) }
//
//    Box(
//        modifier = modifier
//            .onGloballyPositioned {
//                currentPosition = it.localToWindow(Offset.Zero)
//            }
//            .pointerInput(Unit) {
//                detectDragGesturesAfterLongPress(
//                    onDragStart = {
//                        viewModel.startDragging()
//                        currentState.isDragging = true
//                        currentState.dragPosition = currentPosition + it
//                        currentState.draggedBlock = blockType
//                        showBoxesState.value = false
//                    },
//                    onDrag = { change, dragAmount ->
//                        change.consumeAllChanges()
//                        currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
//                    },
//                    onDragEnd = {
//                        viewModel.stopDragging()
//                        currentState.isDragging = false
//                        currentState.dragOffset = Offset.Zero
//                    },
//                    onDragCancel = {
//                        viewModel.stopDragging()
//                        currentState.dragOffset = Offset.Zero
//                        currentState.isDragging = false
//                    }
//                )
//            }
//    ) {
//        content()
//    }
//}
//
//@Composable
//fun <T> DropItem(
//    modifier: Modifier,
//    content: @Composable() (BoxScope.(isInBound: Boolean, data: T?) -> Unit)
//) {
//
//    val dragInfo = LocalDragTargetInfo.current
//    val dragPosition = dragInfo.dragPosition
//    val dragOffset = dragInfo.dragOffset
//    var isCurrentDropTarget by remember {
//        mutableStateOf(false)
//    }
//
//    Box(modifier = modifier.onGloballyPositioned {
//        it.boundsInWindow().let { rect ->
//            isCurrentDropTarget = rect.contains(dragPosition + dragOffset)
//        }
//    }) {
//        val data =
//            if (isCurrentDropTarget && !dragInfo.isDragging) dragInfo.dataToDrop as T? else null
//        content(isCurrentDropTarget, data)
//    }
//}
//
//internal class DragTargetInfo {
//    var isDragging: Boolean by mutableStateOf(false)
//    var dragPosition by mutableStateOf(Offset.Zero)
//    var dragOffset by mutableStateOf(Offset.Zero)
//    var draggedBlock by mutableStateOf<DraggedBlock?>(null)
//    var dataToDrop by mutableStateOf<Any?>(null)
//}