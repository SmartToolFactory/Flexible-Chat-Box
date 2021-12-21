package com.smarttoolfactory.dynamicmessagebox.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.InsertPhoto
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.dynamicmessagebox.getRandomColor
import com.smarttoolfactory.dynamicmessagebox.ui.theme.QuoteTextColor

// FIXME Set image position to end, currently image position is not set correctly
@Composable
fun QuotedMessage(
    modifier: Modifier = Modifier,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
) {
    val color = remember { getRandomColor() }


    println("🍒 QuotedMessage() color: $color")

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        println("🧨 QUOTE message: $quotedMessage, image: $quotedImage")
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
            Text(
                "You", color = color,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                letterSpacing = 1.sp,
                overflow = TextOverflow.Ellipsis
            )

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (quotedImage != null) {
                        Icon(imageVector = Icons.Default.InsertPhoto, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    Text(
                        color = QuoteTextColor,
                        text = quotedMessage ?: "Photo",
                        fontSize = 12.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }

        if (quotedImage != null) {
            Image(
                painter = painterResource(id = quotedImage),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .layoutId("image")
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
            )
        }
    }

}

@Composable
fun QuotedMessageAlt(
    modifier: Modifier = Modifier,
    quotedMessage: String? = null,
    quotedImage: Int? = null,
) {
    val color = remember { getRandomColor() }

    println("🤔 QuotedMessageAlt() color: $color")
    QuoteImageRow(modifier = modifier.background(Color.Green),
        content = {
            Row() {

                Surface(
                    color = color,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                ) {
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .wrapContentHeight()
                ) {
                    Text(
                        "You", color = color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        letterSpacing = 1.sp,
                        overflow = TextOverflow.Ellipsis
                    )

                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (quotedImage != null) {
                                Icon(
                                    imageVector = Icons.Default.InsertPhoto,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                            }

                            Text(
                                text = quotedMessage ?: "Photo",
                                fontSize = 12.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }
                }
            }
        },
        image = {
            if (quotedImage != null) {
                Image(
                    painter = painterResource(id = quotedImage),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .layoutId("image")
                        .size(50.dp)
                        .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                )
            }
        }
    )
}

/**
 * Row for storing quote title, message or image description and image itself.
 * [image] is positioned end of this layout.
 */
@Composable
private fun QuoteImageRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    image: @Composable (() -> Unit)? = null
) {

    val finalContent = @Composable {
        if (image != null) {
            content()
            image.invoke()
        } else {
            content()
        }
    }

    println("⛺️ CustomRow() START...")

    Layout(modifier = modifier, content = finalContent) { measurables, constraints ->

        println("⛺️ CustomRow() Layout measurables: ${measurables.size}, constraints: $constraints")

        var imageIndex = -1

        val placeables = measurables.mapIndexed { index, measurable ->

            if (measurable.layoutId == "image") {
                imageIndex = index
                println("🍭CustomRow() CUSTOM ROW INDEX: $imageIndex")
            }
            measurable.measure(Constraints(0, constraints.maxWidth))
        }

        val size =
            placeables.fold(IntSize.Zero) { current: IntSize, placeable: Placeable ->

                println("🏠 CustomRow() PLACEABLE width: ${placeable.width}, height: ${placeable.height}")
                IntSize(
                    width = current.width + placeable.width,
                    height = maxOf(current.height, placeable.height)
                )
            }


        val width = size.width.coerceAtLeast(constraints.minWidth)

        println("🚐 CustomRow() TOTAL SIZE: $size, width: $width")
        var x = 0
        layout(width, size.height) {
            placeables.forEachIndexed { index: Int, placeable: Placeable ->
                if (index != imageIndex) {
                    placeable.placeRelative(x, 0)
                    x += placeable.width
                } else {
                    placeable.placeRelative(width - placeable.width, 0)
                }
            }
        }
    }
}