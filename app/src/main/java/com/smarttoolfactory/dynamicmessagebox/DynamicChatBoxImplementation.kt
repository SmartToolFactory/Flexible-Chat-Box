package com.smarttoolfactory.dynamicmessagebox

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.lib.DynamicChatBox
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


@Composable
fun DynamicChatBoxImplementation() {

    val messages = remember { mutableStateListOf<ChatMessage>() }
    val sdf = remember { SimpleDateFormat("hh:mm a", Locale.ROOT) }

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

                val messageStatus = remember { MessageStatus.values()[Random.nextInt(3)] }
//                val messageStatus = MessageStatus.values()[Random.nextInt(3)]

                DynamicMessageRow(
                    text = message.message,
                    messageTime = sdf.format(message.date),
                    messageStatus = messageStatus
                )
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
fun DynamicMessageRow(
    text: String,
    messageTime: String,
    messageStatus: MessageStatus
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 2.dp, bottom = 2.dp)
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 60.dp, end = 8.dp)
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xffDCF8C6))
        ) {

//            QuotedMessage()

            var color by remember {
                mutableStateOf(Color.Blue)
            }
            DynamicChatBox(
                modifier = Modifier
//                    .fillMaxWidth()
                    .width(200.dp)
                    .background(color),
                text = text,
                messageStat = {
                    MessageTimeText(
                        modifier = Modifier.wrapContentSize(),
                        messageTime = messageTime,
                        messageStatus = messageStatus
                    )
                },
                onMeasured = { chatRowData ->
                    color = when (chatRowData.measuredType) {
                        0 -> Color.Blue
                        1 -> Color.Red
                        2 -> Color.Green
                        else -> Color.Magenta
                    }
                    println("🔥 CHAT ROW DATA: $chatRowData")
                }
            )
        }
    }
}

@Composable
fun QuotedMessage() {
    val color = remember { getRandomColor() }
    Row(
        modifier = Modifier
            .padding(top = 4.dp, start = 4.dp, end =4.dp)
            .fillMaxWidth()
            .background(Color(0xffC0CA33), shape = RoundedCornerShape(8.dp))
    ) {

        Surface(
            color = color,
            modifier = Modifier
                .fillMaxHeight()
                .width(4.dp)
        ) {}

        Column(modifier = Modifier.fillMaxWidth().padding(2.dp)) {
            Text("You", color = color, fontWeight = FontWeight.Bold)
            Text("Quoted message")
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

