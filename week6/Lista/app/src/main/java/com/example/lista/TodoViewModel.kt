package com.example.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.lista.network.NetworkModule

class TodoViewModel : ViewModel() {

    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos

    fun fetchTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val todoList = NetworkModule.todoApiService.getTodos()
                _todos.value = todoList
            } catch (e: Exception) {
                // Handle error (e.g., logging or showing a message)
            }
        }
    }
}
