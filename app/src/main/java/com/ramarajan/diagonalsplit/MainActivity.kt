package com.ramarajan.diagonalsplit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramarajan.diagonalsplit.ui.theme.DiagonalSplitTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiagonalSplitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DrawDiagonalSplit()
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawDiagonalSplit() {

    var position by remember { mutableStateOf(Offset(1000f, 150f)) }

    LaunchedEffect(true) {
        while (true) {
            position -= Offset(2f, -3f)
            delay(10L)
            if (position.x < 0f || position.y > 1600f) {
                position = Offset(1000f, 150f)
            }
        }
    }

    val textMeasurer = rememberTextMeasurer()

    val style = TextStyle(
        fontSize = 48.sp,
        color = Color.Black,
    )

    Canvas(modifier = Modifier.fillMaxSize()) {

        val leftPoint = size.height * 0.9f
        val rightPoint = size.width * 1.15f
        val path = Path()
        path.lineTo(0f, 0f)
        path.lineTo(0f, leftPoint)
        path.lineTo(rightPoint, 0f)
        path.lineTo(size.width, 0f)
        path.close()

        val rightPath = Path()
        rightPath.moveTo(0f, leftPoint)
        rightPath.lineTo(rightPoint, 0f)
        rightPath.lineTo(size.width, size.height)
        rightPath.lineTo(0f, size.height)
        rightPath.close()

        clipPath(
            path = rightPath,
            clipOp = ClipOp.Intersect,
            ) {
            drawPath(path = rightPath, color = Color.LightGray)
            drawText(
                textMeasurer = textMeasurer,
                text = "Hello",
                style = style,
                topLeft = Offset(0f,leftPoint)

            )
        }

        clipPath(
            path = path,
            clipOp = ClipOp.Intersect,

        ) {
            drawPath(path = path, color = Color.DarkGray)
            val circleRadius = 60.dp.toPx()
            drawCircle(
                radius = circleRadius,
                color = Color.Red,
                center = position,

            )
        }

    }
}
