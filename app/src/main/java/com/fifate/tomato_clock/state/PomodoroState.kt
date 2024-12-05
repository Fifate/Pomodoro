package com.fifate.tomato_clock.state

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.fifate.tomato_clock.R
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

    val context = LocalContext.current
    val player = remember { MediaPlayer.create(context, R.raw.song_of_hope) }

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
                            player.start()
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
                            player.start()
                        }
                    }
                    PomodoroState.BROKE -> {
                        remainingSecs.value = initFocusSecs
                    }
                }
            }
            player.release()
        }
    }
}