package jcp.apps

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class App : Application() {
    var navController: NavHostController? = null
    init {
        instance = requireNotNull(this)
    }

    companion object {
        private lateinit var instance: App

        fun applicationContext(): Context {
            return instance
        }
    }

}

@Composable
fun MyApp() {
    val application = LocalContext.current.applicationContext as App

    // Create the NavController if it doesn't exist
    if (application.navController == null) {
        application.navController = rememberNavController()
    }

    application.navController?.let { navController ->
       // MyAppNavHost(navController)
    }
}

@Composable
fun MyAppNavHost(navController: NavHostController) {
/*    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("details") { DetailsScreen(navController) }
    }*/
}

