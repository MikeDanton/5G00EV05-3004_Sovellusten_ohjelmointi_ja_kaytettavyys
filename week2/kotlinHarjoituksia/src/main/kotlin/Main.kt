package org.example

fun printDaysOfWeek() {
    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    for (day in daysOfWeek) {
        println(day)
    }
}

fun addItemToList(shoppingList: MutableList<String>, item: String) {
    shoppingList.add(item)
    println("Added $item. Shopping list: $shoppingList")
}

fun findItemInList(shoppingList: List<String>, item: String) {
    if (shoppingList.contains(item)) {
        println("$item is in the shopping list.")
    } else {
        println("$item is not in the shopping list.")
    }
}

fun removeItemFromList(shoppingList: MutableList<String>, item: String) {
    if (shoppingList.remove(item)) {
        println("Removed $item. Shopping list: $shoppingList")
    } else {
        println("$item was not found in the shopping list.")
    }
}

fun printShoppingList(shoppingList: List<String>) {
    println("Current shopping list: $shoppingList")
}

fun main() {

    val shoppingList = mutableListOf("Milk", "Bread", "Eggs")

    while (true) {
        println("\n--- Shopping List Manager ---")
        println("1: Print shopping list")
        println("2: Add an item")
        println("3: Remove an item")
        println("4: Find an item")
        println("5: Exit")
        print("Choose an option: ")

        val choice = readLine()?.toIntOrNull() ?: 0

        when (choice) {
            1 -> printShoppingList(shoppingList)
            2 -> {
                print("Enter item to add: ")
                val item = readLine() ?: ""
                addItemToList(shoppingList, item)
            }
            3 -> {
                print("Enter item to remove: ")
                val item = readLine() ?: ""
                removeItemFromList(shoppingList, item)
            }
            4 -> {
                print("Enter item to find: ")
                val item = readLine() ?: ""
                findItemInList(shoppingList, item)
            }
            5 -> {
                println("Exiting the shopping list manager.")
                break
            }
            else -> println("Invalid choice, please select a valid option.")
        }
    }
}