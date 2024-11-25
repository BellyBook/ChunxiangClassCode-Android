package com.fantasy.chunxiangclasscode_android

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Image(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Image(
        painter = painterResource(id),
        contentDescription = "$id",
        modifier = modifier,
        contentScale = contentScale,
    )
}

@Composable
fun ImageDemo() {
    Image(
        id = R.drawable.chunxiang,
        modifier = Modifier
            .size(250.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 100.dp,
                    topEnd = 30.dp,
                    bottomStart = 60.dp,
                    bottomEnd = 100.dp
                )
            )  // 添加 12dp 的圆角
    )


    Icon(
        painter = painterResource(R.drawable.baseline_heart_broken_24),
        contentDescription = "heart",
        tint = Color.Red,
        modifier = Modifier.size(30.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun ImageDemoPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ImageDemo()
    }
}