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
import com.smarttoolfactory.chatflexbox.SubcomposeColumn

@Composable
fun DemoResizableColumn() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Text("Changing width of one composable resizes others to match to have longest width")
        Spacer(modifier = Modifier.height(8.dp))
        SubcomposeColumnSample1()
        SubcomposeColumnSample2()
    }
}

@Composable
private fun SubcomposeColumnSample1() {
    var mainText by remember { mutableStateOf(TextFieldValue("Main Component")) }
    var dependentText by remember { mutableStateOf(TextFieldValue("Dependent Component")) }

    var maxWidthOf by remember { mutableStateOf(0.dp) }

    SubcomposeColumn(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.LightGray)
            .padding(8.dp),
        mainContent = {

            println("🍏 SubcomposeColumn-> MainContent {} composed")

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

//            Column(
//                modifier = Modifier
//                    .background(Orange400)
//                    .padding(4.dp)
//            ) {
//                Text(
//                    text = "Second text",
//                    modifier = Modifier
//                        .background(Blue400),
//                    color = Color.White
//                )
//            }
        }, dependentContent = { size: IntSize ->

            val density = LocalDensity.current.density

            // 🔥 Measure max width of main component in dp  retrieved
            // by subcompose of dependent component from IntSize
            val maxWidth = with(density) {
                size.width / this
            }.dp

            maxWidthOf = maxWidth

            println(
                "🍎 SubcomposeColumn-> DependentContent composed " +
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

@Composable
private fun SubcomposeColumnSample2() {
    var text1 by remember { mutableStateOf(TextFieldValue("Text1 context")) }
    var text2 by remember { mutableStateOf(TextFieldValue("Text2 context")) }
    var text3 by remember { mutableStateOf(TextFieldValue("Text3 context")) }
    var text4 by remember { mutableStateOf(TextFieldValue("Text4 context")) }


    SubcomposeColumn(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.LightGray)
            .padding(8.dp),
        content = {

            Column(
                modifier = Modifier
                    .background(Orange400)
                    .padding(4.dp)
            ) {
                Text(
                    text = text1.text,
                    modifier = Modifier.background(Blue400),
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .background(Pink400)
                    .padding(4.dp)
            ) {

                Text(
                    text = text2.text,
                    modifier = Modifier.background(Green400),
                    color = Color.White
                )
            }


            Column(
                modifier = Modifier
                    .background(Blue400)
                    .padding(4.dp)
            ) {

                Text(
                    text = text3.text,
                    modifier = Modifier.background(Pink400),
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .background(Green400)
                    .padding(4.dp)
            ) {

                Text(
                    text = text4.text,
                    modifier = Modifier.background(Orange400),
                    color = Color.White
                )
            }
        }
    )


    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        value = text1,
        label = { Text("Text1") },
        placeholder = { Text("Set text to change main width") },
        onValueChange = { newValue: TextFieldValue ->
            text1 = newValue
        }
    )

    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        value = text2,
        label = { Text("Text2") },
        placeholder = { Text("Set text to change main width") },
        onValueChange = { newValue: TextFieldValue ->
            text2 = newValue
        }
    )

    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        value = text3,
        label = { Text("Text3") },
        placeholder = { Text("Set text to change main width") },
        onValueChange = { newValue: TextFieldValue ->
            text3 = newValue
        }
    )

    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        value = text4,
        label = { Text("Text4") },
        placeholder = { Text("Set text to change main width") },
        onValueChange = { newValue: TextFieldValue ->
            text4 = newValue
        }
    )
}

