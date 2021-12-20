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
import com.smarttoolfactory.dynamicmessagebox.ui.ChatAppbar
import com.smarttoolfactory.dynamicmessagebox.ui.ChatInput
import com.smarttoolfactory.dynamicmessagebox.ui.MessageTimeText
import com.smarttoolfactory.dynamicmessagebox.ui.QuotedMessage
import com.smarttoolfactory.dynamicmessagebox.ui.theme.QuotedMessageColor
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

@Composable
private fun SentMessageRow(
    text: String,
    messageTime: String,
    messageStatus: MessageStatus
) {
    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 2.dp, bottom = 2.dp)
//            .background(Color.LightGray)
            .padding(start = 60.dp, end = 8.dp)

    ) {


        // This is chat bubble
        SubcomposeColumn(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(SentMessageColor)
                .clickable { },

            mainContent = {
                // 💬 Quoted message
                QuotedMessage(
                    modifier = Modifier
                        .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                        // 🔥 This is required to set Surface height before text is set
                        .height(IntrinsicSize.Min)
                        .background(SentQuoteColor, shape = RoundedCornerShape(8.dp))
                        .clip(shape = RoundedCornerShape(8.dp))
                        .clickable {

                        },
                    quotedMessage = "Quoted long message"
                )
            }
        ) {

            println(
                "📝 SentMessageRow() in dependent()"
                        + " IntSize: $it"
            )

            ChatFlexBoxLayout(
                modifier = Modifier
                    .padding(start = 2.dp, top = 2.dp, end = 4.dp, bottom = 2.dp),
                text = text,
                messageStat = {
                    MessageTimeText(
                        modifier = Modifier.wrapContentSize(),
                        messageTime = messageTime,
                        messageStatus = messageStatus
                    )
                }
            )
        }
    }
}

@Composable
private fun ReceivedMessageRow(text: String, messageTime: String) {
    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 2.dp, bottom = 2.dp)
//            .background(Color.LightGray)
            .padding(start = 8.dp, end = 60.dp)

    ) {

        // This is chat bubble
        SubcomposeColumn(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable { },

            mainContent = {
                // 💬 Quoted message
                QuotedMessage(
                    modifier = Modifier
                        .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                        // 🔥 This is required to set Surface height before text is set
                        .height(IntrinsicSize.Min)
                        .background(QuotedMessageColor, shape = RoundedCornerShape(8.dp))
                        .clip(shape = RoundedCornerShape(8.dp))
                        .clickable {

                        },
                    quotedImage = R.drawable.landscape1
                )
            }
        ) {

            println("📝 ReceivedMessageRow() in dependent() IntSize: $it")

            ChatFlexBoxLayout(
                modifier = Modifier
                    .padding(start = 2.dp, top = 2.dp, end = 4.dp, bottom = 2.dp),
                text = text,
                messageStat = {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            modifier = Modifier.padding(top = 1.dp, bottom = 1.dp, end = 4.dp),
                            text = messageTime,
                            fontSize = 12.sp
                        )
                    }
                }
            )
        }
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

