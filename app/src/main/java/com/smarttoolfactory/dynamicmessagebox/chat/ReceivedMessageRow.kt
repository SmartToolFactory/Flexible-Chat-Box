package com.smarttoolfactory.dynamicmessagebox.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.dynamicmessagebox.ui.theme.ReceivedQuoteColor
import com.smarttoolfactory.lib.ChatFlexBoxLayout
import com.smarttoolfactory.lib.SubcomposeColumn

var recipientRegisteredName = "+44 515 1245 768"
var isRecipientRegistered = true
var recipientOriginalName = "Some user"


/**
 * This received message row uses overloaded [SubcomposeColumn] function only with **content** arg
 * and [QuotedMessage]
 */
@Composable
fun ReceivedMessageRow(
    text: String,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
    messageTime: String,
) {
    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 8.dp, end = 60.dp, top = 2.dp, bottom = 2.dp)

    ) {

        // This is chat bubble
        SubcomposeColumn(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable { },
            content = {
                RecipientName(
                    name = recipientRegisteredName,
                    isName = isRecipientRegistered,
                    altName = recipientOriginalName
                )

                if (quotedMessage != null || quotedImage != null) {
                    // 💬 Quoted message
                    QuotedMessage(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                            // 🔥 This is required to set Surface height before text is set
                            .height(IntrinsicSize.Min)
                            .background(ReceivedQuoteColor, shape = RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {

                            },
                        quotedMessage = quotedMessage,
                        quotedImage = quotedImage
                    )
                }

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
        )
    }
}

/**
 * This received message row uses [SubcomposeColumn] with **mainContent** and **dependentContent**
 * and [QuotedMessageWithConstraintLayout]
 */
@Composable
fun ReceivedMessageRowAlt1(
    text: String,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
    messageTime: String,
) {
    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 8.dp, end = 60.dp, top = 2.dp, bottom = 2.dp)

    ) {

        // This is chat bubble
        SubcomposeColumn(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable { },
            content = {

                RecipientName(
                    name = recipientRegisteredName,
                    isName = isRecipientRegistered,
                    altName = recipientOriginalName
                )

                if (quotedMessage != null || quotedImage != null) {
                    // 💬 Quoted message
                    QuotedMessageWithConstraintLayout(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                            // 🔥 This is required to set Surface height before text is set
                            .height(IntrinsicSize.Min)
                            .background(ReceivedQuoteColor, shape = RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {

                            },
                        quotedMessage = quotedMessage,
                        quotedImage = quotedImage
                    )
                }

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
        )
    }
}

/**
 * This received message row uses [SubcomposeColumn] with **mainContent** and **dependentContent**
 * and [QuotedMessage]
 */
@Composable
fun ReceivedMessageRowAlt2(
    text: String,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
    messageTime: String,
) {
    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 8.dp, end = 60.dp, top = 2.dp, bottom = 2.dp)

    ) {

        // This is chat bubble
        SubcomposeColumn(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable { },
            mainContent = {

                // TODO THIS composable is not resized correctly, use other SubcomposeColumn,
                //  this is for demonstration
                RecipientName(
                    name = recipientRegisteredName,
                    isName = isRecipientRegistered,
                    altName = recipientOriginalName
                )

                if (quotedMessage != null || quotedImage != null) {
                    // 💬 Quoted message
                    QuotedMessage(
                        modifier = Modifier
                            .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                            // 🔥 This is required to set Surface height before text is set
                            .height(IntrinsicSize.Min)
                            .background(ReceivedQuoteColor, shape = RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .clickable {},
                        quotedMessage = quotedMessage,
                        quotedImage = quotedImage
                    )
                }
            },
            dependentContent = {
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
        )
    }
}
