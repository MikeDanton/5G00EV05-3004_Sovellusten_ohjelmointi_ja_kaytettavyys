package com.example.navigointi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigointi.ui.theme.NavigointiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigointiTheme {
                // Set up NavHostController
                val navController = rememberNavController()
                NavApp(navController)
            }
        }
    }
}

@Composable
fun NavApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        // Home Screen
        composable("home") { HomeScreen(navController) }

        // Details Screen
        composable("details") { DetailsScreen(navController) }

        // Settings Screen
        composable("settings") { SettingsScreen(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Screen")
        Spacer(modifier = Modifier.height(16.dp))

        // Button to go to Details
        Button(onClick = { navController.navigate("details") }) {
            Text("Go to Details")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Button to go to Settings
        Button(onClick = { navController.navigate("settings") }) {
            Text("Go to Settings")
        }
    }
}

@Composable
fun DetailsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Details Screen")
        Spacer(modifier = Modifier.height(16.dp))

        // Button to go to Home
        Button(onClick = { navController.navigate("home") }) {
            Text("Go to Home")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Button to go to Settings
        Button(onClick = { navController.navigate("settings") }) {
            Text("Go to Settings")
        }
    }
}

@Composable
fun SettingsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Settings Screen")
        Spacer(modifier = Modifier.height(16.dp))

        // Button to go to Home
        Button(onClick = { navController.navigate("home") }) {
            Text("Go to Home")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Button to go to Details
        Button(onClick = { navController.navigate("details") }) {
            Text("Go to Details")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavigointiTheme {
        val navController = rememberNavController()
        NavApp(navController)
    }
}
