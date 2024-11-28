package com.fantasy.chunxiangclasscode_android

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import coil3.compose.AsyncImage
import github.leavesczy.matisse.GlideImageEngine
import github.leavesczy.matisse.Matisse
import github.leavesczy.matisse.MatisseContract
import github.leavesczy.matisse.MediaResource
import github.leavesczy.matisse.MediaType

val matisse = Matisse(
    maxSelectable = 1,
    imageEngine = GlideImageEngine(),
    mediaType = MediaType.ImageOnly
)

// localhost 127.0.0.1
val mockImage: String get() {
    return  "https://picsum.photos/200/200"
}

@Composable
fun MessageEditView() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .background(Color.White)
            .padding(bottom = 80.dp)
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        // 打开相册选照片的两种方案
        // 1. 用别人写的代码-第三方库
        // 2. 使用系统API
        // 3. 自 ji 写
        // gradle 是什么？
        // 1. 依赖管理工具
        // 2. 构建工具

        val mediaPickerLauncher =
            rememberLauncherForActivityResult(contract = MatisseContract()) { result: List<MediaResource>? ->
                if (!result.isNullOrEmpty()) {
                    val mediaResource = result[0]
                    val uri = mediaResource.uri
                    val path = mediaResource.path
                    val name = mediaResource.name
                    val mimeType = mediaResource.mimeType
                }
            }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                mediaPickerLauncher.launch(matisse)
            }
        ) {
            Text(text = "我的头像")
            Spacer(Modifier.weight(1f))
            AsyncImage(
                model = mockImage,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Gray)
                    .size(40.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                println("点击了对方头像")
            }
        ) {
            AsyncImage(
                model = mockImage,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Gray)
                    .size(40.dp)
            )
            Spacer(Modifier.weight(1f))
            Text(text = "对方头像")
        }
    }
}

@Preview
@Composable
fun MessageEditViewPreview() {
    MessageEditView()
}