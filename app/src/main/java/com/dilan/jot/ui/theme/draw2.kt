package com.dilan.jot.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.dilan.jot.TouchPoint

@Composable
fun Draw2(
    strokes: List<List<TouchPoint>>,
    currentStroke: List<TouchPoint>
) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        // Completed strokes
        strokes.forEach { points ->

            points.forEach { point ->

                drawCircle(
                    color = Color.White,
                    radius = 5f,
                    center = Offset(
                        point.x,
                        point.y
                    )
                )
            }
        }

        // Current stroke
        currentStroke.forEach { point ->

            drawCircle(
                color = Color.White,
                radius = 5f,
                center = Offset(
                    point.x,
                    point.y
                )
            )
        }
    }
}