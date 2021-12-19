package com.smarttoolfactory.dynamicmessagebox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.lib.DynamicChatBox
import com.smarttoolfactory.lib.DynamicWidthLayout
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

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
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

        ChatInput(onMessageChange = { messageContent ->
            messages.add(
                ChatMessage(
                    (messages.size + 1).toLong(),
                    messageContent,
                    System.currentTimeMillis()
                )
            )
        })
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
        DynamicWidthLayout(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xffDCF8C6))
                .clickable { },

            mainContent = {
                // 💬 Quoted message
                QuotedMessage(
                    modifier = Modifier
                        .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                        // 🔥 This is required to set Surface height before text is set
                        .height(IntrinsicSize.Min)
                        .background(Color(0xffdfeed2), shape = RoundedCornerShape(8.dp))
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

            var color by remember {
                mutableStateOf(Color.Blue)
            }

            DynamicChatBox(
                modifier = Modifier
//                    .background(color)
                    .padding(start = 2.dp, top = 2.dp, end = 4.dp, bottom = 2.dp),
                text = text,
                messageStat = {
                    MessageTimeText(
                        modifier = Modifier.wrapContentSize(),
                        messageTime = messageTime,
                        messageStatus = messageStatus
                    )
                },
//                onMeasured = { chatRowData ->
//                    color = when (chatRowData.measuredType) {
//                        0 -> Color.Blue
//                        1 -> Color.Red
//                        2 -> Color.Green
//                        else -> Color.Magenta
//                    }
//                    println("🔥 IMPLEMENTATION-> $chatRowData")
//                },
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
        DynamicWidthLayout(
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
                        .background(Color(0xffECEFF1), shape = RoundedCornerShape(8.dp))
                        .clip(shape = RoundedCornerShape(8.dp))
                        .clickable {

                        },
                    quotedMessage = "Quoted long message"
                )
            }
        ) {

            println(
                "📝 ReceivedMessageRow() in dependent()"
                        + " IntSize: $it"
            )


            DynamicChatBox(
                modifier = Modifier
                    .padding(start = 2.dp, top = 2.dp, end = 4.dp, bottom = 2.dp),
                text = text,
                messageStat = {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            modifier = Modifier
                                .padding(top = 1.dp, bottom = 1.dp),
                            text = messageTime,
                            fontSize = 12.sp
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun QuotedMessage(
    modifier: Modifier = Modifier,
    quotedMessage: String
) {
    val color = remember { getRandomColor() }
    Row(
        modifier = modifier
    ) {

        Surface(
            color = color,
            modifier = Modifier
                .fillMaxHeight()
                .width(4.dp)
        ) {
        }

        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text("You", color = color, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = quotedMessage,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun MessageTimeText(
    modifier: Modifier = Modifier,
    messageTime: String,
    messageStatus: MessageStatus
) {
    val messageStat = remember {
        messageStatus
    }

    Row(
        modifier = modifier.padding(end = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                modifier = Modifier
                    .padding(top = 1.dp, bottom = 1.dp),
                text = messageTime,
                fontSize = 12.sp
            )
        }

        Icon(
            modifier = Modifier
                .size(16.dp, 12.dp)
                .padding(start = 4.dp),
            imageVector = when (messageStat) {
                MessageStatus.PENDING -> {
                    Icons.Default.AccessTime
                }
                MessageStatus.RECEIVED -> {
                    Icons.Default.Done
                }
                else -> Icons.Default.DoneAll
            },
            tint = if (messageStat == MessageStatus.READ) Color(0xff0288D1)
            else Color(0xff424242),
            contentDescription = "messageStatus"
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

