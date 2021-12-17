package com.smarttoolfactory.lib

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize

@Composable
 fun DynamicWidthLayout(
    modifier: Modifier = Modifier,
    mainContent: @Composable () -> Unit,
    dependentContent: @Composable (IntSize) -> Unit
) {

    SubcomposeLayout(modifier = modifier) { constraints ->

        var recompositionIndex = 0

        var mainPlaceables: List<Placeable> = subcompose(SlotsEnum.Main, mainContent).map {
            it.measure(constraints)
        }

        var maxSize =
            mainPlaceables.fold(IntSize.Zero) { currentMax: IntSize, placeable: Placeable ->
                IntSize(
                    width = maxOf(currentMax.width, placeable.width),
                    height = maxOf(currentMax.height, placeable.height)
                )
            }

        val dependentMeasurables: List<Measurable> = subcompose(recompositionIndex++) {
            // 🔥🔥 Send maxSize of mainComponent to
            // dependent composable in case it might be used
            dependentContent(maxSize)
        }

        val dependentPlaceables: List<Placeable> = dependentMeasurables
            .map { measurable: Measurable ->
                measurable.measure(Constraints(maxSize.width, constraints.maxWidth))
            }

        // Get maximum width of dependent composable
        val maxWidth = dependentPlaceables.maxOf { it.width }


        println("✏️ DynamicWidthLayout() maxSize width: ${maxSize.width}, height: ${maxSize.height}")

        // If width of dependent composable is longer than main one, remeasure main one
        // with dependent composable's width using it as minimumWidthConstraint
        if (maxWidth > maxSize.width) {

            println("✏️✏️ DynamicWidthLayout() REMEASURE MAIN COMPONENT maxWidth: $maxWidth, maxSize width: ${maxSize.width}")

            // !!! 🔥🤔 CANNOT use SlotsEnum.Main here why?
            mainPlaceables = subcompose(recompositionIndex++, mainContent).map {
                it.measure(Constraints(maxWidth, constraints.maxWidth))
            }
        }

        // Our final maxSize is longest width and total height of main and dependent composables
        maxSize = IntSize(
            maxSize.width.coerceAtLeast(maxWidth),
            maxSize.height + dependentPlaceables.maxOf { it.height }
        )


        layout(maxSize.width, maxSize.height) {

            println(
                "✏️✏️✏️️ DynamicWidthLayout() layout()-> " +
                        "maxSize width: ${maxSize.width}, height: ${maxSize.height}, " +
                        "maxWidth: $maxWidth"
            )

            // Place layouts
            mainPlaceables.forEach { it.placeRelative(0, 0) }
            dependentPlaceables.forEach {
                it.placeRelative(0, mainPlaceables.maxOf { it.height })
            }
        }
    }
}

enum class SlotsEnum { Main, Dependent, NEW }