package com.example.aciflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.aciflow.ui.screens.HomeScreen
import com.example.aciflow.ui.screens.LoginScreen
import com.example.aciflow.ui.screens.ProfileScreen
import com.example.aciflow.ui.screens.RegisterScreen
import com.example.aciflow.ui.theme.ACIFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ACIFlowTheme(darkTheme = true) {
//                TODO Navigation

                  LoginScreen()
//                RegisterScreen()

//                val navController = rememberNavController()
//                HomeScreen(navController = navController)
//                ProfileScreen(navController = navController)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ACIFlowTheme {
        Greeting("Android")
    }
}