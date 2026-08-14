package com.dilan.jot

import android.R.attr.scaleX
import android.system.Os.close
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.DoNotTouch
import androidx.compose.material.icons.filled.FrontHand
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.domain.model.BackgroundGeometry
import io.ak1.drawbox.domain.model.BackgroundPattern
import io.ak1.drawbox.presentation.viewmodel.rememberDrawBoxController
import kotlinx.coroutines.launch
import kotlin.io.path.Path
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

@Composable
fun ColourWheel(
    onColorSelected: (Color) -> Unit
) {
    val swatches = listOf(
        Color(0xFF4A0E16),
        Color(0xFF71151F),
        Color(0xFF9A202C),
        Color(0xFFC73845),
        Color(0xFFE35B66),
        Color(0xFFF1848C),
        Color(0xFFFFB1B5),

        Color(0xFF7A281F),
        Color(0xFFAD3B2E),
        Color(0xFFD95B48),
        Color(0xFFF27C68),
        Color(0xFFFFAA98),

        Color(0xFF71300B),
        Color(0xFF9C430F),
        Color(0xFFC85E18),
        Color(0xFFE77C2D),
        Color(0xFFFFB45E),

        Color(0xFF6E5900),
        Color(0xFF9A7B00),
        Color(0xFFC2A015),
        Color(0xFFE1C53D),
        Color(0xFFFFE88A),

        Color(0xFF3E500A),
        Color(0xFF657D12),
        Color(0xFF8DA426),
        Color(0xFFB3C957),
        Color(0xFFD8E994),

        Color(0xFF183B20),
        Color(0xFF286039),
        Color(0xFF3F844A),
        Color(0xFF61A865),
        Color(0xFF8BC58A),
        Color(0xFFB5DDB0),

        Color(0xFF104C46),
        Color(0xFF176E66),
        Color(0xFF2D9589),
        Color(0xFF50B9A9),
        Color(0xFF9ADFD3),

        Color(0xFF10485A),
        Color(0xFF176B82),
        Color(0xFF318FA7),
        Color(0xFF52B3C7),
        Color(0xFF91D8E3),

        Color(0xFF102B52),
        Color(0xFF183F78),
        Color(0xFF285B99),
        Color(0xFF417BB7),
        Color(0xFF679DD0),
        Color(0xFF9BC7EC),

        Color(0xFF28265A),
        Color(0xFF373574),
        Color(0xFF514F96),
        Color(0xFF706DB5),
        Color(0xFFA09CDC),

        Color(0xFF3A194A),
        Color(0xFF552567),
        Color(0xFF743581),
        Color(0xFF944C9F),
        Color(0xFFBA7BC0),
        Color(0xFFD5A8D8),

        Color(0xFF501136),
        Color(0xFF74184D),
        Color(0xFF982F69),
        Color(0xFFBC5085),
        Color(0xFFE49ABE),

        Color(0xFF301B13),
        Color(0xFF4D2D20),
        Color(0xFF70432E),
        Color(0xFF956148),
        Color(0xFFC49A7E),

        Color(0xFF1D1E20),
        Color(0xFF34363A),
        Color(0xFF575A60),
        Color(0xFF85888D),
        Color(0xFFC1C3C5)
    )

    var rotation by remember {
        mutableFloatStateOf(0f)
    }

    val density = LocalDensity.current

    val canvasSize = 820.dp


    val outerRadius = with(density) {
        245.dp.toPx()
    }

    val innerRadius = with(density) {
        175.dp.toPx()
    }


    val middleRingInnerRadius = outerRadius + with(density) {
        4.dp.toPx()
    }

    val middleRingOuterRadius = with(density) {
        315.dp.toPx()
    }


    val finalRingInnerRadius = middleRingOuterRadius + with(density) {
        4.dp.toPx()
    }

    val finalRingOuterRadius = with(density) {
        385.dp.toPx()
    }

    Canvas(
        modifier = Modifier
            .size(canvasSize)
            .pointerInput(Unit) {

                awaitEachGesture {

                    val down = awaitFirstDown(
                        requireUnconsumed = false
                    )

                    val centre = Offset(
                        size.width / 2f,
                        -finalRingOuterRadius + 210.dp.toPx()
                    )

                    val position = down.position
                    val dx = position.x - centre.x
                    val dy = position.y - centre.y

                    val distance = kotlin.math.sqrt(
                        dx * dx + dy * dy
                    )


                    if (distance <= finalRingOuterRadius) {

                        down.consume()

                        val sweep = 360f / swatches.size

                        var angle = Math.toDegrees(
                            atan2(
                                dy.toDouble(),
                                dx.toDouble()
                            )
                        ).toFloat()


                        angle -= rotation
                        angle += 90f

                        if (angle < 0f) {
                            angle += 360f
                        }

                        angle %= 360f

                        val swatchIndex =
                            (angle / sweep)
                                .toInt()
                                .coerceIn(
                                    0,
                                    swatches.lastIndex
                                )

                        var previousPosition = position
                        var moved = false

                        while (true) {

                            val event = awaitPointerEvent()

                            val change =
                                event.changes.firstOrNull()
                                    ?: break

                            if (!change.pressed) {
                                break
                            }

                            val dragAmount =
                                change.position -
                                        previousPosition

                            if (dragAmount.getDistance() > 1f) {
                                moved = true
                            }

                            rotation -=
                                dragAmount.x * 0.25f

                            previousPosition =
                                change.position

                            change.consume()
                        }


                        if (!moved) {
                            onColorSelected(
                                swatches[swatchIndex]
                            )
                        }
                    }
                }
            }
    ) {
        val centre = Offset(
            size.width / 2f,
            -finalRingOuterRadius + 210.dp.toPx()
        )

        val sweep =
            360f / swatches.size

        val gapPx = 0.dp.toPx() // cant decide on appearance if 1.dp or 0 looks best


        val gapAngle = Math.toDegrees(
            (gapPx / finalRingOuterRadius).toDouble()
        ).toFloat()

        val swatchSweep =
            sweep - gapAngle

        swatches.forEachIndexed { index, color ->

            // Half the gap on either side
            val startAngle =
                index * sweep -
                        90f +
                        rotation +
                        gapAngle / 2f



            val innerPath = Path()

            innerPath.arcTo(
                rect = Rect(
                    centre.x - outerRadius,
                    centre.y - outerRadius,
                    centre.x + outerRadius,
                    centre.y + outerRadius
                ),
                startAngleDegrees = startAngle,
                sweepAngleDegrees = swatchSweep,
                forceMoveTo = true
            )

            innerPath.arcTo(
                rect = Rect(
                    centre.x - innerRadius,
                    centre.y - innerRadius,
                    centre.x + innerRadius,
                    centre.y + innerRadius
                ),
                startAngleDegrees =
                    startAngle + swatchSweep,
                sweepAngleDegrees =
                    -swatchSweep,
                forceMoveTo = false
            )

            innerPath.close()

            drawPath(
                path = innerPath,
                color = color
            )


            val middlePath = Path()

            middlePath.arcTo(
                rect = Rect(
                    centre.x - middleRingOuterRadius,
                    centre.y - middleRingOuterRadius,
                    centre.x + middleRingOuterRadius,
                    centre.y + middleRingOuterRadius
                ),
                startAngleDegrees = startAngle,
                sweepAngleDegrees = swatchSweep,
                forceMoveTo = true
            )

            middlePath.arcTo(
                rect = Rect(
                    centre.x - middleRingInnerRadius,
                    centre.y - middleRingInnerRadius,
                    centre.x + middleRingInnerRadius,
                    centre.y + middleRingInnerRadius
                ),
                startAngleDegrees =
                    startAngle + swatchSweep,
                sweepAngleDegrees =
                    -swatchSweep,
                forceMoveTo = false
            )

            middlePath.close()

            drawPath(
                path = middlePath,
                color = lerp(
                    color,
                    Color.Black,
                    0.18f
                )
            )



            val finalPath = Path()

            finalPath.arcTo(
                rect = Rect(
                    centre.x - finalRingOuterRadius,
                    centre.y - finalRingOuterRadius,
                    centre.x + finalRingOuterRadius,
                    centre.y + finalRingOuterRadius
                ),
                startAngleDegrees = startAngle,
                sweepAngleDegrees = swatchSweep,
                forceMoveTo = true
            )

            finalPath.arcTo(
                rect = Rect(
                    centre.x - finalRingInnerRadius,
                    centre.y - finalRingInnerRadius,
                    centre.x + finalRingInnerRadius,
                    centre.y + finalRingInnerRadius
                ),
                startAngleDegrees =
                    startAngle + swatchSweep,
                sweepAngleDegrees =
                    -swatchSweep,
                forceMoveTo = false
            )

            finalPath.close()

            drawPath(
                path = finalPath,
                color = lerp(
                    color,
                    Color.Black,
                    0.38f
                )
            )
        }
    }
}

