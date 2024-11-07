package com.fifate.tomato_clock.ui.base

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.fifate.tomato_clock.state.PomodoroState

@Composable
fun Tip(state: MutableState<PomodoroState>){
    Surface(color = MaterialTheme.colorScheme.surface) {
        val tip:String = when(state.value){
            PomodoroState.INIT->"幽兰生前庭，含熏待清风"
            PomodoroState.FOCUSING->"折茎聊可佩，入室自成芳"
            PomodoroState.FOCUSED -> "玉人何处空遗佩，月落沧江壹笛秋"
            PomodoroState.BREAKING -> "清风摇翠环，凉露滴苍玉"
            PomodoroState.BROKE -> "两竿翠竹拂云长，几叶幽兰带露香"
        }
        Text(tip)
    }
}