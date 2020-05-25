package com.miklesam.dotamanager.utils

/**
 * Тип состояния кнопки receiptButton HostActivity
 */
enum class PlayOffState(val id :Int) {
    SEMI_FINALS(id = 0),
    UPPER_BRACKET_FINAL(id = 1),
    LOWER_BRACKER_R1(id = 2),
    LOWER_BRACKET_FINAL(id = 3),
    QUALIFIED(id = 4)
}
