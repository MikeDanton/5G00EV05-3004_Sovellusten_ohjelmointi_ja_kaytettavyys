// File: com/example/fuel/ui/theme/Theme.kt

package com.example.fuel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF6200EE),          // A shade of purple
    onPrimary = Color(0xFFFFFFFF),        // White for text/icons on primary color
    primaryContainer = Color(0xFFBB86FC), // Light purple for containers
    onPrimaryContainer = Color(0xFF3700B3), // Dark purple for text/icons on primary container
    secondary = Color(0xFF03DAC5),        // A teal color for secondary UI elements
    onSecondary = Color(0xFF000000),      // Black for text/icons on secondary color
    secondaryContainer = Color(0xFF018786), // Darker teal for secondary containers
    onSecondaryContainer = Color(0xFF03DAC5), // Lighter teal for text/icons on secondary container
    background = Color(0xFFFFFFFF),       // White for app background
    onBackground = Color(0xFF000000),     // Black for text/icons on background
    surface = Color(0xFFFFFFFF),          // White for surfaces like cards, sheets
    onSurface = Color(0xFF000000),        // Black for text/icons on surfaces
    error = Color(0xFFB00020),            // Red for error messages
    onError = Color(0xFFFFFFFF),          // White for text/icons on error color
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFBB86FC),          // A lighter purple for dark theme
    onPrimary = Color(0xFF3700B3),        // Dark purple for text/icons on primary color
    primaryContainer = Color(0xFF6200EE), // Dark purple for containers
    onPrimaryContainer = Color(0xFFFFFFFF), // White for text/icons on primary container
    secondary = Color(0xFF03DAC5),        // A lighter teal for secondary UI elements
    onSecondary = Color(0xFF000000),      // Black for text/icons on secondary color
    secondaryContainer = Color(0xFF018786), // Darker teal for secondary containers
    onSecondaryContainer = Color(0xFF03DAC5), // Lighter teal for text/icons on secondary container
    background = Color(0xFF121212),       // Dark gray for app background
    onBackground = Color(0xFFFFFFFF),     // White for text/icons on background
    surface = Color(0xFF121212),          // Dark gray for surfaces like cards, sheets
    onSurface = Color(0xFFFFFFFF),        // White for text/icons on surfaces
    error = Color(0xFFCF6679),            // A lighter red for error messages in dark theme
    onError = Color(0xFF000000),          // Black for text/icons on error color
)

// Optional: Define Typography and Shapes
// If you haven't defined Typography and Shapes, you can use the default ones provided by Material3.

@Composable
fun FuelCostCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Choose the color scheme based on the system theme
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    // Apply MaterialTheme with the chosen color scheme, typography, and shapes
    MaterialTheme(
        colorScheme = colors,
        typography = Typography, // You can customize Typography if needed
        content = content
    )
}
