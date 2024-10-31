package com.fifate.tomato_clock.ui.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fifate.tomato_clock.state.PomodoroState
import com.fifate.tomato_clock.ui.base.PomodoroTimerButtons
import com.fifate.tomato_clock.ui.theme.Blue


@Composable
@Preview(showBackground = true, backgroundColor = 0xFF8BBFEE)
fun PreviewPomodoroTimerButtons(){
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Blue
    ){
        var state =  rememberSaveable{ mutableStateOf(PomodoroState.FOCUSING) }
        var isPause = rememberSaveable { mutableStateOf(false) }
        PomodoroTimerButtons(modifier = Modifier, state = state, isPause =isPause )
    }
}