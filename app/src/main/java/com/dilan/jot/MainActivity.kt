package com.dilan.jot

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowInsetsControllerCompat
import com.dilan.jot.ui.theme.Draw2


data class TouchPoint(
    val id: Int,
    val x: Float,
    val y: Float,
    val size: Float,
    val timestamp: Long = 0L
)


class MainActivity : ComponentActivity() {

    private lateinit var activeTracker: ActiveTracker

    private var touchPoints by mutableStateOf<List<TouchPoint>>(emptyList())

    private var currentStroke by mutableStateOf<List<TouchPoint>>(emptyList())

    private var strokes by mutableStateOf<List<List<TouchPoint>>>(emptyList())



    private var deadzoneLeft = 0f
    private var deadzoneRight = 0f
    private var deadzoneTop = 0f
    private var deadzoneBottom = 0f


    private fun isInsideDeadzone(
        x: Float,
        y: Float
    ): Boolean {

        return x >= deadzoneLeft &&
                x <= deadzoneRight &&
                y >= deadzoneTop &&
                y <= deadzoneBottom
    }



    override fun dispatchTouchEvent(
        event: MotionEvent
    ): Boolean {

        val action = event.actionMasked

        val actionName = when (action) {

            MotionEvent.ACTION_DOWN ->
                "DOWN"

            MotionEvent.ACTION_UP ->
                "UP"

            MotionEvent.ACTION_MOVE ->
                "MOVE"

            MotionEvent.ACTION_CANCEL ->
                "CANCEL"

            MotionEvent.ACTION_POINTER_DOWN ->
                "POINTER_DOWN"

            MotionEvent.ACTION_POINTER_UP ->
                "POINTER_UP"

            else ->
                "OTHER"
        }


        Log.d(
            "RAW_TOUCH",
            "$actionName " +
                    "count=${event.pointerCount} " +
                    "time=${event.eventTime}"
        )



        for (i in 0 until event.pointerCount) {

            Log.d(
                "RAW_TOUCH",
                "  POINTER[$i] " +
                        "id=${event.getPointerId(i)} " +
                        "x=${event.getX(i)} " +
                        "y=${event.getY(i)} " +
                        "pressure=${event.getPressure(i)} " +
                        "size=${event.getSize(i)} " +
                        "touchMajor=${event.getTouchMajor(i)} " +
                        "touchMinor=${event.getTouchMinor(i)} " +
                        "toolMajor=${event.getToolMajor(i)} " +
                        "toolMinor=${event.getToolMinor(i)}"
            )
        }




        if (action == MotionEvent.ACTION_DOWN) {

            currentStroke = emptyList()
        }


        if (
            action == MotionEvent.ACTION_DOWN ||
            action == MotionEvent.ACTION_MOVE ||
            action == MotionEvent.ACTION_POINTER_DOWN ||
            action == MotionEvent.ACTION_POINTER_UP ||
            action == MotionEvent.ACTION_CANCEL
        ) {

            val newPoints =
                ArrayList<TouchPoint>(
                    event.pointerCount
                )


            for (i in 0 until event.pointerCount) {

                val id =
                    event.getPointerId(i)


                if (action == MotionEvent.ACTION_MOVE) {

                    for (h in 0 until event.historySize) {

                        val hx =
                            event.getHistoricalX(i, h)

                        val hy =
                            event.getHistoricalY(i, h)

                        val hs =
                            event.getHistoricalSize(i, h)

                        val ht =
                            event.getHistoricalEventTime(h)


                        if (!isInsideDeadzone(hx, hy)) {

                            newPoints.add(
                                TouchPoint(
                                    id = id,
                                    x = hx,
                                    y = hy,

                                    size = hs,

                                    timestamp = ht
                                )
                            )
                        }
                    }
                }




                val x =
                    event.getX(i)

                val y =
                    event.getY(i)

                val size =
                    event.getSize(i)


                if (!isInsideDeadzone(x, y)) {

                    newPoints.add(
                        TouchPoint(
                            id = id,
                            x = x,
                            y = y,


                            size = size,

                            timestamp =
                                event.eventTime
                        )
                    )
                }
            }




            if (newPoints.isNotEmpty()) {

                currentStroke =
                    currentStroke + newPoints

                touchPoints =
                    touchPoints + newPoints
            }
        }



        if (action == MotionEvent.ACTION_UP) {

            if (currentStroke.isNotEmpty()) {

                strokes =
                    strokes + listOf(currentStroke)

                currentStroke =
                    emptyList()
            }
        }



        if (action == MotionEvent.ACTION_CANCEL) {

            Log.d(
                "RAW_CANCEL",
                "========== ACTION_CANCEL =========="
            )

            Log.d(
                "RAW_CANCEL",
                "pointerCount=${event.pointerCount}"
            )

            Log.d(
                "RAW_CANCEL",
                "action=${event.action}"
            )

            Log.d(
                "RAW_CANCEL",
                "actionMasked=${event.actionMasked}"
            )

            Log.d(
                "RAW_CANCEL",
                "actionIndex=${event.actionIndex}"
            )

            Log.d(
                "RAW_CANCEL",
                "eventTime=${event.eventTime}"
            )

            Log.d(
                "RAW_CANCEL",
                "downTime=${event.downTime}"
            )


            for (i in 0 until event.pointerCount) {

                Log.d(
                    "RAW_CANCEL",
                    "POINTER[$i] " +
                            "id=${event.getPointerId(i)} " +
                            "x=${event.getX(i)} " +
                            "y=${event.getY(i)} " +
                            "size=${event.getSize(i)}"
                )
            }


            Log.d(
                "RAW_CANCEL",
                "=================================="
            )
        }


        return super.dispatchTouchEvent(event)
    }



    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)




        val originalCallback =
            window.callback


        window.callback =
            object : Window.Callback by originalCallback!! {

                override fun dispatchTouchEvent(
                    event: MotionEvent
                ): Boolean {

                    val actionName =
                        when (event.actionMasked) {

                            MotionEvent.ACTION_DOWN ->
                                "DOWN"

                            MotionEvent.ACTION_UP ->
                                "UP"

                            MotionEvent.ACTION_MOVE ->
                                "MOVE"

                            MotionEvent.ACTION_CANCEL ->
                                "CANCEL"

                            MotionEvent.ACTION_POINTER_DOWN ->
                                "POINTER_DOWN"

                            MotionEvent.ACTION_POINTER_UP ->
                                "POINTER_UP"

                            else ->
                                "OTHER"
                        }


                    Log.d(
                        "WINDOW_TOUCH",
                        "$actionName " +
                                "count=${event.pointerCount} " +
                                "time=${event.eventTime}"
                    )


                    for (i in 0 until event.pointerCount) {

                        Log.d(
                            "WINDOW_TOUCH",
                            "  POINTER[$i] " +
                                    "id=${event.getPointerId(i)} " +
                                    "x=${event.getX(i)} " +
                                    "y=${event.getY(i)} " +
                                    "pressure=${event.getPressure(i)} " +
                                    "size=${event.getSize(i)} " +
                                    "touchMajor=${event.getTouchMajor(i)} " +
                                    "touchMinor=${event.getTouchMinor(i)} " +
                                    "toolMajor=${event.getToolMajor(i)} " +
                                    "toolMinor=${event.getToolMinor(i)}"
                        )
                    }


                    return originalCallback
                        .dispatchTouchEvent(event)
                }
            }




        setContent {

            window.setFlags(
                WindowManager.LayoutParams.FLAG_SPLIT_TOUCH,
                WindowManager.LayoutParams.FLAG_SPLIT_TOUCH
            )


            window.setNavigationBarContrastEnforced(
                false
            )


            WindowInsetsControllerCompat(
                window,
                window.decorView
            ).isAppearanceLightNavigationBars =
                false




            val screenWidth =
                resources.displayMetrics
                    .widthPixels
                    .toFloat()

            val screenHeight =
                resources.displayMetrics
                    .heightPixels
                    .toFloat()



            deadzoneLeft =
                screenWidth * 0.7f

            deadzoneRight =
                screenWidth

            deadzoneTop =
                0f

            deadzoneBottom =
                screenHeight



            Box(
                modifier =
                    Modifier.fillMaxSize()
            ) {

                App()




                Box(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.7f)
                            .align(Alignment.TopStart)
                            .background(Color.Black)
                )



                Box(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.3f)
                            .align(Alignment.TopEnd)
                            .background(Color.White)
                )




                Draw2(
                    strokes = strokes,
                    currentStroke = currentStroke
                )




                val tracker =
                    ActiveTrackerDrawing(
                        drawAreaWidth = 1305.6f
                    )


                activeTracker =
                    tracker
            }
        }
    }
}