class Drawing : Screen {

    @Composable
    override fun Content() {

        val scope = rememberCoroutineScope()

        val controller = rememberDrawBoxController()
        val state by controller.state.collectAsState()

        val canUndo by controller.canUndo.collectAsState(
            initial = false
        )

        val canRedo by controller.canRedo.collectAsState(
            initial = false
        )

        val backgroundPattern = BackgroundPattern(
            geometry = BackgroundGeometry.DotGrid(
                spacing = 40.dp,
                radius = 1.5.dp
            ),
            tint = Color.White
        )
        LaunchedEffect(key1 = Unit) {
            controller.setColor(Color(0xFFE1BE95))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {

            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                DrawBox(
                    state = state,
                    onIntent = controller::onIntent,
                    modifier = Modifier
                        .weight(0.84f)
                        .fillMaxHeight(),
                    showGrid = false,
                    backgroundPattern = backgroundPattern
                )


                Box(
                    //onClick = {},
                    modifier = Modifier
                        .weight(0.16f)
                        .pointerInput(Unit) {
                            awaitEachGesture {

                                val down = awaitFirstDown(requireUnconsumed = false)

                                down.consume()

                                waitForUpOrCancellation()
                            }
                        }
                            .combinedClickable(
                            onClick = {},
                            onLongClick = {

                            }
                        )

                        .fillMaxHeight()
                        .background(Color(0xFF111111))
                        .drawBehind {
                            drawLine(
                                color = Color(0xFFE1BE95),
                                start = Offset(0f, 0f),
                                end = Offset(0f, size.height),
                                strokeWidth = 3.dp.toPx()
                            )
                        }
                ) {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            imageVector = Icons.Filled.FrontHand,
                            contentDescription = null,
                            modifier = Modifier

                                .size(44.dp)
                                .graphicsLayer {
                                scaleX = -1f
                            },
                            tint = Color(0xFFE1BE95)
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = "Deadzone",
                            fontFamily = FontFamily(Font(R.font.bagelfatone)),
                            fontSize = 28.sp,
                            color = Color.Gray
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                            text = buildAnnotatedString {
                                append("Place the ")

                                withStyle(
                                    SpanStyle(
                                        color = Color(0xFFE1BE95)
                                    )
                                ) {
                                    append("BASE")
                                }

                                append(" of your palm here")
                            },
                            fontSize = 15.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.bagelfatone)),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }


            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(
                        top = 54.dp,
                        end = 16.dp
                    )
                    .border(
                        width = 2.dp,
                        color = Color.Gray,
                        shape = CircleShape
                    )
                    .background(
                        color = Color.Black,
                        shape = CircleShape
                    )
                    .height(50.dp)
                    .width(110.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                IconButton(
                    onClick = {
                        if (canUndo) {
                            scope.launch {
                                controller.undo()
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Undo,
                        contentDescription = "Undo",
                        tint = if (canUndo) {
                            Color.LightGray
                        } else {
                            Color(0xFF2F2F2F)
                        }
                    )
                }

                IconButton(
                    onClick = {
                        if (canRedo) {
                            scope.launch {
                                controller.redo()
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Redo,
                        contentDescription = "Redo",
                        tint = if (canRedo) {
                            Color.LightGray
                        } else {
                            Color(0xFF2F2F2F)
                        }
                    )
                }
            }


            IconButton(
                onClick = {
                    controller.saveBitmap()
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        top = 54.dp,
                        start = 16.dp
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFFCE5038),
                        shape = CircleShape
                    )
                    .background(
                        color = Color.Black,
                        shape = CircleShape
                    )
                    .size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Save",
                    tint = Color.LightGray
                )
            }
            var showColourWheel by remember { mutableStateOf(false) }

            IconButton(
                onClick = {
                    showColourWheel = true
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        top = 54.dp,
                        start = 76.dp
                    )
                    .border(
                        width = 2.dp,
                        color = Color.Gray,
                        shape = CircleShape
                    )
                    .background(
                        color = Color.Black,
                        shape = CircleShape
                    )
                    .size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Palette,
                    contentDescription = "Palette",
                    tint = Color.LightGray
                )
            }

            AnimatedVisibility(
                visible = showColourWheel,
                enter = fadeIn(
                    animationSpec = tween(220)
                ) + scaleIn(
                    initialScale = 0.75f,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ),
                exit = fadeOut(
                    animationSpec = tween(180)
                ) + scaleOut(
                    targetScale = 0.75f,
                    animationSpec = tween(
                        durationMillis = 180
                    )
                )
            ) {

                Box(
                    modifier = Modifier.fillMaxSize()
                ) {

                    // Full-screen layer behind the wheel
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Color.Black.copy(alpha = 0.35f)
                            )
                            .clickable {
                                showColourWheel = false
                            }
                    )

                    // Wheel
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        ColourWheel(
                            onColorSelected = { color ->
                                controller.setColor(color)
                                showColourWheel = false
                            }
                        )
                    }
                }
            }
        }
    }
}
