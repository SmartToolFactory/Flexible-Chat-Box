package com.smarttoolfactory.dynamicmessagebox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.dynamicmessagebox.ui.theme.Blue400
import com.smarttoolfactory.dynamicmessagebox.ui.theme.Green400
import com.smarttoolfactory.dynamicmessagebox.ui.theme.Orange400
import com.smarttoolfactory.dynamicmessagebox.ui.theme.Pink400
import com.smarttoolfactory.lib.DynamicWidthLayout

@Composable
fun DynamicWidthImplementation() {

    val density = LocalDensity.current.density

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        var mainText by remember { mutableStateOf(TextFieldValue("Main Component")) }
        var dependentText by remember { mutableStateOf(TextFieldValue("Dependent Component")) }

        var maxWidthOf by remember { mutableStateOf(0.dp) }

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = mainText,
            label = { Text("Main") },
            placeholder = { Text("Set text to change main width") },
            onValueChange = { newValue: TextFieldValue ->
                mainText = newValue
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = dependentText,
            label = { Text("Dependent") },
            placeholder = { Text("Set text to change dependent width") },
            onValueChange = { newValue ->
                dependentText = newValue
            }
        )

        DynamicWidthLayout(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.LightGray)
                .padding(8.dp),
            mainContent = {

                println("🍏 DynamicWidthLayout-> MainContent {} composed")

                Column(
                    modifier = Modifier
                        .background(Orange400)
                        .padding(4.dp)
                ) {
                    Text(
                        text = mainText.text,
                        modifier = Modifier
                            .background(Blue400)
                            .height(40.dp),
                        color = Color.White
                    )
                }

                Column(
                    modifier = Modifier
                        .background(Orange400)
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Second text",
                        modifier = Modifier
                            .background(Blue400),
                        color = Color.White
                    )
                }
            }

            , dependentContent = { size: IntSize ->

                // 🔥 Measure max width of main component in dp  retrieved
                // by subcompose of dependent component from IntSize
                val maxWidth = with(density) {
                    size.width / this
                }.dp

                maxWidthOf = maxWidth

                println(
                    "🍎 DynamicWidthLayout-> DependentContent composed " +
                            "Dependent size: $size, "
                            + "maxWidth: $maxWidth"
                )

                Column(
                    modifier = Modifier
                        .background(Pink400)
                        .padding(4.dp)
                ) {

                    Text(
                        text = dependentText.text,
                        modifier = Modifier
                            .background(Green400),
                        color = Color.White
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Width of main component",
            modifier = Modifier
                .padding(12.dp)
        )
        Text(
            text = "Max width from subCompose(): $maxWidthOf",
            modifier = Modifier
                .padding(12.dp)
                .width(maxWidthOf)
                .background(Color(0xff8D6E63)),
            color = Color.White
        )
    }
}
