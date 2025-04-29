package jcp.apps.aosd.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jcp.apps.Greeting
import jcp.apps.base.AppToolBar
import jcp.apps.core.DP
import androidx.compose.runtime.*
import jcp.apps.game.FlappyBirdGame
import jcp.apps.game.WhackAMoleGame
import jcp.apps.jptask.MarqueeTextView
import jcp.apps.jptask.MarqueeTextViewExample
import jcp.apps.jptask.MovableFab


//https://play.google.com/store/apps/details?id=com.softin.ledbanner&hl=zh_HK
@Composable
fun MainScreen(modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    var isPlaying by remember { mutableStateOf(true) }
    Box {

        Column {

            Column {


                MarqueeTextViewExample(
                )



            }
            /*    MarqueeTextWithToggle(
                    text = "Welcome to the Marquee Animation in Jetpack Compose! ",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )*/

            Box(modifier = Modifier.height(60.dp))
            Scaffold(modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .background(color = Color.DarkGray)) { _ ->
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.DarkGray)) {
                    AppToolBar()
                    Text(text = "Home Screen")
                }
                // HomeScreen(modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding()))
            }
        }
        MovableFab()
    }

}