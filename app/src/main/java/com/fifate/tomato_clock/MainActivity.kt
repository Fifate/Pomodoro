package com.fifate.tomato_clock

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.fifate.tomato_clock.state.PomodoroState
import com.fifate.tomato_clock.ui.theme.TomatoClockTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TomatoClockTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    PomodoroTimer()
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("DefaultLocale", "CoroutineCreationDuringComposition", "UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PomodoroTimer() {
    var state:PomodoroState by remember { mutableStateOf(PomodoroState.FOCUSED) }


    // State to store the initial and remaining time in seconds
    val initialFocusTime by remember { mutableIntStateOf(1500) } // 25 minutes in seconds
    val initBreakTime by remember { mutableIntStateOf(5*60) } // 15 minutes in seconds
    var remainingFocusTime by remember { mutableIntStateOf(initialFocusTime) }
    var remainingBreakTime by remember { mutableIntStateOf(initBreakTime) }
    var overFocusTime by remember { mutableIntStateOf(0) }
    var overBreakTime by remember { mutableIntStateOf(0) }
    var isPause by remember { mutableStateOf(false) }


    val timeDisplay = derivedStateOf {
        String.format("%2d:%02d", remainingFocusTime / 60, remainingFocusTime % 60)
    }


    // Coroutine scope
    val coroutineScope = rememberCoroutineScope()

    // Function to start the timer
    @RequiresApi(Build.VERSION_CODES.O)
    fun startFocus() {
        state = PomodoroState.FOCUSING
        isPause = false
        remainingFocusTime = initialFocusTime
    }

    fun focusEnd() {
        state = PomodoroState.FOCUSED
        overFocusTime = 0
    }

    fun startBreak() {
        state = PomodoroState.BREAKING
        remainingBreakTime = initBreakTime
    }

    fun breakEnd() {
        state = PomodoroState.BROKE
        overBreakTime = 0
    }


    fun pauseTimer(){
        isPause = true
    }

    fun continueTimer(){
        isPause = false
    }

    // Function to reset the timer
    fun resetTimer() {
        isPause = false
        remainingFocusTime = initialFocusTime
        remainingBreakTime = initBreakTime
    }

    fun skipBreak(){
        state = PomodoroState.FOCUSING
        remainingFocusTime = initialFocusTime
    }

    fun skipFocus(){
        state = PomodoroState.BREAKING
        remainingBreakTime = initBreakTime
    }

    // Launch a coroutine to update the remaining time
    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
            while(isActive){
                delay(1000)
                if (isPause){
                    continue
                }
                when(state){
                    PomodoroState.INIT -> {}
                    PomodoroState.FOCUSING -> {
                        if (remainingFocusTime > 0) {
                            remainingFocusTime--
                        } else if(remainingFocusTime == 0){
                            focusEnd()
                        }
                    }
                    PomodoroState.FOCUSED -> {
                        overFocusTime++
                    }
                    PomodoroState.BREAKING -> {
                        if (remainingBreakTime > 0) {
                            remainingBreakTime--
                        } else if (remainingBreakTime == 0) {
                            breakEnd()
                        }
                    }
                    PomodoroState.BROKE ->{
                        overBreakTime++
                    }
                }
            }
    }

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
            PomodoroState.INIT -> Button(onClick = { startFocus() }){
                Text(text = "开始专注", fontSize = 24.sp)
            }
            PomodoroState.FOCUSING -> Column(modifier = Modifier.align(Alignment.CenterHorizontally)){

                Text(timeDisplay.value, fontSize = 100.sp,modifier = Modifier.align(Alignment.CenterHorizontally)) //显示倒计时
                Row {
                    if (!isPause){
                        Button(onClick = {pauseTimer()}) { Text(text = "暂停",fontSize=24.sp) }
                        Button(onClick = {skipFocus()}){ Text(text="跳过", fontSize=24.sp) }
                    }else {
                        Button(onClick = {continueTimer()}) { Text(text = "继续") }
                    }
                    Button(onClick = {resetTimer()}){Text(text="重置", fontSize=24.sp)} //重置按钮
                }
            }
            PomodoroState.FOCUSED -> Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(String.format("%2d:%02d", overFocusTime / 60, overFocusTime % 60), fontSize = 20.sp)
                Text(text="专注完成，快活动活动休息一下吧！", fontSize = 20.sp,modifier = Modifier.align(Alignment.CenterHorizontally))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    if (!isPause){
                        Button(onClick = {pauseTimer()}) { Text(text = "暂停",fontSize=12.sp)}
                        Button(onClick = {startBreak()}){ Text(text="开始休息", fontSize=12.sp) }
                    }else {
                        Button(onClick = {continueTimer()}) { Text(text = "继续",fontSize=12.sp) }
                    }
                    Button(onClick = {resetTimer()}){Text(text="重置", fontSize=12.sp)} //重置按钮
                }
            }
            PomodoroState.BREAKING -> Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text="休息中", fontSize = 48.sp)
                Text(String.format("%2d:%02d", remainingBreakTime / 60, remainingBreakTime % 60), fontSize = 48.sp)
                Row {
                    if (!isPause){
                        Button(onClick = {pauseTimer()}) { Text(text = "暂停") }
                        Button(onClick = {skipBreak()}){ Text(text="跳过", fontSize=24.sp) }
                    }else {
                        Button(onClick = {continueTimer()}) { Text(text = "继续") }
                    }
                    Button(onClick = {resetTimer()}){Text(text="重置", fontSize=24.sp)} //重置按钮
                }
            }
            PomodoroState.BROKE -> Column {
                Text(String.format("%2d:%02d", overBreakTime / 60, overBreakTime % 60), fontSize = 24.sp)
                Text(text="休息时间结束，打起精神继续加油！", fontSize = 48.sp)
                if (!isPause){
                    Button(onClick = {pauseTimer()}) { Text(text = "暂停") }
                    Button(onClick = {startFocus()}){ Text(text="开始专注", fontSize=24.sp) }
                }else {
                    Button(onClick = {continueTimer()}) { Text(text = "继续") }
                }
                Button(onClick = {resetTimer()}){Text(text="重置", fontSize=24.sp)} //重置按钮
            }
        }
    }
}
