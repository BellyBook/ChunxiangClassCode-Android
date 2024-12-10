package com.fantasy.chunxiangclasscode_android.design

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun Icon(
    @DrawableRes id: Int,
    modifier: Modifier = Modifier.size(24.dp),
    tint: Color = Color(color = 0xFF333333)
) {
    androidx.compose.material3.Icon(
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = modifier,
        tint = tint
    )
}