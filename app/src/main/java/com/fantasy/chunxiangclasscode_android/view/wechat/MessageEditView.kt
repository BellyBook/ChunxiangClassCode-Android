package com.fantasy.chunxiangclasscode_android.view.wechat

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.fantasy.chunxiangclasscode_android.design.avatarStyle
import com.fantasy.chunxiangclasscode_android.model.MessageType
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
fun MessageEditView(vm: WechatDemoViewModel = viewModel()) {

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
        item { Avatar1() }

        // 对方头像
        item { Avatar2() }

        // 添加消息
        item {
            AddMessage()
        }


    }
}

@Composable
private fun AddMessage(vm: WechatDemoViewModel = viewModel()) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = {
                vm.addMessage()
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
                    selected = vm.inputMessage.isMine == mine,
                    onClick = {
                        vm.inputMessage = vm.inputMessage.copy(isMine = mine)
                    }
                ) {
                    Text(text = if (mine) "我的消息" else "对方的消息")
                }
            }
        }
        TextField(
            value = vm.inputMessage.text,
            onValueChange = { vm.inputMessage = vm.inputMessage.copy(text = it) },
            modifier = Modifier.fillMaxWidth()
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            MessageType.entries.forEach { type ->
                SegmentedButton(
                    shape = RectangleShape,
                    selected = vm.inputMessage.type == type,
                    onClick = {
                        vm.inputMessage = vm.inputMessage.copy(type = type)
                    }
                ) {
                    Text(text = type.title)
                }
            }
        }


        vm.messages.forEach { message ->
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
                        vm.messages.remove(message)
                    })
                }

                MessageCell(message = message)
            }
        }
    }
}

@Composable
private fun Avatar2(vm: WechatDemoViewModel = viewModel()) {
    val mediaPickerLauncher2 =
        rememberLauncherForActivityResult(contract = MatisseContract()) { result: List<MediaResource>? ->
            vm.avatar2 = result?.firstOrNull()
        }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            mediaPickerLauncher2.launch(matisse)
        }
    ) {
        AsyncImage(
            model = vm.avatar2?.uri,
            contentDescription = null,
            modifier = Modifier.avatarStyle()
        )
        Spacer(Modifier.weight(1f))
        Text(text = "对方头像")
    }
}

@Composable
private fun Avatar1(vm: WechatDemoViewModel = viewModel()) {
    val mediaPickerLauncher1 =
        rememberLauncherForActivityResult(contract = MatisseContract()) { result: List<MediaResource>? ->
            vm.avatar1 = result?.firstOrNull()
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
            model = vm.avatar1?.uri,
            contentDescription = null,
            modifier = Modifier.avatarStyle()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessageEditViewPreview() {
    MessageEditView()
}