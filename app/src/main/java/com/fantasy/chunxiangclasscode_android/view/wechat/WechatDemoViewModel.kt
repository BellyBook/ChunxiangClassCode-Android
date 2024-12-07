package com.fantasy.chunxiangclasscode_android.view.wechat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.fantasy.chunxiangclasscode_android.model.Message
import com.fantasy.chunxiangclasscode_android.model.MessageType
import github.leavesczy.matisse.MediaResource

class WechatDemoViewModel: ViewModel() {
    var avatar1 by  mutableStateOf<MediaResource?>(null)
    var avatar2 by mutableStateOf<MediaResource?>(null)
    val messages = mutableStateListOf<Message>()

    var inputMessage by mutableStateOf(Message(text = ""))
    fun addMessage() {
        if (inputMessage.text.isEmpty()) {
            return
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
    }
}
