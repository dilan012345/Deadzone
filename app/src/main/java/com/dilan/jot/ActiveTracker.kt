package com.dilan.jot

import android.util.Log
import android.view.MotionEvent
import androidx.compose.ui.geometry.Offset
class ActiveTracker(
    private val drawAreaWidth: Float,
    private val onPoint: (Offset) -> Unit
) {

    fun onEvent(event: MotionEvent) {

        for (i in 0 until event.pointerCount) {

            val x = event.getX(i)
            val y = event.getY(i)

            Log.d("ACTIVE_TRACKER",
                "action=${event.actionMasked} id=${event.getPointerId(i)} x=$x y=$y"
            )

            if (x < drawAreaWidth) {
                onPoint(Offset(x, y))
            }
        }
    }
}