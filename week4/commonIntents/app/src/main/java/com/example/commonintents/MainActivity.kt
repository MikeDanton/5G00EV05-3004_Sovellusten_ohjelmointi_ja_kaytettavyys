package com.example.commonintents

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.commonintents.ui.theme.CommonIntentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CommonIntentsTheme {
                // Main UI
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    // Button 1: Open Browser
                    Button(
                        onClick = { openBrowser() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Avaa internetselain")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Button 2: Show Location in Maps
                    Button(
                        onClick = { showLocation() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Näytä sijainti kartalla")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Button 3: Set an Alarm
                    Button(
                        onClick = { setAlarm() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Aseta hälytys")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Button 4: Dial a Phone Number
                    Button(
                        onClick = { dialPhoneNumber() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Soita puhelu")
                    }
                }
            }
        }
    }

    private fun openBrowser() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
        startActivity(intent)
    }

    private fun showLocation() {
        val gmmIntentUri = Uri.parse("geo:60.1699,24.9384?q=Helsinki")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun setAlarm() {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_HOUR, 9)
            putExtra(AlarmClock.EXTRA_MINUTES, 30)
            putExtra(AlarmClock.EXTRA_MESSAGE, "Wake up!")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Alarm feature is not supported on this device", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dialPhoneNumber() {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:1234567890")
        }
        startActivity(intent)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CommonIntentsTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Button layout preview
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Avaa internetselain")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Näytä sijainti kartalla")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Aseta hälytys")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Soita puhelu")
            }
        }
    }
}
