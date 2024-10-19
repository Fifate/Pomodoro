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
                    color = MaterialTheme.colorScheme.background
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
    var remainingFocusTime by remember { mutableIntStateOf(initialFocusTime) }
    var remainingBreakTime by remember { mutableIntStateOf(initBreakTime) }
    var overFocusTime by remember { mutableIntStateOf(0) }
    var overBreakTime by remember { mutableIntStateOf(0) }
    var isPause by remember { mutableStateOf(false) }


    StateControl(
        isPause,
        state,
        focusing = {
            if (remainingFocusTime > 0) {
                remainingFocusTime--
            } else if (remainingFocusTime == 0) {
                state.value = PomodoroState.FOCUSED
                overFocusTime = 0
            }
        },
        focused = { overFocusTime++ },
        breaking = {
            if (remainingBreakTime > 0) {
                remainingBreakTime--
            } else if (remainingBreakTime == 0) {
                state.value = PomodoroState.BROKE
                overBreakTime = 0
            }
        },
        broke = { overBreakTime++ }
    )

    PomodoroTimerUI(isPause,
        state.value,
        remainingFocusTime,
        remainingBreakTime,
        overFocusTime,
        overBreakTime,
        startFocus = {
            state.value = PomodoroState.FOCUSING
            isPause = false
            remainingFocusTime = initialFocusTime
        },
        startBreak = {
            state.value = PomodoroState.BREAKING
            remainingBreakTime = initBreakTime
        },
        pauseTimer = {
            isPause = true
        },
        continueTimer = {
            isPause = false
        },
        resetTimer = {
            isPause = false
            remainingFocusTime = initialFocusTime
            remainingBreakTime = initBreakTime
        },
        skipBreak = {
            state.value = PomodoroState.FOCUSING
            remainingFocusTime = initialFocusTime
        },
        skipFocus = {
            state.value = PomodoroState.BREAKING
            remainingBreakTime = initBreakTime
        })

}
