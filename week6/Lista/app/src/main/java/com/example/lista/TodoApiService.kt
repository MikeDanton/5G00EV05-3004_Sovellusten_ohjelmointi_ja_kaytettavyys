package com.example.lista.network

import retrofit2.http.GET
import com.example.lista.Todo

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>
}