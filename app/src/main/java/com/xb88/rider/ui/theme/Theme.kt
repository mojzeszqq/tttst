package com.xb88.rider.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.xb88.rider.domain.model.ThemeMode

val AsixOrangeSun = Color(0xFFFF5A00)
val AsixOrangeNight = Color(0xFFFF6B00)

/** Crisp white background, heavy dark typography — maximum readability in direct sunlight. */
private val SunHighContrastColors = lightColorScheme(
    primary = AsixOrangeSun,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFFFFE0CC),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF111111),
    onSecondary = Color.White,
    background = Color.White,
    onBackground = Color(0xFF0A0A0A),
    surface = Color.White,
    onSurface = Color(0xFF0A0A0A),
    surfaceVariant = Color(0xFFF1F1F1),
    onSurfaceVariant = Color(0xFF222222),
    surfaceContainer = Color(0xFFF4F4F4),
    outline = Color(0xFF333333),
    error = Color(0xFFB00020),
    onError = Color.White,
)

/** Pure black (OLED) background, high-contrast white text — saves battery, no glare at night. */
private val NightEnduroOledColors = darkColorScheme(
    primary = AsixOrangeNight,
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF3A1800),
    onPrimaryContainer = Color.White,
    secondary = Color.White,
    onSecondary = Color.Black,
    background = Color.Black,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF151515),
    onSurfaceVariant = Color(0xFFE6E6E6),
    surfaceContainer = Color(0xFF101010),
    outline = Color(0xFFBBBBBB),
    error = Color(0xFFFF5252),
    onError = Color.Black,
)

private val base = Typography()

/** Heavier weights than Material defaults for readability through a visor or in sunlight. */
private val RiderTypography = Typography(
    displayLarge = base.displayLarge.copy(fontWeight = FontWeight.Black),
    displayMedium = base.displayMedium.copy(fontWeight = FontWeight.Black),
    headlineMedium = base.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
    headlineSmall = base.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
    titleLarge = base.titleLarge.copy(fontWeight = FontWeight.Bold),
    titleMedium = base.titleMedium.copy(fontWeight = FontWeight.Bold),
    bodyLarge = base.bodyLarge.copy(fontWeight = FontWeight.Medium, fontSize = 17.sp),
    bodyMedium = base.bodyMedium.copy(fontWeight = FontWeight.Medium, fontSize = 15.sp),
    labelLarge = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = 0.2.sp),
)

@Composable
fun isNightTheme(mode: ThemeMode): Boolean = when (mode) {
    ThemeMode.SYSTEM -> isSystemInDarkTheme()
    ThemeMode.SUN_HIGH_CONTRAST -> false
    ThemeMode.NIGHT_ENDURO_OLED -> true
}

@Composable
fun XB88Theme(mode: ThemeMode = ThemeMode.SYSTEM, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (isNightTheme(mode)) NightEnduroOledColors else SunHighContrastColors,
        typography = RiderTypography,
        content = content,
    )
}
