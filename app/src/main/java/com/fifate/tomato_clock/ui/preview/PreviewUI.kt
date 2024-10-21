package com.fifate.tomato_clock.ui.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fifate.tomato_clock.ui.base.Timer
import com.fifate.tomato_clock.ui.theme.Blue

@Composable
@Preview(showBackground = true)
fun PreviewTimer(){
    Surface(
        color = Blue,
    ){
        Timer(remainingSecs = 10,
            modifier = Modifier.fillMaxSize().offset(y = (-70).dp)
        )
    }

}