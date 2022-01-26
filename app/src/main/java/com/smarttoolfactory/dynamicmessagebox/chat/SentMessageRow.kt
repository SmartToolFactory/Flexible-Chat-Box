package com.smarttoolfactory.dynamicmessagebox.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.dynamicmessagebox.MessageStatus
import com.smarttoolfactory.dynamicmessagebox.ui.theme.SentMessageColor
import com.smarttoolfactory.dynamicmessagebox.ui.theme.SentQuoteColor
import com.smarttoolfactory.chatflexbox.ChatFlexBoxLayout
import com.smarttoolfactory.chatflexbox.ChatRowData
import com.smarttoolfactory.chatflexbox.SubcomposeColumn
import com.smarttoolfactory.chatflexbox.measureText

/**
 * This sent message row uses overloaded [SubcomposeColumn] function only with **content** arg
 * and [QuotedMessage]
 */
@Composable
fun SentMessageRow(
    text: String,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
    messageTime: String,
    messageStatus: MessageStatus
) {

    println("🚕 SentMessageRow() ROOT text: $text")

    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 60.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
    ) {

        // This is chat bubble
        SubcomposeColumn(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(SentMessageColor)
                .clickable { },

            content = {

                println("🚕🚗 SentMessageRow() SubcomposeColumn() text: $text")

                // 💬 Quoted message
                if (quotedMessage != null || quotedImage != null) {
                    QuotedMessage(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                            // 🔥 This is required to set Surface height before text is set
                            .height(IntrinsicSize.Min)
                            .background(SentQuoteColor, shape = RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {

                            },
                        quotedMessage = quotedMessage,
                        quotedImage = quotedImage
                    )
                }

                ChatFlexBoxLayout(
                    modifier = Modifier.padding(
                        start = 2.dp,
                        top = 2.dp,
                        end = 8.dp,
                        bottom = 2.dp
                    ),
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
        )
    }
}

/**
 * This sent message row uses overloaded [SubcomposeColumn] function only with **content** arg
 * and [QuotedMessage].
 *
 * [ChatFlexBoxLayout] is overloaded function that uses `message` and `messageStat` composables
 * and [ChatRowData]
 */
@Composable
fun SentMessageRowAlt1(
    text: String,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
    messageTime: String,
    messageStatus: MessageStatus
) {

    val chatRowData = remember { ChatRowData() }

    println("🚛 SentMessageRowAlt1() ROOT text: $text")


    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 60.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)


    ) {


        // This is chat bubble
        SubcomposeColumn(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(SentMessageColor)
                .clickable { },

            content = {

                println("🚛🏎 SentMessageRowAlt1() text: $text")

                // 💬 Quoted message
                if (quotedMessage != null || quotedImage != null) {
                    QuotedMessage(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                            // 🔥 This is required to set Surface height before text is set
                            .height(IntrinsicSize.Min)
                            .background(SentQuoteColor, shape = RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {

                            },
                        quotedMessage = quotedMessage,
                        quotedImage = quotedImage
                    )
                }

                ChatFlexBoxLayout(
                    modifier = Modifier.padding(
                        start = 2.dp,
                        top = 2.dp,
                        end = 8.dp,
                        bottom = 2.dp
                    ),
                    message = {
                        Text(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                            text = text,
                            fontSize = 16.sp,
                            onTextLayout = {
                                // ⚠️ THIS IS REQUIRED TO MEASURE Text size and get line count
                                measureText(chatRowData, it)
                            }
                        )
                    },
                    messageStat = {
                        MessageTimeText(
                            modifier = Modifier.wrapContentSize(),
                            messageTime = messageTime,
                            messageStatus = messageStatus
                        )
                    },
                    chatRowData = chatRowData
                )
            }
        )
    }
}

/**
 * This sent message row uses [SubcomposeColumn] with **mainContent** and **dependentContent**
 * and [QuotedMessage]
 */
@Composable
fun SentMessageRowAlt2(
    text: String,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
    messageTime: String,
    messageStatus: MessageStatus
) {

    println("🚙 SentMessageRowAlt2() ROOT text: $text")


    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 60.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
    ) {


        // This is chat bubble
        SubcomposeColumn(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(SentMessageColor)
                .clickable { },

            mainContent = {

                println("🚙🚌 SentMessageRowAlt2() MAIN CONTENT text: $text")

                // 💬 Quoted message
                if (quotedMessage != null || quotedImage != null) {
                    QuotedMessage(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                            // 🔥 This is required to set Surface height before text is set
                            .height(IntrinsicSize.Min)
                            .background(SentQuoteColor, shape = RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {},
                        quotedMessage = quotedMessage,
                        quotedImage = quotedImage
                    )
                }
            }, dependentContent = {

                ChatFlexBoxLayout(
                    modifier = Modifier.padding(
                        start = 2.dp,
                        top = 2.dp,
                        end = 8.dp,
                        bottom = 2.dp
                    ),
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
        )
    }
}

/**
 * This sent message row uses [SubcomposeColumn] with **mainContent** and **dependentContent**
 * and [QuotedMessageWithConstraintLayout]
 */
@Composable
fun SentMessageRowAlt3(
    text: String,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
    messageTime: String,
    messageStatus: MessageStatus
) {

    println("🚗 SentMessageRowAlt3() ROOT text: $text")
    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 60.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
    ) {


        // This is chat bubble
        SubcomposeColumn(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(SentMessageColor)
                .clickable { },

            mainContent = {

                println("🚗🚛 SentMessageRowAlt3() text: $text")

                // 💬 Quoted message
                if (quotedMessage != null || quotedImage != null) {
                    QuotedMessageWithConstraintLayout(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                            // ⚠️ This is not working with ConstraintLayout
                            // 🔥 This is required to set Surface height before text is set
                            .height(IntrinsicSize.Min)
                            .background(SentQuoteColor, shape = RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {

                            },
                        quotedMessage = quotedMessage,
                        quotedImage = quotedImage
                    )
                }
            }, dependentContent = {
                ChatFlexBoxLayout(
                    modifier = Modifier.padding(
                        start = 2.dp,
                        top = 2.dp,
                        end = 8.dp,
                        bottom = 2.dp
                    ),
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
        )
    }
}