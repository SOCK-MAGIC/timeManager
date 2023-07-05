package com.setruth.timemanager.ui.screen.stopwatch

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.center
import com.setruth.timemanager.R
import kotlin.math.roundToInt

@Composable
fun StopWatchView(modifier: Modifier = Modifier) {
    DragGestureDemo()
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun DragGestureDemo() {
    val image = ImageBitmap.imageResource(id = R.drawable.avatar_rengwuxian)
    var isLarge by remember { mutableStateOf(false) }
    val scaleSize = animateFloatAsState(targetValue = if (isLarge) 2f else 1f)
    var offset by remember { mutableStateOf(Offset.Zero) }
    // 惯性滑动
    val filingOffset = remember {
        Animatable(Offset.Zero, Offset.VectorConverter)
    }
    val horizontalVelocity by remember {
        mutableStateOf(0f)
    }
    val verticalVelocity by remember {
        mutableStateOf(0f)
    }
    val rScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .scale(scaleSize.value)
            .fillMaxSize()
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .combinedClickable(
                onDoubleClick = {
                    isLarge = !isLarge
                },
                onClick = {
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .pointerInput(Unit) {
                detectDragGestures { _, dragAmount ->
                    if (isLarge) {
                        offset += dragAmount
                        val newValue = Offset(
                            x = offset.x.coerceIn(
                                -(image.width - size.center.x) / 2f,
                                (image.width - size.center.x) / 2f,
                            ),
                            y = offset.y.coerceIn(
                                -(size.center.y - image.height) / 2f,
                                (size.center.y - image.height) / 2f
                            )
                        )
                        offset = newValue
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawImage(
                image = image,
                topLeft = Offset(center.x - image.width / 2f, center.y - image.height / 2f)
            )
        }
    }
}
