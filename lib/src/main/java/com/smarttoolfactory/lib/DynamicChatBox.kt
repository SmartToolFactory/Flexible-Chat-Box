package com.smarttoolfactory.lib

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DynamicChatBox(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = 16.sp,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    messageStat: @Composable () -> Unit,
    onMeasured: ((ChatRowData) -> Unit)? = null
) {
    val chatRowData = remember { ChatRowData() }

    val content = @Composable {

        Message(
            modifier = Modifier,
            text = text,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            onTextLayout = { textLayoutResult ->
                chatRowData.lineCount = textLayoutResult.lineCount
                chatRowData.lastLineWidth =
                    textLayoutResult.getLineRight(chatRowData.lineCount - 1)
            }
        )

        messageStat()
    }

    Layout(
        modifier = modifier,
        content = content
    ) { measurables: List<Measurable>, constraints: Constraints ->

        if (measurables.size != 2)
            throw IllegalArgumentException("There should be 2 components for this layout")

        println("⚠️ CHAT const maxWidth: ${constraints.maxWidth}, min: ${constraints.minWidth}")

        val placeables: List<Placeable> = measurables.map { measurable ->
            // Measure each child maximum constraints since message can cover all of the available
            // space by parent
            measurable.measure(Constraints(0, constraints.maxWidth))
        }

        val message = placeables.first()
        val status = placeables.last()

        // calculate chat row dimensions are not  based on message and status positions
        if (chatRowData.width == 0 || chatRowData.height == 0) {
            // Constrain wit max width instead of longest sibling
            // since this composable can be longest of siblings after calculation
            chatRowData.parentWidth = constraints.maxWidth
            calculateChatWidthAndHeight(text, chatRowData, message, status)
            // Parent width of this chat row is either result of width calculation
            // or quote or other sibling width if they are longer than calculated width.
            chatRowData.parentWidth =
                chatRowData.width.coerceAtLeast(minimumValue = constraints.minWidth)
        }

        println(
            "⚠️⚠️ CHAT constraints.minWidth: ${constraints.minWidth}, " +
                    "max: ${constraints.maxWidth}, " +
                    "CHAT_ROW_DATA: $chatRowData"
        )

        // Send measurement results if requested by Composable
        onMeasured?.invoke(chatRowData)

        layout(width = chatRowData.parentWidth, height = chatRowData.height) {

            println(
                "⚠️⚠️⚠️ CHAT layout() status x: ${chatRowData.parentWidth - status.width}, " +
                        "y: ${chatRowData.height - status.height}"
            )

            message.placeRelative(0, 0)
            status.placeRelative(
                chatRowData.parentWidth - status.width,
                chatRowData.height - status.height
            )
        }
    }
}

private fun calculateChatWidthAndHeight(
    text: String,
    chatRowData: ChatRowData,
    message: Placeable,
    status: Placeable?,
) {

    val lineCount = chatRowData.lineCount
    val lastLineWidth = chatRowData.lastLineWidth
    val parentWidth = chatRowData.parentWidth


    if (status == null) {
        chatRowData.width = message.width
        chatRowData.height = message.height
    } else {

        println("🌽 CHAT calculate() STATUS width: ${status.width}, message width: ${message.width}, parent: ${chatRowData.parentWidth}")

        if (lineCount > 1 && lastLineWidth + status.measuredWidth < message.measuredWidth) {
            chatRowData.width = message.measuredWidth
            chatRowData.height = message.measuredHeight
            chatRowData.measuredType = 0
            println(
                "🔥 CHAT calculate(): $text, parentWidth: $parentWidth,  " +
                        "lineCount: $lineCount, lastLineWidth: $lastLineWidth, " +
                        "message.width: ${message.width}, status.measuredWidth: ${status.measuredWidth}"
            )
        } else if (lineCount > 1 && lastLineWidth + status.measuredWidth >= parentWidth) {
            chatRowData.width = message.measuredWidth
            chatRowData.height = message.measuredHeight + status.measuredHeight
            chatRowData.measuredType = 1
            println(
                "🤔 CHAT calculate(): $text, parentWidth: $parentWidth,  " +
                        "lineCount: $lineCount, lastLineWidth: $lastLineWidth, " +
                        "message.width: ${message.width}, status.measuredWidth: ${status.measuredWidth}"
            )
        } else if (lineCount == 1 && message.width + status.measuredWidth >= parentWidth) {
            chatRowData.width = message.measuredWidth
            chatRowData.height = message.measuredHeight + status.measuredHeight
            chatRowData.measuredType = 2
            println(
                "🎃 CHAT calculate(): $text, parentWidth: $parentWidth,  " +
                        "lineCount: $lineCount, lastLineWidth: $lastLineWidth, " +
                        "message.width: ${message.width}, status.measuredWidth: ${status.measuredWidth}"
            )
        } else {
            chatRowData.width = message.measuredWidth + status.measuredWidth
            chatRowData.height = message.measuredHeight
            chatRowData.measuredType = 3
            println(
                "🚀 CHAT calculate(): $text, parentWidth: $parentWidth,  " +
                        "lineCount: $lineCount, lastLineWidth: $lastLineWidth, " +
                        "message.width: ${message.width}, status.measuredWidth: ${status.measuredWidth}"
            )
        }
    }
}

@Composable
private fun Message(
    modifier: Modifier = Modifier,
    text: String,
    onTextLayout: (TextLayoutResult) -> Unit,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = 16.sp,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier
            .wrapContentSize()
            .padding(horizontal = 6.dp, vertical = 6.dp),
        text = text,
        onTextLayout = onTextLayout,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
    )
}


data class ChatRowData(
    var lastLineWidth: Float = 0f,
    var lineCount: Int = 0,
    var width: Int = 0,
    var height: Int = 0,
    var parentWidth: Int = 0,
    var measuredType: Int = 0,
) {


    override fun toString(): String {
        return "ChatRowData lastLineWidth: $lastLineWidth, lineCount: $lineCount, " +
                "width: $width, height: $height, " +
                "parentWidth: $parentWidth, measuredType: $measuredType"
    }
}

