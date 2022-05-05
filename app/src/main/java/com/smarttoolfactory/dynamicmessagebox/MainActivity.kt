@file:OptIn(ExperimentalPagerApi::class)

package com.smarttoolfactory.dynamicmessagebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.smarttoolfactory.dynamicmessagebox.ui.ChatAppbar
import com.smarttoolfactory.dynamicmessagebox.ui.theme.DynamicMessageBoxTheme
import kotlinx.coroutines.launch

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
//                    ResizableColumnImplementation()
//                    DemoChatAndWidth()
//                    DemoFullChat()
                    Column(modifier = Modifier.fillMaxSize()) {
                        HomeContent()
                    }
                }
            }
        }
    }
}


@ExperimentalPagerApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HomeContent() {

    val pagerState: PagerState = rememberPagerState(initialPage = 0)

    val coroutineScope = rememberCoroutineScope()

    if (pagerState.currentPage == 0) {
        ChatAppbar()
    }

    ScrollableTabRow(
        backgroundColor = Color(0xff00897B),
        contentColor = Color.White,
        edgePadding = 8.dp,
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        indicator = {}
    ) {
        // Add tabs for all of our pages
        tabList.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        count = tabList.size
    ) { page: Int ->

        when (page) {

            0 -> DemoFullChat()
            1 -> DemoChatAndWidth()
            else -> DemoResizableColumn()
        }
    }
}

internal val tabList =
    listOf(
        "Full Chat",
        "Row Dimensions",
        "Resizable Column",
    )