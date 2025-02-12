package com.fifate.tomato_clock.ui.base

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fifate.tomato_clock.R
import com.fifate.tomato_clock.state.PomodoroState


@SuppressLint("DefaultLocale")
@Composable
fun Timer(remainingSecs: MutableState<Int>, size:Int, color: Color, state: MutableState<PomodoroState>){
    Surface(color = MaterialTheme.colorScheme.surface) {
        Box(Modifier.clip(CircleShape).background(color).size(size.dp),contentAlignment = Alignment.Center){
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                StatusIcon(state)
                Text(text = String.format("%02d:%02d", remainingSecs.value / 60, remainingSecs.value % 60),
                    color= MaterialTheme.colorScheme.onPrimary,
                    fontSize = (size/3.2).sp)
                StatusText(state=state)
            }
        }
    }
}

@Composable
fun StatusText(modifier: Modifier=Modifier,state: MutableState<PomodoroState>){
    Surface(color = MaterialTheme.colorScheme.primary) {
        val statusText:String = when(state.value){
            PomodoroState.INIT->"开始专注"
            PomodoroState.FOCUSING->"专注中"
            PomodoroState.FOCUSED -> "开始休息"
            PomodoroState.BREAKING -> "休息中"
            PomodoroState.BROKE -> "开始专注"
        }
        Text(statusText, fontSize = 30.sp)
    }
}

@Composable
fun StatusIcon(state: MutableState<PomodoroState>){
    if(state.value == PomodoroState.INIT
        || state.value == PomodoroState.FOCUSING
        || state.value ==PomodoroState.BROKE){
        Icon(painter = painterResource(id=R.drawable.focusing),"focus")
    }else{
        Icon(painter = painterResource(id=R.drawable.breaking),"break")
    }
}