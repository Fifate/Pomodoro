package com.fifate.tomato_clock.ui.base

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.fifate.tomato_clock.R
import com.fifate.tomato_clock.config.initBreakSecs
import com.fifate.tomato_clock.config.initFocusSecs
import com.fifate.tomato_clock.state.PomodoroState
import com.fifate.tomato_clock.ui.theme.SunOrange

@Composable
fun PauseOrContinue(isPause: MutableState<Boolean>){
    if(isPause.value){
        ContinueButton(onClick = {isPause.value = !isPause.value})
    }else{
        PauseButton(onClick = {isPause.value = !isPause.value})
    }
}


@Composable
fun PomodoroTimerButtons(
    state: MutableState<PomodoroState>,
    isPause: MutableState<Boolean>,
    remainingSecs:MutableState<Int>,
){
    Surface(color = Color.Transparent) {
        Row(horizontalArrangement = Arrangement.Center) {
            when(state.value){
                PomodoroState.INIT -> StartButton(onClick = {state.value = PomodoroState.FOCUSING})
                PomodoroState.FOCUSING -> {
                    PauseOrContinue(isPause)
                    NextButton(onClick = {
                        state.value = PomodoroState.BREAKING
                        remainingSecs.value = initBreakSecs
                        })
                    ResetButton(onClick = {
                        state.value = PomodoroState.FOCUSING
                        remainingSecs.value = initFocusSecs
                    })
                    StopButton(onClick = {state.value = PomodoroState.INIT})
                }
                PomodoroState.FOCUSED -> {
                    StartButton(onClick = {
                        state.value = PomodoroState.BREAKING
                        remainingSecs.value = initBreakSecs
                    })
                    ResetButton(onClick = {
                        state.value = PomodoroState.FOCUSING
                        remainingSecs.value = initFocusSecs
                    })
                    StopButton(onClick = {state.value = PomodoroState.INIT})
                }
                PomodoroState.BREAKING -> {
                    PauseOrContinue(isPause)
                    NextButton(onClick = {
                        state.value = PomodoroState.FOCUSING
                        remainingSecs.value = initFocusSecs
                    })
                    ResetButton(onClick = {
                        state.value = PomodoroState.BREAKING
                        remainingSecs.value = initBreakSecs
                    })
                    StopButton(onClick = {state.value = PomodoroState.INIT})
                }
                PomodoroState.BROKE -> {
                    StartButton(onClick = {
                        state.value = PomodoroState.FOCUSED
                        remainingSecs.value = initFocusSecs
                    })
                    ResetButton(onClick = {
                        state.value = PomodoroState.INIT
                        remainingSecs.value = initBreakSecs
                    })
                    StopButton(onClick = {state.value = PomodoroState.INIT})
                }
            }
        }
    }

}


@SuppressLint("UnrememberedMutableState", "DefaultLocale")
@Composable
fun PomodoroTimerUI(
    isPause:MutableState<Boolean>,
    state: MutableState<PomodoroState>,
    remainingSecs: MutableState<Int>,
    smallGoal: MutableState<String>
) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
//        Image(
//            painter = painterResource(id = R.drawable.bamboo),
//            contentDescription = stringResource(id = R.string.bamboo)
//        )
        Box(modifier = Modifier.weight(1f).align(Alignment.Start)){
            Image(painterResource(R.drawable.bamboo), contentDescription = "bamboo")
        }

        Timer(remainingSecs, 260, SunOrange,state)

        Goal(state, smallGoal)

        PomodoroTimerButtons(state,isPause,remainingSecs)

        Box(modifier = Modifier.weight(1f)){
            Image(painterResource(R.drawable.orchid), contentDescription = "bamboo")
        }

//        Image(
//            painter = painterResource(id = R.drawable.orchid),
//            contentDescription = stringResource(id = R.string.orchid)
//        )



    }
}


@Composable
fun Goal(state: MutableState<PomodoroState>, smallGoal: MutableState<String>){
    Surface(color = MaterialTheme.colorScheme.surface) {
        TextField(value=smallGoal.value,
            onValueChange = {smallGoal.value=it},
            placeholder = { Text("定个小目标吧") },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp))
    }
}
