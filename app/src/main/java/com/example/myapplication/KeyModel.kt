package com.example.myapplication

import androidx.compose.ui.unit.Dp

data class KeyModel(var id: Int,
                    val whKeySizeHeight: Dp, val whKeySizeWidth: Dp, var songId: Int = 0, var shortSoundId: Int = 0
)
