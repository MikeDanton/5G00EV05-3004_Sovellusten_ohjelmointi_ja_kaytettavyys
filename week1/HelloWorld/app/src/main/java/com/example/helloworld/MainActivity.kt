package com.example.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.helloworld.ui.theme.HelloWorldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloWorldTheme(darkTheme = true) {
                MyApp()
            }

        }
    }
}

@Composable
fun MyApp() {
    var buttonText by remember { mutableStateOf("Click to say Hello!") }
    var isHello by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background), // Set background color
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Box for Title
        TopBox()

        // Add space between the top box and center box
        Spacer(modifier = Modifier.height(16.dp))

        // Use Column or Row to wrap MiddleBox and apply weight
        Column(
            modifier = Modifier
                .weight(1f) // Apply weight here to the Column
                .fillMaxWidth()
        ) {
            // Middle Box with Toggle Text
            MiddleBox(buttonText)
        }

        // Add space between the middle box and button
        Spacer(modifier = Modifier.height(16.dp))

        // Button with toggle functionality
        BottomButton {
            isHello = !isHello
            buttonText = if (isHello) "Click to say Hello!" else "Hello, Android!"
        }
    }
}

@Composable
fun TopBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary), RoundedCornerShape(16.dp)) // Apply border first
            .clip(RoundedCornerShape(16.dp)) // Then clip the content inside the border
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "My app",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun MiddleBox(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface) // Use theme surface color
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.primary), // Use theme color
                MaterialTheme.shapes.medium // Use theme shape for border
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun BottomButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary, // Use theme color for the button's background
            contentColor = MaterialTheme.colorScheme.onPrimary // Use theme color for the text
        )
    ) {
        Text("SAY HELLO")
    }
}
