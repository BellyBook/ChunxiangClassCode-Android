package com.fantasy.chunxiangclasscode_android.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun AnimationTest() {
    var doIt by remember { mutableStateOf(false) }
    var hp by remember { mutableIntStateOf(100) }
    val color by animateColorAsState(
        when (hp) {
            in 61..100 -> Color.Green
            in 41..60 -> Color.Red
            in 21..40 -> Color.Gray
            else -> Color.LightGray
        }, label = "color",
        animationSpec = tween(durationMillis = 2000)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = doIt,
            enter = fadeIn(animationSpec = tween(durationMillis = 2000)),
//            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color)
                    .clickable {
                        if (hp > 0) {
                            hp -= 20
                        }
                    }
                    .size(200.dp)
            ) {
                Text(
                    text = if (hp <= 0) "game over" else "$hp",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }


        Button(
            onClick = {
                doIt = true
            }
        ) {
            Text("play game")
        }
    }
}

@Preview
@Composable
fun AnimationTestPreview() {
    AnimationTest()
}