package com.fifate.tomato_clock.ui.base

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Label
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.fifate.tomato_clock.R
import com.fifate.tomato_clock.ui.theme.White
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp


@SuppressLint("DefaultLocale")
@Composable
fun Timer(remainingSecs:Int, modifier: Modifier){
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.surface) {
        Box(modifier = modifier, contentAlignment = Alignment.Center){
            Image(painter = painterResource(id = R.drawable.circle),
                contentDescription = stringResource(id = R.string.timer_circle),
                contentScale = ContentScale.Fit, // 保持图片的宽高比
                modifier = Modifier
                    .align(Alignment.Center)
                    .scale(3.5F) // 设置图片的缩放比例,
            )
            Text(text = String.format("%02d:%02d", remainingSecs / 60, remainingSecs % 60),
                color= MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 90.sp)
        }
    }
}