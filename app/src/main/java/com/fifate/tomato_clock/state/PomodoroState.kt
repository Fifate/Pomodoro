package com.fifate.tomato_clock.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    isPause: Boolean,
    state: PomodoroState,
    focusing: () -> Unit,
    focused: () -> Unit,
    breaking: () -> Unit,
    broke: () -> Unit
) {

    // Coroutine scope
    val coroutineScope = rememberCoroutineScope()
    // Launch a coroutine to update the remaining time
    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            while (isActive) {
                delay(1000)
                if (isPause) {
                    continue
                }
                when (state) {
                    PomodoroState.INIT -> {}
                    PomodoroState.FOCUSING -> focusing()
                    PomodoroState.FOCUSED -> focused()
                    PomodoroState.BREAKING -> breaking()
                    PomodoroState.BROKE -> broke()
                }
            }
        }
    }
}