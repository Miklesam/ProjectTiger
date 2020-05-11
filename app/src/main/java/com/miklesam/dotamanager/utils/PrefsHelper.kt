package com.miklesam.dotamanager.utils

import android.content.Context
import android.content.SharedPreferences

object PrefsHelper {

    private lateinit var prefs: SharedPreferences

    private const val PREFS_NAME = "params"
    const val TEAM_NAME = "team_name"
    const val ENEMY_NAME = "enemy_name"
    const val SHOW_CONTINUE = "continue_show"
    const val CAREER_DAY = "career_day"
    const val CAREER_MONTH = "career_month"
    const val CAREER_YEAR = "career_year"

    const val POSITION_1 = "position_1"
    const val POSITION_2 = "position_2"
    const val POSITION_3 = "position_3"
    const val POSITION_4 = "position_4"
    const val POSITION_5 = "position_5"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun read(key: String, value: String): String? {
        return prefs.getString(key, value)
    }

    fun read(key: String, value: Long): Long? {
        return prefs.getLong(key, value)
    }

    fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            commit()
        }
    }

    fun write(key: String, value: Long) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putLong(key, value)
            commit()
        }
    }
}