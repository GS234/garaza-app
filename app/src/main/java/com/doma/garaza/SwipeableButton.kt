package com.doma.garaza

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun SwipeableButton(
    isActivated: Boolean,
    modifier: Modifier = Modifier,
    borderColor: Color = Color.LightGray,
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    content: @Composable () -> Unit
){
    val haptic = LocalHapticFeedback.current
    val padding = 3.dp
    val width = 100.dp
    val thresh = 0.99f
    val endOff = ((width + padding*2f) * LocalDensity.current.density).value
//    val endOff = 0f
    var passed = false
    var contextMenuWidth by remember {
        mutableFloatStateOf(value = 0f)
    }
    val offset = remember {
        Animatable(initialValue = 0f)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isActivated, contextMenuWidth) {
        if(isActivated){
            offset.animateTo(contextMenuWidth-endOff)
        } else {
            offset.animateTo(0f)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(30.dp)
            .onSizeChanged{
                contextMenuWidth = it.width.toFloat()
            }
            .clip(RoundedCornerShape(20.dp))
            .border(2.dp, borderColor, RoundedCornerShape(20.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(padding)
                .size(width, width)
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(contextMenuWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(0f, contextMenuWidth - endOff)
                                offset.snapTo(newOffset)

                                if ((newOffset >= (contextMenuWidth - endOff) * thresh) && !passed) {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    passed = true
                                }
                                if (newOffset < (contextMenuWidth - endOff) * thresh){
                                    passed = false
                                }
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value >= (contextMenuWidth - endOff) * thresh -> {
                                    haptic.performHapticFeedback(HapticFeedbackType.Confirm) // haptics
                                    onExpanded()
                                    scope.launch {
                                        offset.animateTo(0f)
                                    }
                                }

                                else -> {
                                    scope.launch {
                                        offset.animateTo(0f)
                                        onCollapsed()
                                    }
                                }
                            }
                        }
                    )
                },
            onClick = {
                if (offset.value == 0f){
                    haptic.performHapticFeedback(HapticFeedbackType.VirtualKey) // haptics
                    scope.launch {
                        offset.animateTo((contextMenuWidth-endOff)*0.1f)
                        offset.animateTo(0f)
                    }
                }
            }
        ){
            content()
        }
    }

}