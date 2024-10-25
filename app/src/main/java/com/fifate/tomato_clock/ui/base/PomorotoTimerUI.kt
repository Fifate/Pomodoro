package com.fifate.tomato_clock.ui.base

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.fifate.tomato_clock.state.PomodoroState

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
    isPause:Boolean,
    state: PomodoroState,

    remainingFocusTime:Int,
    remainingBreakTime: Int,
    overFocusTime:Int,
    overBreakTime:Int,

    startFocus: () -> Unit,
    startBreak:()->Unit,
    pauseTimer: () -> Unit,
    continueTimer: () -> Unit,
    resetTimer: () -> Unit,
    skipBreak:()->Unit,
    skipFocus: () -> Unit,

) {

    val timeDisplay = derivedStateOf {
        String.format("%2d:%02d", remainingFocusTime / 60, remainingFocusTime % 60)
    }
    Box(){

    }


//    // UI layout
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        if(isPause){
//            Text("暂停中",fontSize = 24.sp)
//        }
//        when(state){
//            PomodoroState.INIT -> Button(onClick = startFocus){
//                Text(text = "开始专注", fontSize = 24.sp)
//            }
//            PomodoroState.FOCUSING -> Column(modifier = Modifier.align(Alignment.CenterHorizontally)){
//
//                Text(timeDisplay.value, fontSize = 100.sp,modifier = Modifier.align(Alignment.CenterHorizontally)) //显示倒计时
//                Row {
//                    if (!isPause){
//                        Button(onClick = pauseTimer) { Text(text = "暂停",fontSize=24.sp) }
//                        Button(onClick = skipFocus){ Text(text="跳过", fontSize=24.sp) }
//                    }else {
//                        Button(onClick = continueTimer) { Text(text = "继续") }
//                    }
//                    Button(onClick = resetTimer){Text(text="重置", fontSize=24.sp)} //重置按钮
//                }
//            }
//            PomodoroState.FOCUSED -> Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                Text(String.format("%2d:%02d", overFocusTime / 60, overFocusTime % 60), fontSize = 20.sp)
//                Text(text="专注完成，快活动活动休息一下吧！", fontSize = 20.sp,modifier = Modifier.align(Alignment.CenterHorizontally))
//                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                    if (!isPause){
//                        Button(onClick = pauseTimer) { Text(text = "暂停",fontSize=12.sp)}
//                        Button(onClick = startBreak){ Text(text="开始休息", fontSize=12.sp) }
//                    }else {
//                        Button(onClick = continueTimer) { Text(text = "继续",fontSize=12.sp) }
//                    }
//                    Button(onClick = resetTimer){Text(text="重置", fontSize=12.sp)} //重置按钮
//                }
//            }
//            PomodoroState.BREAKING -> Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                Text(text="休息中", fontSize = 48.sp)
//                Text(String.format("%2d:%02d", remainingBreakTime / 60, remainingBreakTime % 60), fontSize = 48.sp)
//                Row {
//                    if (!isPause){
//                        Button(onClick = pauseTimer) { Text(text = "暂停") }
//                        Button(onClick = skipBreak){ Text(text="跳过", fontSize=24.sp) }
//                    }else {
//                        Button(onClick = continueTimer) { Text(text = "继续") }
//                    }
//                    Button(onClick = resetTimer){Text(text="重置", fontSize=24.sp)} //重置按钮
//                }
//            }
//            PomodoroState.BROKE -> Column {
//                Text(String.format("%2d:%02d", overBreakTime / 60, overBreakTime % 60), fontSize = 24.sp)
//                Text(text="休息时间结束，打起精神继续加油！", fontSize = 48.sp)
//                if (!isPause){
//                    Button(onClick = pauseTimer) { Text(text = "暂停") }
//                    Button(onClick = startFocus){ Text(text="开始专注", fontSize=24.sp) }
//                }else {
//                    Button(onClick = continueTimer) { Text(text = "继续") }
//                }
//                Button(onClick = resetTimer){Text(text="重置", fontSize=24.sp)} //重置按钮
//            }
//        }
//    }
}


