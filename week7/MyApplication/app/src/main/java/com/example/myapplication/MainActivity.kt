package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.livedata.observeAsState

class CounterViewModel : ViewModel() {
    // Private MutableLiveData to keep the counter value
    private val _counter = MutableLiveData(0)
    // Public LiveData to expose the counter value to observers
    val counter: LiveData<Int> get() = _counter

    // Method to increase the counter
    fun increase() {
        _counter.value = (_counter.value ?: 0) + 1
    }

    // Method to decrease the counter
    fun decrease() {
        _counter.value = (_counter.value ?: 0) - 1
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CounterScreen()
            }
        }
    }
}

@Composable
fun CounterScreen(counterViewModel: CounterViewModel = viewModel()) {
    val counter by counterViewModel.counter.observeAsState(0)

    // Layout for the counter UI
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Counter: $counter",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = { counterViewModel.increase() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Kasvata")
            }

            Button(
                onClick = { counterViewModel.decrease() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Pienennä")
            }
        }
    }
}