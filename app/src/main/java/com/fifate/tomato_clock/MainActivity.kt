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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.fifate.tomato_clock.config.initFocusSecs
import com.fifate.tomato_clock.config.loadConfig
import com.fifate.tomato_clock.state.PomodoroState
import com.fifate.tomato_clock.state.StateControl
import com.fifate.tomato_clock.ui.base.PomodoroTimerUI
import com.fifate.tomato_clock.ui.theme.TomatoClockTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loadConfig()
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
@Composable
fun PomodoroTimer() {
    val state =  rememberSaveable{ mutableStateOf(PomodoroState.INIT) }
    val remainingSecs  =  rememberSaveable{ mutableIntStateOf(initFocusSecs) }
    val isPause = rememberSaveable { mutableStateOf(false) }
    val smallGoalMutableState = rememberSaveable {  mutableStateOf("") }


    StateControl(
        isPause,
        state,
        remainingSecs)

    PomodoroTimerUI(
        isPause,
        state,
        remainingSecs,
        smallGoalMutableState,
    )

}
