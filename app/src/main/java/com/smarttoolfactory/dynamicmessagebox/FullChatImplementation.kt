package com.smarttoolfactory.dynamicmessagebox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.dynamicmessagebox.chat.MessageTimeText
import com.smarttoolfactory.dynamicmessagebox.chat.QuotedMessage
import com.smarttoolfactory.dynamicmessagebox.chat.ReceivedMessageRow
import com.smarttoolfactory.dynamicmessagebox.chat.SentMessageRow
import com.smarttoolfactory.dynamicmessagebox.ui.ChatAppbar
import com.smarttoolfactory.dynamicmessagebox.ui.ChatInput
import com.smarttoolfactory.dynamicmessagebox.ui.theme.ReceivedQuoteColor
import com.smarttoolfactory.dynamicmessagebox.ui.theme.SentMessageColor
import com.smarttoolfactory.dynamicmessagebox.ui.theme.SentQuoteColor
import com.smarttoolfactory.lib.ChatFlexBoxLayout
import com.smarttoolfactory.lib.SubcomposeColumn
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
                if (message.id.toInt() % 2 == 1) {
                    SentMessageRow(
                        text = message.message,
                        messageTime = sdf.format(message.date),
                        messageStatus = messageStatus
                    )
                } else {
                    ReceivedMessageRow(
                        text = message.message,
                        messageTime = sdf.format(message.date)
                    )
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

data class ChatMessage(val id: Long, var message: String, var date: Long)

fun getRandomColor() = Color(
    red = Random.nextInt(256),
    green = Random.nextInt(256),
    blue = Random.nextInt(256),
    alpha = 255
)

