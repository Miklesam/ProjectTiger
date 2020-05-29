package com.miklesam.dotamanager.utils

/**
 * Тип состояния кнопки receiptButton HostActivity
 */
enum class TournamentCompetition(val id :String) {
    PRACTICE(id = "0"),
    MAJOR_CLOSED_QUALI(id = "1"),
    MINIR_QUALI(id = "2"),
    MINOR(id = "3"),
    MAJOR(id = "4"),
    THE_INTERNATIONAL(id = "5"),
}
