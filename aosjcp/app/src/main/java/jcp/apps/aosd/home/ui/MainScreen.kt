package jcp.apps.aosd.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import jcp.apps.base.AppToolBar
import jcp.apps.core.DP
import jcp.apps.datastore.PreferencesManager
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    val context = LocalContext.current
    val preferencesManager: PreferencesManager = PreferencesManager(context)
    val scope = rememberCoroutineScope()

    val usernameKey = stringPreferencesKey("username")
    val usernameFlow = preferencesManager.getPreference(usernameKey, "Guest")

    // Collect DataStore Flow as State
    val username by usernameFlow.collectAsState(initial = "Guest")
    Column {
        Box(modifier = Modifier.height(60.dp))
        Scaffold(modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)) { _ ->
           Column(modifier = Modifier
               .fillMaxSize()
               .background(color = Color.DarkGray)) {
               AppToolBar()
                Text(text = "Home Screen")
               Text(text = "Hello, $username!")
               Button(onClick = {
                   scope.launch {
                       preferencesManager.savePreference(usernameKey, "ComposeUser")
                   }
               }) {
                   Text("Update Username")
               }
            }
           // HomeScreen(modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding(), bottom = innerPadding.calculateBottomPadding()))
        }
    }
}