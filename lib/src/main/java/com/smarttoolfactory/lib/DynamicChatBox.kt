package com.smarttoolfactory.lib

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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

enum class SlotsEnum { Main, Dependent }

//@Composable
//fun SubComposeChatLayout(
//    modifier: Modifier = Modifier,
//    text: String,
//    color: Color = Color.Unspecified,
//    fontSize: TextUnit = 16.sp,
//    fontStyle: FontStyle? = null,
//    fontWeight: FontWeight? = null,
//    fontFamily: FontFamily? = null,
//    letterSpacing: TextUnit = TextUnit.Unspecified,
//    textDecoration: TextDecoration? = null,
//    textAlign: TextAlign? = null,
//    lineHeight: TextUnit = TextUnit.Unspecified,
//    overflow: TextOverflow = TextOverflow.Clip,
//    softWrap: Boolean = true,
//    maxLines: Int = Int.MAX_VALUE,
//    messageStat: @Composable (ChatRowData) -> Unit
//) {
//
//    val chatRowData = remember { ChatRowData() }
//
//    val content = @Composable {
//
//        Message(
//            modifier = Modifier,
//            text = text,
//            onTextLayout = { textLayoutResult ->
//                chatRowData.lineCount = textLayoutResult.lineCount
//                chatRowData.lastLineWidth =
//                    textLayoutResult.getLineRight(chatRowData.lineCount - 1)
//            }
//        )
//    }
//
//    SubcomposeLayout(modifier = modifier) { constraints ->
//
//        val mainPlaceables: List<Placeable> = subcompose(SlotsEnum.Main, content).map {
//            it.measure(constraints)
//        }
//
//        val maxSize =
//            mainPlaceables.fold(IntSize.Zero) { currentMax: IntSize, placeable: Placeable ->
//                IntSize(
//                    width = maxOf(currentMax.width, placeable.width),
//                    height = maxOf(currentMax.height, placeable.height)
//                )
//            }
//
//        layout(maxSize.width, maxSize.height) {
//
//            println("🔥 SubcomposeLayout-> layout() maxSize width: ${maxSize.width}, height: ${maxSize.height}")
//            mainPlaceables.forEach { it.placeRelative(0, 0) }
//
//            subcompose(SlotsEnum.Dependent) {
//                dependentContent(chatRowData)
//            }.forEach {
//                it.measure(constraints).placeRelative(0, 0)
//            }
//
//        }
//    }
//}


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

        chatRowData.parentWidth = constraints.maxWidth

        val placeables: List<Placeable> = measurables.map { measurable ->
            // Measure each child
            measurable.measure(Constraints(0,constraints.maxWidth))
        }

        val message = placeables.first()
        val status = placeables.last()

        measureChatWidthAndHeight(text, chatRowData, message, status)

        // Send measurement results if requested by Composable
        onMeasured?.invoke(chatRowData)

        layout(width = chatRowData.width, height = chatRowData.height) {
            message.placeRelative(0, 0)
            status.placeRelative(
                chatRowData.width - status.width,
                chatRowData.height - status.height
            )
        }
    }
}

private fun measureChatWidthAndHeight(
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

        println("🌽 STATUS width: ${status.width}, message width: ${message.width}")

        if (lineCount > 1 && lastLineWidth + status.measuredWidth < message.measuredWidth) {
            chatRowData.width = message.measuredWidth
            chatRowData.height = message.measuredHeight
            chatRowData.measuredType = 0
            println(
                "🔥 TEXT: $text, parentWidth: $parentWidth,  " +
                        "lineCount: $lineCount, lastLineWidth: $lastLineWidth, " +
                        "message.width: ${message.width}, status.measuredWidth: ${status.measuredWidth}"
            )
        } else if (lineCount > 1 && lastLineWidth + status.measuredWidth >= parentWidth) {
            chatRowData.width = message.measuredWidth
            chatRowData.height = message.measuredHeight + status.measuredHeight
            chatRowData.measuredType = 1
            println(
                "🤔 TEXT: $text, parentWidth: $parentWidth,  " +
                        "lineCount: $lineCount, lastLineWidth: $lastLineWidth, " +
                        "message.width: ${message.width}, status.measuredWidth: ${status.measuredWidth}"
            )
        } else if (lineCount == 1 && message.width + status.measuredWidth >= parentWidth) {
            chatRowData.width = message.measuredWidth
            chatRowData.height = message.measuredHeight + status.measuredHeight
            chatRowData.measuredType = 2
            println(
                "🎃 TEXT: $text, parentWidth: $parentWidth,  " +
                        "lineCount: $lineCount, lastLineWidth: $lastLineWidth, " +
                        "message.width: ${message.width}, status.measuredWidth: ${status.measuredWidth}"
            )
        } else {
            chatRowData.width = message.measuredWidth + status.measuredWidth
            chatRowData.height = message.measuredHeight
            chatRowData.measuredType = 3
            println(
                "🚀 TEXT: $text, parentWidth: $parentWidth,  " +
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
        modifier = modifier.wrapContentSize().padding(horizontal = 6.dp, vertical = 6.dp),
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



class ChatRowData(
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

