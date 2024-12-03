package com.fifate.tomato_clock.ui.base

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
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
        Box(modifier = Modifier
            .weight(1f)
            .align(Alignment.Start)){
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Goal(state: MutableState<PomodoroState>, smallGoal: MutableState<String>) {
    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(false) }
    val fz = 28.sp

    Surface(color = MaterialTheme.colorScheme.surface) {
        BasicTextField( // Use BasicTextField directly
            value = smallGoal.value,
            onValueChange = { smallGoal.value = it },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = fz,
                textAlign = TextAlign.Center // Center text within TextField
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            decorationBox = { innerTextField -> // Custom decoration box
                if (!isFocused.value && smallGoal.value.isEmpty()) { // Show placeholder when not focused and empty
                    Text(
                        "定个小目标",
                        fontSize = fz,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center, // Center placeholder text
                        modifier = Modifier.fillMaxWidth() // Make placeholder fill width
                    )
                }
                innerTextField() // Render the actual TextField
            },
            modifier = Modifier
                .onFocusChanged { isFocused.value = it.isFocused }
                .fillMaxWidth() // Make TextField fill width
        )
    }
}