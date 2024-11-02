package com.fifate.tomato_clock.ui.base

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.fifate.tomato_clock.R


@Composable
fun CustomButton(modifier: Modifier=Modifier, onClick: () -> Unit, id: Int){
    Surface (modifier = modifier, color = Color.Transparent){
        IconButton(onClick = onClick, colors = iconButtonColors(containerColor = Color.Transparent)) {
            Icon(painter = painterResource(id = id), contentDescription = null)
        }
    }
}

@Composable
fun StartButton(modifier: Modifier=Modifier,onClick: () -> Unit) {
    Surface (modifier=modifier){
        CustomButton(modifier,onClick,id = R.drawable.start_icon)
    }
}


@Composable
fun StopButton(modifier: Modifier=Modifier, onClick: () -> Unit) {
    Surface (modifier = modifier){
        CustomButton(modifier,onClick,id = R.drawable.stop_icon)
    }
}


@Composable
fun ContinueButton(modifier:Modifier=Modifier, onClick: () -> Unit) {
    Surface (modifier = modifier){
        CustomButton(modifier,onClick,id = R.drawable.continue_icon)
    }
}


@Composable
fun PauseButton(modifier: Modifier=Modifier, onClick: () -> Unit) {
    Surface (modifier = modifier){
        CustomButton(modifier,onClick,id = R.drawable.pause_icon)
    }
}

@Composable
fun NextButton(modifier: Modifier=Modifier, onClick: () -> Unit) {
    Surface (modifier = modifier){
        CustomButton(modifier,onClick,id = R.drawable.next_icon)
    }
}

@Composable
fun ResetButton(modifier: Modifier=Modifier, onClick: () -> Unit) {
    Surface (modifier = modifier){
        CustomButton(modifier,onClick,id = R.drawable.reset_icon)
    }
}

