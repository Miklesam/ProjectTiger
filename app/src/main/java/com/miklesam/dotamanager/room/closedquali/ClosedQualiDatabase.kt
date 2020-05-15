package com.miklesam.dotamanager.room.closedquali

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.datamodels.TournamentTeam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(entities = [TournamentTeam::class], version = 1)
abstract class ClosedQualiDatabase :RoomDatabase(){
    abstract fun noteDao(): ClosedQualiDao
    companion object {
        @Volatile
        private var INSTANCE: ClosedQualiDatabase? = null
        fun getInstance(context: Context): ClosedQualiDatabase =
            INSTANCE
                ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also { INSTANCE = it }
            }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ClosedQualiDatabase::class.java, "closedquali.db"
            ).build()
    }

}