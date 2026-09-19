package com.example.dukaansetu.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = NavyPrimary,
    secondary = PrimaryBlue,
    tertiary = AccentBlue,
    background = BgLight,
    surface = CardSurface,
    onPrimary = CardSurface,
    onSecondary = CardSurface,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    outline = BorderLight
)

@Composable
fun DukaanSetuTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
