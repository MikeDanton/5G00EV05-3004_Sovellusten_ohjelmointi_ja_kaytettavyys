package com.example.lista

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TodoListScreen(viewModel: TodoViewModel, modifier: Modifier = Modifier) {
    val todos = viewModel.todos.collectAsState().value
    LazyColumn(modifier = modifier) {
        items(todos) { todo ->
            TodoItem(todo = todo)
        }
    }
}

@Composable
fun TodoItem(todo: Todo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Checkbox(
            checked = todo.completed,
            onCheckedChange = null, // Checkbox is for display purposes only
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = todo.title)
    }
}
