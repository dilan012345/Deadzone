package com.dilan.jot

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.dilan.jot.ui.theme.JotTheme

@Composable
fun App(){
    JotTheme {
        Navigator(
            screen = Drawing()
        )

    }
}