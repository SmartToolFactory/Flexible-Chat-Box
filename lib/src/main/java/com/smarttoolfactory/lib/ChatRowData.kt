package com.smarttoolfactory.lib

/**
 * Class that contains info about chat message text, textWidth, if line count,
 * width of last line and other attributes.
 *
 * [textWidth], [text]
 */
data class ChatRowData(
    var text: String = "",
    // Width of the text without padding
    var textWidth: Int = 0,
    var lastLineWidth: Float = 0f ,
    var lineCount: Int = 0,
) {

    var rowWidth: Int = 0
    internal set

    var rowHeight: Int = 0
        internal set

    var parentWidth: Int = 0
        internal set

    var measuredType: Int = 0
        internal set

    override fun toString(): String {
        return "ChatRowData text: $text, " +
                "lastLineWidth: $lastLineWidth, lineCount: $lineCount, " +
                "textWidth: ${textWidth}, rowWidth: $rowWidth, height: $rowHeight, " +
                "parentWidth: $parentWidth, measuredType: $measuredType"
    }
}
