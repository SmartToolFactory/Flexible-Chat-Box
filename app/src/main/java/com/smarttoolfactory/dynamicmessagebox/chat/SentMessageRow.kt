package com.smarttoolfactory.dynamicmessagebox.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.dynamicmessagebox.MessageStatus
import com.smarttoolfactory.dynamicmessagebox.ui.theme.SentMessageColor
import com.smarttoolfactory.dynamicmessagebox.ui.theme.SentQuoteColor
import com.smarttoolfactory.lib.ChatFlexBoxLayout
import com.smarttoolfactory.lib.SubcomposeColumn

/**
 * This sent message row uses [SubcomposeColumn] with **mainContent** and **dependentContent**
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

    println("🚗 SentMessageRow()")
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
                // 💬 Quoted message
                if (quotedMessage != null || quotedImage != null) {
                    QuotedMessage(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                            // ⚠️ This is not working with ConstraintLayout
                            // 🔥 This is required to set Surface height before text is set
//                            .height(IntrinsicSize.Min)
                            .height(60.dp)
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
                        end = 4.dp,
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
 * and [QuotedMessageAlt]
 */
@Composable
fun SentMessageRow2(
    text: String,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
    messageTime: String,
    messageStatus: MessageStatus
) {

    println("🚙 SentMessageRow2()")

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
                // 💬 Quoted message
                if (quotedMessage != null || quotedImage != null) {
                    QuotedMessageAlt(
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
            }, dependentContent = {

                ChatFlexBoxLayout(
                    modifier = Modifier.padding(
                        start = 2.dp,
                        top = 2.dp,
                        end = 4.dp,
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
 * and [QuotedMessageAlt]
 */
@Composable
fun SentMessageRowAlt(
    text: String,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
    messageTime: String,
    messageStatus: MessageStatus
) {

    println("🚕 SentMessageRowAlt()")

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
                // 💬 Quoted message
                if (quotedMessage != null || quotedImage != null) {
                    QuotedMessageAlt(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                            // 🔥 This is required to set Surface height before text is set
//                            .height(IntrinsicSize.Min)
                            .size(60.dp)
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
                        end = 4.dp,
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