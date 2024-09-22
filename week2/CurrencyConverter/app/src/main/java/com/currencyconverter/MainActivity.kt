package com.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.currencyconverter.ui.theme.CurrencyConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CurrencyConverterUI(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverterUI(modifier: Modifier = Modifier) {
    var inputAmount by remember { mutableStateOf("") }
    var convertedAmount by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CurrencySelectionSection(
            modifier = Modifier.weight(0.3f)  // Reduced weight
        )
        Spacer(modifier = Modifier.height(16.dp))

        ConvertedAmountBox(
            convertedAmount = convertedAmount,
            modifier = Modifier.weight(0.2f)  // Reduced weight even more
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputAndConvertButton(
            inputAmount = inputAmount,
            onInputAmountChange = { inputAmount = it },
            onConvertClick = {
                convertedAmount = if (inputAmount.isNotEmpty()) inputAmount else "0"
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)  // Fixed height for input and button section to avoid being squeezed
        )
    }
}

@Composable
fun CurrencySelectionSection(modifier: Modifier = Modifier) {
    val fromCurrency = "USD"  // Placeholder
    val toCurrency = "USD"    // Placeholder

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // From currency dropdown placeholder
        CurrencyDropdown(label = "From", selectedCurrency = fromCurrency)
        Spacer(modifier = Modifier.height(16.dp))
        // To currency dropdown placeholder
        CurrencyDropdown(label = "To", selectedCurrency = toCurrency)
    }
}

@Composable
fun CurrencyDropdown(label: String, selectedCurrency: String) {
    TextField(
        value = selectedCurrency,
        onValueChange = {},
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ConvertedAmountBox(convertedAmount: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()  // Use fillMaxWidth to prevent it from taking too much height
            .clip(RoundedCornerShape(8.dp))
            .border(
                BorderStroke(2.dp, Color.Gray),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Muunnettu summa:",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = convertedAmount,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputAndConvertButton(
    inputAmount: String,
    onInputAmountChange: (String) -> Unit,
    onConvertClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Valuuttamuunnin",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = inputAmount,
            onValueChange = onInputAmountChange,
            label = { Text("Syötä summa") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onConvertClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Muunna")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyConverterPreview() {
    CurrencyConverterTheme {
        CurrencyConverterUI()
    }
}