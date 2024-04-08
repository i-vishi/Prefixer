package dev.vishalgaur.prefixerapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.vishalgaur.prefixerapp.R

val Roboto = FontFamily(
    Font(R.font.roboto, FontWeight.Black),
)
val RobotoBlack = FontFamily(
    Font(R.font.roboto_black, FontWeight.Black),
)
val RobotoThin = FontFamily(
    Font(R.font.roboto_thin, FontWeight.Black),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Grey100,
    ),
    headlineLarge = TextStyle(
        fontFamily = RobotoBlack,
        fontSize = 96.sp,
        color = Grey100,
        lineHeight = 112.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = RobotoThin,
        fontSize = 36.sp,
        color = AquaBlue,
        lineHeight = 52.sp,
    ),
)
