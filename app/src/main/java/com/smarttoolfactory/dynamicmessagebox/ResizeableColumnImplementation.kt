package com.smarttoolfactory.dynamicmessagebox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.dynamicmessagebox.ui.theme.Blue400
import com.smarttoolfactory.dynamicmessagebox.ui.theme.Green400
import com.smarttoolfactory.dynamicmessagebox.ui.theme.Orange400
import com.smarttoolfactory.dynamicmessagebox.ui.theme.Pink400
import com.smarttoolfactory.lib.ResizableColumn

@Composable
fun ResizableColumnImplementation() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        var text1 by remember { mutableStateOf(TextFieldValue("Text1")) }
        var text2 by remember { mutableStateOf(TextFieldValue("Text2")) }
        var text3 by remember { mutableStateOf(TextFieldValue("Text3")) }
        var text4 by remember { mutableStateOf(TextFieldValue("Text4")) }
        var text5 by remember { mutableStateOf(TextFieldValue("Text5")) }

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

        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = text5,
            label = { Text("Text1") },
            placeholder = { Text("Set text to change main width") },
            onValueChange = { newValue: TextFieldValue ->
                text5 = newValue
            }
        )

        ResizableColumn(
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

                Text(
                    text = text5.text,
                    modifier = Modifier.background(Pink400),
                    color = Color.White
                )
            }
        )
    }
}

