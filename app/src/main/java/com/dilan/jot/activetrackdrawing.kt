package com.dilan.jot

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.sqrt

@Composable
fun ActiveTrackerDrawing(
    drawAreaWidth: Float
): ActiveTracker {

    var strokes by remember {
        mutableStateOf<List<List<Offset>>>(emptyList())
    }

    var currentStroke by remember {
        mutableStateOf<List<Offset>>(emptyList())
    }

    val handler = remember {
        Handler(Looper.getMainLooper())
    }

    val endStroke = remember {
        Runnable {
            if (currentStroke.isNotEmpty()) {

                strokes = strokes + listOf(currentStroke)

                currentStroke = emptyList()
            }
        }
    }

    val tracker = remember {

        ActiveTracker(
            drawAreaWidth = drawAreaWidth,

            onPoint = { point ->

                // New point arrived, so the stroke is still alive.
                handler.removeCallbacks(endStroke)

                currentStroke =
                    currentStroke + point

                // If no more points arrive for 300 ms,
                // consider the stroke finished.
                handler.postDelayed(
                    endStroke,
                    90L
                )
            }
        )
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {


        for (stroke in strokes) {

            if (stroke.size == 1) {

                drawCircle(
                    color = Color.Red,
                    radius = 5f,
                    center = stroke[0]
                )

            } else if (stroke.size >= 2) {

                drawWritingPath(
                    points = stroke
                )
            }
        }



        if (currentStroke.size == 1) {

            drawCircle(
                color = Color.Red,
                radius = 5f,
                center = currentStroke[0]
            )

        } else if (currentStroke.size >= 2) {

            drawWritingPath(
                points = currentStroke
            )
        }
    }

    DisposableEffect(Unit) {

        onDispose {
            handler.removeCallbacks(endStroke)
        }
    }

    return tracker
}




private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawWritingPath(
    points: List<Offset>
) {

    if (points.size < 2) return

    val path = Path()

    path.moveTo(
        points[0].x,
        points[0].y
    )


    for (i in 1 until points.size) {

        val previous = points[i - 1]
        val current = points[i]

        val midpoint = Offset(
            (previous.x + current.x) / 2f,
            (previous.y + current.y) / 2f
        )

        path.quadraticBezierTo(
            previous.x,
            previous.y,
            midpoint.x,
            midpoint.y
        )
    }

    path.lineTo(
        points.last().x,
        points.last().y
    )

    drawPath(
        path = path,
        color = Color.Red,
        style = Stroke(
            width = 10f
        )
    )
}