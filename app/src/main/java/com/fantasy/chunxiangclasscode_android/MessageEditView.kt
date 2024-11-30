package com.fantasy.chunxiangclasscode_android

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import github.leavesczy.matisse.GlideImageEngine
import github.leavesczy.matisse.ImageEngine
import github.leavesczy.matisse.Matisse
import github.leavesczy.matisse.MatisseContract
import github.leavesczy.matisse.MediaResource
import github.leavesczy.matisse.MediaType
import kotlinx.parcelize.Parcelize

@Parcelize
class CoilImageEngine : ImageEngine {

    @Composable
    override fun Thumbnail(mediaResource: MediaResource) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = mediaResource.uri,
            contentScale = ContentScale.Crop,
            contentDescription = mediaResource.name
        )
    }

    @Composable
    override fun Image(mediaResource: MediaResource) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = mediaResource.uri,
            contentScale = ContentScale.Crop,
            contentDescription = mediaResource.name
        )
    }

}


val matisse = Matisse(
    maxSelectable = 1,
    imageEngine = CoilImageEngine(),
    mediaType = MediaType.ImageOnly,
    fastSelect = true
)

// localhost 127.0.0.1
val mockImage: String
    get() {
        return "https://picsum.photos/200/200"
    }

@Composable
fun MessageEditView(
    avatar1: MutableState<MediaResource?>,
    avatar2: MutableState<MediaResource?>,
    messages: SnapshotStateList<Message>,
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .padding(12.dp)
            .fillMaxHeight(0.95f)
            .fillMaxWidth()
    ) {
        // 打开相册选照片的两种方案
        // 1. 用别人写的代码-第三方库
        // 2. 使用系统API
        // 3. 自 ji 写
        // gradle 是什么？
        // 1. 依赖管理工具
        // 2. 构建工具

        // 我的头像
        item {

            val mediaPickerLauncher1 =
                rememberLauncherForActivityResult(contract = MatisseContract()) { result: List<MediaResource>? ->
                    avatar1.value = result?.firstOrNull()
                }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    mediaPickerLauncher1.launch(matisse)
                }
            ) {
                Text(text = "我的头像")
                Spacer(Modifier.weight(1f))
                AsyncImage(
                    model = avatar1.value?.uri,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Gray)
                        .size(40.dp)
                )
            }
        }

        // 对方头像
        item {
            val mediaPickerLauncher2 =
                rememberLauncherForActivityResult(contract = MatisseContract()) { result: List<MediaResource>? ->
                    avatar2.value = result?.firstOrNull()
                }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    mediaPickerLauncher2.launch(matisse)
                }
            ) {
                AsyncImage(
                    model = avatar2.value?.uri,
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

        // 添加消息
        item {
            var inputMessage by remember { mutableStateOf(Message(text = "")) }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        if (inputMessage.text.isEmpty()) {
                            return@Button
                        }

                        if (inputMessage.type == MessageType.trans) {
                            val isMine = inputMessage.isMine
                            messages += inputMessage.copy(
                                text = "已被接收",
                                amount = inputMessage.text
                            )
                            messages += inputMessage.copy(
                                text = "已接收",
                                amount = inputMessage.text,
                                isMine = !isMine
                            )
                        } else {
                            messages += inputMessage.copy()
                        }
                        inputMessage = inputMessage.copy(text = "")
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "添加一条消息")
                }

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(true, false).forEach { mine ->
                        SegmentedButton(
                            shape = RectangleShape,
                            selected = inputMessage.isMine == mine,
                            onClick = {
                                inputMessage = inputMessage.copy(isMine = mine)
                            }
                        ) {
                            Text(text = if (mine) "我的消息" else "对方的消息")
                        }
                    }
                }
                TextField(
                    value = inputMessage.text,
                    onValueChange = { inputMessage = inputMessage.copy(text = it) },
                    modifier = Modifier.fillMaxWidth()
                )

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    MessageType.entries.forEach { type ->
                        SegmentedButton(
                            shape = RectangleShape,
                            selected = inputMessage.type == type,
                            onClick = {
                                inputMessage = inputMessage.copy(type = type)
                            }
                        ) {
                            Text(text = type.title)
                        }
                    }
                }


                messages.forEach { message ->
                    var expanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier.clickable {
                            expanded = true
                        }
                    ) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            Text(text = "删除消息", modifier = Modifier.clickable {
                                messages.remove(message)
                            })
                        }

                        MessageCell(
                            avatar1 = avatar1,
                            avatar2 = avatar2,
                            message = message
                        )
                    }
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun MessageEditViewPreview() {
    MessageEditView(
        avatar1 = remember { mutableStateOf(null) },
        avatar2 = remember { mutableStateOf(null) },
        messages = remember { mutableStateListOf() }
    )
}