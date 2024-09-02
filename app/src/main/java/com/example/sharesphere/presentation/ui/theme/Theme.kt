package com.example.sharesphere.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color.White, //
    onPrimary = Black05, //
    primaryContainer = Black15, //
    onPrimaryContainer = Grey96, //
    inversePrimary = Black40, //

    secondary = Black80, //
    onSecondary = Black20, //
    secondaryContainer = Black30, //
    onSecondaryContainer = darkblack90,

    tertiary = Orange80, //violet shades
    onTertiary = Orange20,
    tertiaryContainer = Orange30,
    onTertiaryContainer = Orange90,

    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,

    background = Black05, //black13 and background and onbackground switched
    onBackground = Color.White, //

    surface = Black05,
    onSurface = Black91,

    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = Black05, //
    onSurfaceVariant = Black43,//

    surfaceContainer = Color.Black,

    outline = Black13, //
//    outlineVariant = Black15 //
    outlineVariant = Grey10//Grey79

)

private val LightColorScheme = lightColorScheme(
    primary = Black05, //
    onPrimary = Color.White, //
    primaryContainer = Black90, //
    onPrimaryContainer = Black10, //
    inversePrimary = Black80, //
    secondary = Grey53,//green blue 40
    onSecondary = Color.White,
    secondaryContainer = Black90,  //navigation bar selected
    onSecondaryContainer = Black10, //navigation bar selected icon
    tertiary = Orange50,//40 shade
    onTertiary = Color.White,
    tertiaryContainer = Orange90,
    onTertiaryContainer = Orange10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = Color.White, // main background
    onSurface = BlueGrey31, //main text
    surfaceVariant = Color.White, //navigation bar-surface container- changed
    onSurfaceVariant = Grey65, //supporting text
    surfaceContainer = Color.White, //bottom navigation bar
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    outline = Grey92, //outline
    outlineVariant = Grey86, //divider

)

@Composable
fun ShareSphereTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor =
                if (darkTheme) DarkColorScheme.surface.toArgb() else LightColorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}