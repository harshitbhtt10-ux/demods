package com.example.dukaansetu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dukaansetu.ui.DukaanSetuApp
import com.example.dukaansetu.ui.theme.DukaanSetuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DukaanSetuTheme {
                DukaanSetuApp()
            }
        }
    }
}
