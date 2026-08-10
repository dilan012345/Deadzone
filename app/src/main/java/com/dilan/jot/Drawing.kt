package com.dilan.jot

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LineWeight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.presentation.viewmodel.rememberDrawBoxController
import java.nio.file.Files.size
import io.ak1.drawbox.DrawBox
import io.ak1.drawbox.domain.model.BackgroundPattern

class DotPatternPainter : Painter() {

    override val intrinsicSize = Size(40f, 40f)

    override fun DrawScope.onDraw() {
        drawRect(Color.Black)

        drawCircle(
            color = Color.White,
            radius = 1.5f,
            center = Offset(
                size.width / 2f,
                size.height / 2f
            )
        )
    }
}
class Drawing : Screen {
    @Composable
    override fun Content(){
        val backgroundPattern = BackgroundPattern(
            painter = DotPatternPainter()
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            val controller = rememberDrawBoxController()
            val state by controller.state.collectAsState()
            LaunchedEffect(Unit) {
                controller.setBgColor(Color.Transparent)
                controller.setStrokeWidth(50f)

            }
            DrawBox(
                state = state,
                onIntent = controller::onIntent,
                modifier = Modifier
                    .fillMaxSize(),
                showGrid = false,
                backgroundPattern = backgroundPattern

            )
            Box(
                modifier = Modifier
                    .padding(bottom = 55.dp)
                    .align(Alignment.BottomCenter)
                    .width(210.dp)
                    .height(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xfff6f3e1))
            ){
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                ) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .background(Color.Black)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Pen",
                            tint = Color(0xfff6f3e1)
                        )
                    }

                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .background(Color.Black)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LineWeight,
                            contentDescription = "Thickness",
                            tint = Color(0xfff6f3e1)
                        )
                    }

                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .background(Color.Black)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More",
                            tint = Color(0xfff6f3e1)
                        )
                    }
                }
            }
        }


    }
}