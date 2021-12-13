import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//package com.smarttoolfactory.lib
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.Layout
//import androidx.compose.ui.layout.Measurable
//import androidx.compose.ui.layout.Placeable
//import androidx.compose.ui.unit.Constraints
//
//@Composable
//fun MessageRow(
//    text: String,
//    messageTime: String,
//    messageStatus: MessageStatus
//) {
//    Column(
//        horizontalAlignment = Alignment.End,
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .padding(top = 2.dp, bottom = 2.dp)
////            .background(Color.LightGray)
//    ) {
//        ChatFlexBoxLayout(
//
//            modifier = Modifier
//                .padding(start = 60.dp, end = 8.dp)
//                .shadow(1.dp, RoundedCornerShape(8.dp))
//                .clip(RoundedCornerShape(8.dp))
//                .background(Color(0xffDCF8C6)),
//            text,
//            messageTime,
//            messageStatus
//        )
//    }
//}
//@Composable
//fun ChatFlexBoxLayout(
//    modifier: Modifier = Modifier,
//    text: String,
//    messageTime: String,
//    messageStatus: MessageStatus
//) {
//
//    val textData = remember { ChatRowData() }
//
//    val content = @Composable {
//        Message(
//            modifier = Modifier,
//            text = text,
//            onTextLayout = { textLayoutResult ->
//                textData.lineCount = textLayoutResult.lineCount
//                textData.lastLineWidth = textLayoutResult.getLineRight(textData.lineCount - 1)
//            }
//        )
//
//        MessageTimeText(
//            modifier = Modifier,
//            messageTime = messageTime,
//            messageStatus = messageStatus
//        )
//    }
//
//    Layout(
//        modifier = modifier,
//        content = content
//    ) { measurables: List<Measurable>, constraints: Constraints ->
//
//        if (measurables.size != 2)
//            throw IllegalArgumentException("There should be 2 components for this layout")
//
//        val parentWidth = constraints.maxWidth
//
//        val placeables: List<Placeable> = measurables.map { measurable ->
//            // Measure each child
//            measurable.measure(constraints)
//        }
//
//        val message = placeables.first()
//        val status = placeables.last()
//
//        val lineCount = textData.lineCount
//        val lastLineWidth = textData.lastLineWidth
//
//        val width: Int
//        val height: Int
//
//        if (lineCount > 1 && lastLineWidth + status.measuredWidth < message.measuredWidth) {
//            width = message.measuredWidth
//            height = message.measuredHeight
//        } else if (lineCount > 1 && lastLineWidth + status.measuredWidth >= parentWidth) {
//            width = message.measuredWidth
//            height = message.measuredHeight + status.measuredHeight
//        } else if (lineCount == 1 && message.width + status.measuredWidth >= parentWidth) {
//            width = message.measuredWidth
//            height = message.measuredHeight + status.measuredHeight
//        } else {
//            width = message.measuredWidth + status.measuredWidth
//            height = message.measuredHeight
//        }
//
//        layout(width = width, height = height) {
//            message.placeRelative(0, 0)
//            status.placeRelative(width - status.width, height - status.height)
//        }
//    }
//}