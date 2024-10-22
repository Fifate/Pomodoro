package com.fifate.tomato_clock.ui.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fifate.tomato_clock.state.PomodoroState
import com.fifate.tomato_clock.ui.base.PomodoroTimerButtons
import com.fifate.tomato_clock.ui.base.Timer
import com.fifate.tomato_clock.ui.theme.Blue

@Composable
//@Preview(showBackground = true)
fun PreviewTimer(){
    Surface(
        color = Blue,
    ){
        Timer(remainingSecs = 10,
            modifier = Modifier.fillMaxSize().offset(y = (-70).dp)
        )
    }

}

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