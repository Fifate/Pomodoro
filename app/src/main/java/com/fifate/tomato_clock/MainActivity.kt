package com.fifate.tomato_clock

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fifate.tomato_clock.state.PomodoroState
import com.fifate.tomato_clock.state.StateControl
import com.fifate.tomato_clock.ui.base.PomodoroTimerUI
import com.fifate.tomato_clock.ui.theme.TomatoClockTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TomatoClockTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    PomodoroTimer()
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("DefaultLocale", "CoroutineCreationDuringComposition", "UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PomodoroTimer() {
    var state =  rememberSaveable{ mutableStateOf(PomodoroState.INIT) }


    // State to store the initial and remaining time in seconds
    val initialFocusTime by remember { mutableIntStateOf(1500) } // 25 minutes in seconds
    val initBreakTime by remember { mutableIntStateOf(5 * 60) } // 15 minutes in seconds
    val remainingSecs  =  rememberSaveable{ mutableIntStateOf(initialFocusTime) }
    val isPause = rememberSaveable { mutableStateOf(false) }


    StateControl(
        isPause,
        state,
        remainingSecs)

    PomodoroTimerUI(
        modifier = Modifier.fillMaxSize(),
        isPause,
        state,
        remainingSecs,
    )

}
