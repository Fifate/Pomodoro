package com.fifate.tomato_clock.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import com.fifate.tomato_clock.config.initBreakSecs
import com.fifate.tomato_clock.config.initFocusSecs
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
    remainingSecs: MutableState<Int>,
) {
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
                    PomodoroState.INIT -> {remainingSecs.value = initFocusSecs}
                    PomodoroState.FOCUSING -> {
                        if (remainingSecs.value > 0) {
                            remainingSecs.value--
                        } else if (remainingSecs.value == 0) {
                            state.value = PomodoroState.FOCUSED
                            remainingSecs.value = initBreakSecs
                        }
                    }
                    PomodoroState.FOCUSED -> {
                        remainingSecs.value = initBreakSecs
                    }
                    PomodoroState.BREAKING -> {
                        if (remainingSecs.value > 0) {
                            remainingSecs.value--
                        } else if (remainingSecs.value == 0) {
                            state.value = PomodoroState.BROKE
                        }
                    }
                    PomodoroState.BROKE -> {
                        remainingSecs.value = initFocusSecs
                    }
                }
            }
        }
    }
}