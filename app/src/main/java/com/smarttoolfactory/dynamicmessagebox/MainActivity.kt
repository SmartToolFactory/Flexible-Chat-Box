package com.smarttoolfactory.dynamicmessagebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.smarttoolfactory.dynamicmessagebox.ui.theme.DynamicMessageBoxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DynamicMessageBoxTheme {

                val systemUiController = rememberSystemUiController()

                LaunchedEffect(
                    key1 = Unit,
                    block = {
                        systemUiController.setStatusBarColor(
                            color = Color(0xff00897B)
                        )
                    }
                )

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // 🔥 Uncomment only one sample to see each implementation
                    ResizableColumnImplementation()
//                    ChatAndWidthImplementation()
//                    FullChatImplementation()

                }
            }
        }
    }
}
