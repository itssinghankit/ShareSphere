package com.example.sharesphere.presentation.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.sharesphere.R

enum class DefinedFonts(val fontFamily: FontFamily) {
    latoBlack(FontFamily(Font(R.font.lato_black))),
    latoBold(FontFamily(Font(R.font.lato_bold))),
    latoRegular(FontFamily(Font(R.font.lato_regular))),
    latoLight(FontFamily(Font(R.font.lato_light))),
    latoThin(FontFamily(Font(R.font.lato_thin)))
}