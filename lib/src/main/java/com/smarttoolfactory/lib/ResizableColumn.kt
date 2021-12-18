package com.smarttoolfactory.lib

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize

/**
 * Column that resizes its children to width of the longest child
 */
@Composable
fun ResizableColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {

    SubcomposeLayout(modifier = modifier) { constraints ->

        var recompositionIndex = 0

        var placeables: List<Placeable> = subcompose(recompositionIndex++, content).map {
            it.measure(constraints)
        }

        val maxSize =
            placeables.fold(IntSize.Zero) { currentMax: IntSize, placeable: Placeable ->
                IntSize(
                    width = maxOf(currentMax.width, placeable.width),
                    height = currentMax.height + placeable.height
                )
            }

        // Remeasure every element using width of longest item as minWidth of Constraint
        if (!placeables.isNullOrEmpty() && placeables.size > 1) {
            placeables = subcompose(recompositionIndex, content).map { measurable: Measurable ->
                measurable.measure(Constraints(maxSize.width, constraints.maxWidth))
            }
        }

        layout(maxSize.width, maxSize.height) {
            var yPos = 0
            placeables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, yPos)
                yPos += placeable.height
            }

        }
    }
}