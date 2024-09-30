package com.example.fuel

import android.os.Bundle
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale
import com.example.fuel.ui.theme.FuelCostCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FuelCostCalculatorTheme { // Use your custom theme here
                FuelCostCalculatorApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelCostCalculatorApp() {
    val context = LocalContext.current // Get the context here in a composable

    var fuelPrice by rememberSaveable { mutableStateOf("") }
    var consumption by rememberSaveable { mutableStateOf("") }
    var distance by rememberSaveable { mutableStateOf("") }
    var costResult by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Fuel Price Input
                OutlinedTextField(
                    value = fuelPrice,
                    onValueChange = { fuelPrice = it },
                    label = { Text(stringResource(R.string.fuel_price_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Consumption Input
                OutlinedTextField(
                    value = consumption,
                    onValueChange = { consumption = it },
                    label = { Text(stringResource(R.string.consumption_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Distance Input
                OutlinedTextField(
                    value = distance,
                    onValueChange = { distance = it },
                    label = { Text(stringResource(R.string.distance_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Calculate Button
                Button(
                    onClick = {
                        costResult = calculateFuelCost(fuelPrice, consumption, distance, context)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = stringResource(R.string.calculate_cost))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Result Text
                Text(
                    text = stringResource(R.string.fuel_cost_result, costResult),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    )
}

fun calculateFuelCost(
    fuelPrice: String,
    consumption: String,
    distance: String,
    context: Context
): String {
    val normalizedFuelPrice = fuelPrice.replace(',', '.').trim()
    val normalizedConsumption = consumption.replace(',', '.').trim()
    val normalizedDistance = distance.replace(',', '.').trim()

    val price = normalizedFuelPrice.toDoubleOrNull()
    if (price == null) {
        return context.getString(R.string.invalid_fuel_price)
    }

    val cons = normalizedConsumption.toDoubleOrNull()
    if (cons == null) {
        return context.getString(R.string.invalid_consumption)
    }

    val dist = normalizedDistance.toDoubleOrNull()
    if (dist == null) {
        return context.getString(R.string.invalid_distance)
    }

    return try {
        val totalCost = (cons / 100) * dist * price
        String.format(Locale("fi", "FI"), "%.2f", totalCost)
    } catch (e: Exception) {
        context.getString(R.string.calculation_error)
    }
}