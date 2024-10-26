package com.fifate.tomato_clock.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class PomodoroState {
    INIT, //初始
    FOCUSING, //专注中
    FOCUSED, //专注后
    BREAKING, //休息中
    BROKE,//休息后
}


@Composable
fun StateControl(
    isPause:  MutableState<Boolean>,
    state: MutableState<PomodoroState>,
    remainingSecs: MutableIntState,
) {
    val initFocusSecs = 25*60;
    val initBreakSecs = 5*60;

    // Coroutine scope
    val coroutineScope = rememberCoroutineScope()
    // Launch a coroutine to update the remaining time
    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            while (isActive) {
                delay(1000)
                if (isPause.value) {
                    continue
                }
                when (state.value) {
                    PomodoroState.INIT -> {remainingSecs.intValue = initFocusSecs}
                    PomodoroState.FOCUSING -> {
                        if (remainingSecs.intValue > 0) {
                            remainingSecs.intValue--
                        } else if (remainingSecs.intValue == 0) {
                            state.value = PomodoroState.FOCUSED
                            remainingSecs.intValue = initBreakSecs
                        }
                    }
                    PomodoroState.FOCUSED -> {
                        remainingSecs.intValue = initBreakSecs
                    }
                    PomodoroState.BREAKING -> {
                        if (remainingSecs.intValue > 0) {
                            remainingSecs.intValue--
                        } else if (remainingSecs.intValue == 0) {
                            state.value = PomodoroState.BROKE
                        }
                    }
                    PomodoroState.BROKE -> {
                        remainingSecs.intValue = initFocusSecs
                    }
                }
            }
        }
    }
}