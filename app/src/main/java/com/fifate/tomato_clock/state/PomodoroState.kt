package com.fifate.tomato_clock.state

enum class PomodoroState {
    INIT, //初始
    FOCUSING, //专注中
    FOCUSED, //专注后
    BREAKING, //休息中
    BROKE,//休息后
}