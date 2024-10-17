package com.fifate.tomato_clock

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fifate.tomato_clock.ui.theme.TomatoClockTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TomatoClockTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    PomodoroTimer()
                }
            }
        }
    }
}


@SuppressLint("DefaultLocale")
@Preview(showBackground = true)
@Composable
fun PomodoroTimer() {
    // State to store the initial and remaining time in seconds
    var initialTime by remember { mutableStateOf(1500) } // 25 minutes in seconds
    var remainingTime by remember { mutableStateOf(initialTime) }
    // State to track the timer state
    var isRunning by remember { mutableStateOf(false) }

    // Coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // Function to start the timer
    @RequiresApi(Build.VERSION_CODES.O)
    fun startTimer() {
        if (!isRunning && remainingTime > 0) {
            coroutineScope.launch {
                do{
                    kotlinx.coroutines.delay(1000)
                    if(isRunning){
                        remainingTime--
                    }else{
                        break
                    }
                }while (remainingTime > 0)
                // Notify when time is up
                // ...
                isRunning = false
            }
            isRunning = true
        }
    }

    // Function to pause the timer
    fun pauseTimer() {
        isRunning = false
    }

    // Function to reset the timer
    fun resetTimer() {
        isRunning = false
        remainingTime = initialTime
    }

    // UI layout
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = String.format("%d:%02d", remainingTime / 60, remainingTime % 60),
            fontSize = 24.sp
        )

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { resetTimer() }) {
                Text("Reset")
            }

            Button(onClick = { if (isRunning) pauseTimer() else startTimer() }) {
                Text(if (isRunning) "Pause" else "Start")
            }
        }
    }
}
