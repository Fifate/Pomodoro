package com.fifate.tomato_clock.ui.base

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.fifate.tomato_clock.state.PomodoroState

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
    // UI layout
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(isPause){
            Text("暂停中",fontSize = 24.sp)
        }
        when(state){
            PomodoroState.INIT -> Button(onClick = startFocus){
                Text(text = "开始专注", fontSize = 24.sp)
            }
            PomodoroState.FOCUSING -> Column(modifier = Modifier.align(Alignment.CenterHorizontally)){

                Text(timeDisplay.value, fontSize = 100.sp,modifier = Modifier.align(Alignment.CenterHorizontally)) //显示倒计时
                Row {
                    if (!isPause){
                        Button(onClick = pauseTimer) { Text(text = "暂停",fontSize=24.sp) }
                        Button(onClick = skipFocus){ Text(text="跳过", fontSize=24.sp) }
                    }else {
                        Button(onClick = continueTimer) { Text(text = "继续") }
                    }
                    Button(onClick = resetTimer){Text(text="重置", fontSize=24.sp)} //重置按钮
                }
            }
            PomodoroState.FOCUSED -> Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(String.format("%2d:%02d", overFocusTime / 60, overFocusTime % 60), fontSize = 20.sp)
                Text(text="专注完成，快活动活动休息一下吧！", fontSize = 20.sp,modifier = Modifier.align(Alignment.CenterHorizontally))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    if (!isPause){
                        Button(onClick = pauseTimer) { Text(text = "暂停",fontSize=12.sp)}
                        Button(onClick = startBreak){ Text(text="开始休息", fontSize=12.sp) }
                    }else {
                        Button(onClick = continueTimer) { Text(text = "继续",fontSize=12.sp) }
                    }
                    Button(onClick = resetTimer){Text(text="重置", fontSize=12.sp)} //重置按钮
                }
            }
            PomodoroState.BREAKING -> Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text="休息中", fontSize = 48.sp)
                Text(String.format("%2d:%02d", remainingBreakTime / 60, remainingBreakTime % 60), fontSize = 48.sp)
                Row {
                    if (!isPause){
                        Button(onClick = pauseTimer) { Text(text = "暂停") }
                        Button(onClick = skipBreak){ Text(text="跳过", fontSize=24.sp) }
                    }else {
                        Button(onClick = continueTimer) { Text(text = "继续") }
                    }
                    Button(onClick = resetTimer){Text(text="重置", fontSize=24.sp)} //重置按钮
                }
            }
            PomodoroState.BROKE -> Column {
                Text(String.format("%2d:%02d", overBreakTime / 60, overBreakTime % 60), fontSize = 24.sp)
                Text(text="休息时间结束，打起精神继续加油！", fontSize = 48.sp)
                if (!isPause){
                    Button(onClick = pauseTimer) { Text(text = "暂停") }
                    Button(onClick = startFocus){ Text(text="开始专注", fontSize=24.sp) }
                }else {
                    Button(onClick = continueTimer) { Text(text = "继续") }
                }
                Button(onClick = resetTimer){Text(text="重置", fontSize=24.sp)} //重置按钮
            }
        }
    }

}