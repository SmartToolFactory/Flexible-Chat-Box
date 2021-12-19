package com.smarttoolfactory.dynamicmessagebox.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.dynamicmessagebox.getRandomColor

@Composable
fun QuotedMessage(
    modifier: Modifier = Modifier,
    quotedMessage: String?=null,
    quotedImage:Int?=null,
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
            if (quotedMessage!=null) {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = quotedMessage,
                        fontSize = 12.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }else if(quotedImage != null) {

            }
        }
    }
}