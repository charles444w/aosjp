package jcp.apps.aosd.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.lang.reflect.Modifier

@Composable
fun HomeScreen(modifier: androidx.compose.ui.Modifier) {
    Column(modifier = modifier) {
        Text(text = "Home Screen")
    }
}