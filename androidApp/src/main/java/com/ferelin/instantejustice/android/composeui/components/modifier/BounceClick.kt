package com.ferelin.instantejustice.android.composeui.components.modifier

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

@Stable
enum class AppButtonState { Pressed, Idle }

const val BOUNCE_DEFAULT_SCALE = 1f
const val BOUNCE_SCALE_UP = 1.1f
const val BOUNCE_SCALE_DOWN = 0.9f

fun Modifier.bounceClick(bounceScale: Float) = composed {
    var buttonState by remember { mutableStateOf(AppButtonState.Idle) }
    val scale by animateFloatAsState(
        targetValue = if (buttonState == AppButtonState.Pressed) bounceScale else BOUNCE_DEFAULT_SCALE,
        label = "button scale"
    )

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = { /**/ }
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == AppButtonState.Pressed) {
                    waitForUpOrCancellation()
                    AppButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    AppButtonState.Pressed
                }
            }
        }
}