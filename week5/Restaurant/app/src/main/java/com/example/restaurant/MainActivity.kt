package com.example.restaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

data class Restaurant(
    val name: String,
    val address: String,
    val rating: Double,
    val cuisine: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantApp()
        }
    }
}

@Composable
fun RestaurantApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "restaurantList") {
        composable("restaurantList") {
            RestaurantListScreen(navController)
        }
        
        composable(
            route = "restaurantDetail/{name}/{address}/{rating}/{cuisine}",
            arguments = listOf(
                navArgument("name") { defaultValue = "" },
                navArgument("address") { defaultValue = "" },
                navArgument("rating") { defaultValue = "0.0" },
                navArgument("cuisine") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val address = backStackEntry.arguments?.getString("address")
            val rating = backStackEntry.arguments?.getString("rating")?.toDoubleOrNull() ?: 0.0
            val cuisine = backStackEntry.arguments?.getString("cuisine")
            RestaurantDetailScreen(name, address, rating, cuisine)
        }
    }
}

@Composable
fun RestaurantListScreen(navController: NavController) {
    // List of restaurants
    val restaurants = listOf(
        Restaurant("The Gourmet Kitchen", "123 Food St.", 4.5, "Italian"),
        Restaurant("Sushi World", "456 Sushi Ave.", 4.8, "Japanese"),
        Restaurant("Taco Paradise", "789 Taco Blvd.", 4.2, "Mexican"),
        Restaurant("Burger Heaven", "321 Burger Ln.", 4.0, "American"),
        Restaurant("Pasta House", "147 Noodle St.", 4.3, "Italian"),
        Restaurant("Spicy Curry Palace", "852 Spice Rd.", 4.7, "Indian"),
        Restaurant("Le Petit Bistro", "963 French St.", 4.6, "French"),
        Restaurant("Wok 'n' Roll", "258 Noodle Ave.", 4.1, "Chinese"),
        Restaurant("Pizza Planet", "159 Slice Blvd.", 3.9, "Italian"),
        Restaurant("The BBQ Shack", "753 Grill Ln.", 4.4, "American"),
        Restaurant("Ramen Kingdom", "357 Ramen St.", 4.9, "Japanese"),
        Restaurant("Café Mocha", "123 Coffee Rd.", 4.0, "Cafe"),
        Restaurant("Viva la Vegan", "456 Green St.", 4.6, "Vegan"),
        Restaurant("El Toro Loco", "789 Fiesta Ave.", 4.3, "Mexican"),
        Restaurant("Dim Sum Delight", "159 Dumpling Ln.", 4.5, "Chinese"),
        Restaurant("The Greek Taverna", "258 Olive St.", 4.7, "Greek"),
        Restaurant("Kebab Palace", "963 Spice St.", 4.3, "Middle Eastern"),
        Restaurant("The Hot Pot Spot", "654 Boil Ave.", 4.2, "Chinese"),
        Restaurant("Falafel Corner", "321 Vegan Blvd.", 4.0, "Middle Eastern"),
        Restaurant("Seaside Sushi", "753 Ocean Ave.", 4.8, "Japanese"),
        Restaurant("The Taco Stand", "987 Fiesta St.", 3.8, "Mexican"),
        Restaurant("Steakhouse Supreme", "654 Meat Ln.", 4.9, "American"),
        Restaurant("Pho Haven", "258 Soup Ave.", 4.4, "Vietnamese"),
        Restaurant("The Sushi Spot", "951 Fish Blvd.", 4.2, "Japanese"),
        Restaurant("The Vegan Joint", "753 Plant Ave.", 4.7, "Vegan")
    )

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val filteredRestaurants = restaurants.filter {
        it.name.contains(searchQuery.text, ignoreCase = true)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search by restaurant name") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(filteredRestaurants) { restaurant ->
                RestaurantListItem(restaurant = restaurant) {
                    // Navigate and pass restaurant details
                    navController.navigate(
                        "restaurantDetail/${restaurant.name}/${restaurant.address}/${restaurant.rating}/${restaurant.cuisine}"
                    )
                }
            }
        }
    }
}

@Composable
fun RestaurantListItem(restaurant: Restaurant, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = restaurant.name, style = MaterialTheme.typography.titleLarge)
            Text(text = restaurant.address, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun RestaurantDetailScreen(name: String?, address: String?, rating: Double, cuisine: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        name?.let {
            Text(text = "Restaurant Details", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Name: $it", style = MaterialTheme.typography.bodyLarge)
        }

        address?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Address: $it", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Rating: $rating", style = MaterialTheme.typography.bodyLarge)

        cuisine?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Cuisine: $it", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantApp()
}