package com.smarttoolfactory.dynamicmessagebox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.dynamicmessagebox.chat.*
import com.smarttoolfactory.dynamicmessagebox.ui.ChatAppbar
import com.smarttoolfactory.dynamicmessagebox.ui.ChatInput
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


@Composable
fun FullChatImplementation() {

    val messages = remember { mutableStateListOf<ChatMessage>() }
    val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffFBE9E7))
    ) {

        ChatAppbar()

        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = scrollState,
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
        ) {
            items(messages) { message: ChatMessage ->

                // Remember random stats icon to not create at each recomposition
                val messageStatus = remember { MessageStatus.values()[Random.nextInt(3)] }

                // Toggle between sent and received message
                when (message.id.toInt() % 6) {
                    1 -> {
                        SentMessageRow(
                            text = message.message,
                            messageTime = sdf.format(System.currentTimeMillis()),
                            messageStatus = messageStatus
                        )

                    }
                    2 -> {
                        ReceivedMessageRow(
                            text = message.message,
                            messageTime = sdf.format(System.currentTimeMillis()),
                        )
                    }
                    3 -> {
                        SentMessageRow(
                            text = message.message,
                            quotedMessage = "Quote message",
                            messageTime = sdf.format(System.currentTimeMillis()),
                            messageStatus = messageStatus
                        )

                    }
                    4 -> {
                        ReceivedMessageRow(
                            text = message.message,
                            quotedMessage = "Quote",
                            messageTime = sdf.format(System.currentTimeMillis()),
                        )

                    }
                    5 -> {
                        SentMessageRow(
                            text = message.message,
                            quotedImage = R.drawable.landscape1,
                            messageTime = sdf.format(System.currentTimeMillis()),
                            messageStatus = messageStatus
                        )

                    }
                    else -> {
                        ReceivedMessageRow(
                            text = message.message,
                            quotedImage = R.drawable.landscape2,
                            messageTime = sdf.format(System.currentTimeMillis()),
                        )
                    }
                }
            }
        }

        ChatInput(
            onMessageChange = { messageContent ->
                messages.add(
                    ChatMessage(
                        (messages.size + 1).toLong(),
                        messageContent,
                        System.currentTimeMillis()
                    )
                )

                coroutineScope.launch {
                    scrollState.animateScrollToItem(messages.size - 1)
                }

            }
        )
    }
}

enum class MessageStatus {
    PENDING, RECEIVED, READ
}

data class ChatMessage(
    val id: Long,
    var message: String,
    var date: Long
)

fun getRandomColor() = Color(
    red = Random.nextInt(256),
    green = Random.nextInt(256),
    blue = Random.nextInt(256),
    alpha = 255
)

