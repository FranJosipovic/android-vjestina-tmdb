package agency.five.codebase.android.movieapp.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserScoreProgressBar(
    rating: Float,
    size: Dp = 40.dp,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.wrapContentSize()) {
        Canvas(modifier = Modifier
            .size(size)) {
            drawArc(
                color = Color(152, 237, 159),
                alpha = 0.5F,
                startAngle = 0F,
                sweepAngle = 360F,
                useCenter = false,
                style = Stroke(
                    width = 4.dp.toPx(),
                ),
            )
            drawArc(
                color = Color(61, 235, 75),
                startAngle = 270F,
                sweepAngle = ((rating / 10) * 360),
                useCenter = false,
                style = Stroke(
                    width = 4.dp.toPx(),
                ),
            )
        }
        Text(text = rating.toString(),
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview
fun UserScoreProgressBarPreview() {
    UserScoreProgressBar(6F)
}