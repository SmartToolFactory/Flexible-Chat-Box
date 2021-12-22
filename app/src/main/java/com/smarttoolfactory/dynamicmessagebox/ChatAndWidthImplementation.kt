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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.dynamicmessagebox.chat.*
import com.smarttoolfactory.dynamicmessagebox.ui.theme.Orange400
import com.smarttoolfactory.dynamicmessagebox.ui.theme.SentQuoteColor
import com.smarttoolfactory.lib.ChatFlexBoxLayout
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

@Composable
fun ChatAndWidthImplementation() {

    val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }
    var quote by remember { mutableStateOf("Quote Message") }
    var message by remember { mutableStateOf("Very Long Message") }
    val messageStatus = remember { MessageStatus.values()[Random.nextInt(3)] }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffFBE9E7))
            .padding(8.dp)
    ) {


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
                    .padding(start = 60.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)

            ) {
                MessageRow(
                    text = "Single line message",
                    messageTime = sdf.format(System.currentTimeMillis()),
                    messageStatus = messageStatus
                )
                Spacer(modifier = Modifier.height(4.dp))
                MessageRow(
                    text = "Message ad stat is longer than parent",
                    messageTime = sdf.format(System.currentTimeMillis()),
                    messageStatus = messageStatus
                )
                Spacer(modifier = Modifier.height(4.dp))

                MessageRow(
                    text = "Multiple line sample message that shorter \n" +
                            "second line shorter",
                    messageTime = sdf.format(System.currentTimeMillis()),
                    messageStatus = messageStatus
                )
                Spacer(modifier = Modifier.height(4.dp))
                MessageRow(
                    text = "Multiple line sample message that shorter \n" +
                            "second line longer than first line.........",
                    messageTime = sdf.format(System.currentTimeMillis()),
                    messageStatus = messageStatus
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            MessageRow(
                text = message,
                messageTime = sdf.format(System.currentTimeMillis()),
                messageStatus = messageStatus
            )
            Spacer(modifier = Modifier.height(4.dp))


            // FIXME This one is not correctly positioned, use barrier with ConstraintLayout
//            QuotedMessage(
//                modifier = Modifier
//                    .padding(top = 4.dp, start = 4.dp, end = 4.dp)
//                    // 🔥 This is required to set Surface height before text is set
//                    .height(IntrinsicSize.Min)
//                    .background(SentQuoteColor, shape = RoundedCornerShape(8.dp))
//                    .clip(shape = RoundedCornerShape(8.dp))
//                    .clickable {
//
//                    },
//                quotedMessage = quote
//            )

            QuotedMessage(
                modifier = Modifier
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                    // ⚠️ This is not working with ConstraintLayout
                    // 🔥 This is required to set Surface height before text is set
                    .background(SentQuoteColor, shape = RoundedCornerShape(8.dp))
                    .height(IntrinsicSize.Min)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clickable {},
                quotedImage = R.drawable.landscape1
            )

            QuotedMessageAlt(
                modifier = Modifier
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                    // 🔥 This is required to set Surface height before text is set
                    .background(SentQuoteColor, shape = RoundedCornerShape(8.dp))
                    .height(IntrinsicSize.Min)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clickable {},
//                quotedMessage = quote
                quotedImage = R.drawable.landscape1
            )

            SentMessageRowAlt(
                text = message,
                messageTime = sdf.format(System.currentTimeMillis()),
                messageStatus = messageStatus
            )

            SentMessageRowAlt(
                text = message,
                quotedMessage = quote,
                messageTime = sdf.format(System.currentTimeMillis()),
                messageStatus = messageStatus
            )

            SentMessageRowAlt(
                text = message,
                quotedImage = R.drawable.landscape1,
                messageTime = sdf.format(System.currentTimeMillis()),
                messageStatus = messageStatus
            )

            ReceivedMessageRowAlt(
                text = message,
                messageTime = sdf.format(System.currentTimeMillis()),
            )

            ReceivedMessageRowAlt(
                text = message,
                quotedMessage = quote,
                messageTime = sdf.format(System.currentTimeMillis()),
            )

            ReceivedMessageRowAlt(
                text = message,
                quotedImage = R.drawable.landscape2,
                messageTime = sdf.format(System.currentTimeMillis()),
            )
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
private fun MessageRow(
    text: String,
    messageTime: String,
    messageStatus: MessageStatus
) {
    var color by remember {
        mutableStateOf(Orange400)
    }

    ChatFlexBoxLayout(
        modifier = Modifier
            .background(color, shape = RoundedCornerShape(8.dp))
            .padding(start = 2.dp, top = 2.dp, end = 4.dp, bottom = 2.dp),
        text = text,
        messageStat = {
            MessageTimeText(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(end = 6.dp),
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
        }
    )
}
