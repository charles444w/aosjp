package jcp.apps.aosd.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import jcp.apps.Greeting

@Composable
fun MainScreen(modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    Column {
        Scaffold(modifier = androidx.compose.ui.Modifier.fillMaxSize().background(color = Color.DarkGray)) { _ ->
           Column(modifier = Modifier.fillMaxSize().background(color = Color.DarkGray)) {
                Text(text = "Home Screen")
            }
           // HomeScreen(modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding()))
        }
    }
}