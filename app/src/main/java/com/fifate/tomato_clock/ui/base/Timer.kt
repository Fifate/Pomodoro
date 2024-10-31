package com.fifate.tomato_clock.ui.base

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("DefaultLocale")
@Composable
fun Timer(remainingSecs:Int, size:Int,color: Color){
    Surface(color = MaterialTheme.colorScheme.surface) {
        Box(Modifier.clip(CircleShape).background(color).size(size.dp),contentAlignment = Alignment.Center){
            Text(text = String.format("%02d:%02d", remainingSecs / 60, remainingSecs % 60),
                color= MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Center),
                fontSize = (size/2.8).sp)
        }
    }
}