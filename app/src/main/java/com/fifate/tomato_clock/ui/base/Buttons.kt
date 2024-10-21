package com.fifate.tomato_clock.ui.base

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import com.fifate.tomato_clock.R


@Composable
fun CustomButton(modifier: Modifier, onClick: () -> Unit, id: Int){
    Surface (modifier = modifier, color = MaterialTheme.colorScheme.onSurface){
        Button(onClick = onClick) {
            Image(painter = painterResource(id = id), contentDescription = null)
        }
    }
}

@Composable
fun StartButton(modifier: Modifier,onClick: () -> Unit) {
    Surface (modifier=modifier, color = MaterialTheme.colorScheme.onSurface){
        CustomButton(modifier,onClick,id = R.drawable.start_icon)
    }
}


@Composable
fun StopButton(modifier: Modifier, onClick: () -> Unit) {
    Surface (modifier = modifier, color = MaterialTheme.colorScheme.onSurface){
        CustomButton(modifier,onClick,id = R.drawable.stop_icon)
    }
}


@Composable
fun ContinueButton(modifier: Modifier, onClick: () -> Unit) {
    Surface (modifier = modifier, color = MaterialTheme.colorScheme.onSurface){
        CustomButton(modifier,onClick,id = R.drawable.continue_icon)
    }
}


@Composable
fun PauseButton(modifier: Modifier, onClick: () -> Unit) {
    Surface (modifier = modifier, color = MaterialTheme.colorScheme.onSurface){
        CustomButton(modifier,onClick,id = R.drawable.pause_icon)
    }
}

@Composable
fun SkipButton(modifier: Modifier, onClick: () -> Unit) {
    Surface (modifier = modifier, color = MaterialTheme.colorScheme.onSurface){
        CustomButton(modifier,onClick,id = R.drawable.skip_icon)
    }
}

@Composable
fun ResetButton(modifier: Modifier, onClick: () -> Unit) {
    Surface (modifier = modifier, color = MaterialTheme.colorScheme.onSurface){
        CustomButton(modifier,onClick,id = R.drawable.reset_icon)
    }
}

