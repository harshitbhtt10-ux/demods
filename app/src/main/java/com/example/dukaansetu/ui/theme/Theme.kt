package com.example.dukaansetu.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    secondary = AccentBlue,
    tertiary = GreenSuccess,
    background = NavyDark,
    surface = NavyPrimary,
    onPrimary = CardSurface,
    onBackground = CardSurface,
    onSurface = CardSurface
)

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
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
