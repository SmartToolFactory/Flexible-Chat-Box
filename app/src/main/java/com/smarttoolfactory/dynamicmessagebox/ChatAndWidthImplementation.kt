package com.smarttoolfactory.dynamicmessagebox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.dynamicmessagebox.ui.MessageTimeText
import com.smarttoolfactory.dynamicmessagebox.ui.QuotedMessage
import com.smarttoolfactory.lib.DynamicChatBox
import com.smarttoolfactory.lib.DynamicWidthLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@Composable
fun ChatAndWidthImplementation() {

    val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        var quote by remember { mutableStateOf("Quote") }
        var message by remember { mutableStateOf("Message") }
        val messageStatus = remember { MessageStatus.values()[Random.nextInt(3)] }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 2.dp, bottom = 2.dp)
            .background(Color.LightGray)
                    .padding(start = 60.dp, end = 8.dp)

            ) {
                Message(
                    text = message,
                    messageTime = sdf.format(System.currentTimeMillis()),
                    messageStatus = messageStatus
                )
            }

//            SentMessageRow(
//                text = message,
//                quotedMessage = quote,
//                messageTime = sdf.format(System.currentTimeMillis()),
//                messageStatus = messageStatus
//            )
//
//            SentMessageRow(
//                text = message,
//                quotedMessage = quote,
//                messageTime = sdf.format(System.currentTimeMillis()),
//                messageStatus = messageStatus
//            )
        }

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = quote,
            label = { Text("Main") },
            placeholder = { Text("Set text to change main width") },
            onValueChange = { newValue: String ->
                quote = newValue
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = message,
            label = { Text("Dependent") },
            placeholder = { Text("Set text to change dependent width") },
            onValueChange = { newValue ->
                message = newValue
            }
        )

    }
}

@Composable
private fun Message(
    text: String,
    messageTime: String,
    messageStatus: MessageStatus
) {
    var color by remember {
        mutableStateOf(Color.Yellow)
    }

    DynamicChatBox(
        modifier = Modifier
            .background(color)
//            .padding(start = 2.dp, top = 2.dp, end = 4.dp, bottom = 2.dp)
        ,
        text = text,
        messageStat = {
            MessageTimeText(
                modifier = Modifier.wrapContentSize(),
                messageTime = messageTime,
                messageStatus = messageStatus
            )
        },
        onMeasure = { chatRowData ->
            color = when (chatRowData.measuredType) {
                0 -> Color.Yellow
                1 -> Color.Red
                2 -> Color.Green
                else -> Color.Magenta
            }
//            println("🔥 IMPLEMENTATION-> $chatRowData")
        },
    )
}

@Composable
private fun SentMessageRow(
    text: String,
    quotedMessage: String,
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
                    quotedMessage = quotedMessage
                )
            }
        ) {

            DynamicChatBox(
                modifier = Modifier.padding(start = 2.dp, top = 2.dp, end = 4.dp, bottom = 2.dp),
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