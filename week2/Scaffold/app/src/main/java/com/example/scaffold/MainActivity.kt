package com.example.scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.scaffold.ui.theme.ScaffoldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScaffoldTheme {
                MyScaffoldUI()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffoldUI() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ylävalikko") }
            )
        },
        bottomBar = {
            BottomAppBar {
                Text("Alavalikko")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text("Keskellä näyttöä oleva teksti")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /*TODO: Lisää toiminto */ }) {
                Text("Nappi")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyScaffoldPreview() {
    ScaffoldTheme {
        MyScaffoldUI()
    }
}
