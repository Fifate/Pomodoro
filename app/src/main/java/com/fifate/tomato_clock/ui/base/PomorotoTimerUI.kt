package com.fifate.tomato_clock.ui.base

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.fifate.tomato_clock.state.PomodoroState
import com.fifate.tomato_clock.ui.theme.SunOrange

@Composable
fun PauseOrContinue(modifier: Modifier,isPause: MutableState<Boolean>){
    if(isPause.value){
        ContinueButton(modifier = modifier, onClick = {isPause.value = !isPause.value})
    }else{
        PauseButton(modifier = modifier, onClick = {isPause.value = !isPause.value})
    }
}


@Composable
fun PomodoroTimerButtons(
    modifier: Modifier,
    state: MutableState<PomodoroState>,
    isPause: MutableState<Boolean>
){
    Surface(modifier = modifier, color = Color.Transparent) {
        Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
            when(state.value){
                PomodoroState.INIT -> StartButton(modifier = modifier, onClick = {state.value = PomodoroState.FOCUSING})
                PomodoroState.FOCUSING -> {
                    PauseOrContinue(modifier,isPause)
                    NextButton(modifier = modifier, onClick = {state.value = PomodoroState.BREAKING})
                    ResetButton(modifier = modifier, onClick = {state.value = PomodoroState.BROKE})
                    StopButton(modifier = modifier, onClick = {state.value = PomodoroState.INIT})
                }
                PomodoroState.FOCUSED -> {
                    NextButton(modifier = modifier, onClick = {state.value = PomodoroState.BREAKING})
                    ResetButton(modifier = modifier, onClick = {state.value = PomodoroState.FOCUSING})
                    StopButton(modifier = modifier, onClick = {state.value = PomodoroState.INIT})
                }
                PomodoroState.BREAKING -> {
                    PauseOrContinue(modifier,isPause)
                    NextButton(modifier = modifier, onClick = {state.value = PomodoroState.FOCUSING})
                    ResetButton(modifier = modifier, onClick = {state.value = PomodoroState.BROKE})
                    StopButton(modifier = modifier, onClick = {state.value = PomodoroState.INIT})
                }
                PomodoroState.BROKE -> {
                    NextButton(modifier = modifier, onClick = {state.value = PomodoroState.FOCUSED})
                    ResetButton(modifier = modifier, onClick = {state.value = PomodoroState.INIT})
                    StopButton(modifier = modifier, onClick = {state.value = PomodoroState.INIT})
                }
            }
        }
    }

}


@SuppressLint("UnrememberedMutableState", "DefaultLocale")
@Composable
fun PomodoroTimerUI(
    modifier: Modifier,
    isPause:MutableState<Boolean>,
    state: MutableState<PomodoroState>,

    remainingSecs:MutableState<Int>,
) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()){
//        Image(
//            painter = painterResource(id = R.drawable.bamboo),
//            contentDescription = stringResource(id = R.string.bamboo)
//        )
        Box(modifier = Modifier.weight(1f))

        Timer(remainingSecs.value, 260, SunOrange)

        PomodoroTimerButtons(Modifier.weight(0.3f),state,isPause)

        Box(modifier = Modifier.weight(1f))

//        Image(
//            painter = painterResource(id = R.drawable.orchid),
//            contentDescription = stringResource(id = R.string.orchid)
//        )

    }
}
