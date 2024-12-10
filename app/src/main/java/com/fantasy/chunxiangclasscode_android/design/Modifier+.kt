package com.fantasy.chunxiangclasscode_android.design

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.avatarStyle() = this
    .clip(RoundedCornerShape(4.dp))
    .background(Color.Gray)
    .size(40.dp)