package com.example.talestogether.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Paleta pastel compartilhada
val PastelColorScheme: ColorScheme = lightColorScheme(
    primary = Color(0xFFB39DDB),
    onPrimary = Color.White,
    secondary = Color(0xFFB39DDB),
    onSecondary = Color.White,
    background = Color(0xFFFDFDFD),
    onBackground = Color.Black,
    surface = Color(0xFFEDE7F6),
    onSurface = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9575CD),
    onPrimary = Color.White,
    secondary = Color(0xFF9575CD),
    onSecondary = Color.White,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White
)

@Composable
fun TalestogetherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else PastelColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
