package com.miklesam.dotamanager.room.closedquali_playoff

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.miklesam.dotamanager.datamodels.MatchScore
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.datamodels.TournamentTeam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(entities = [MatchScore::class], version = 1)
abstract class ClosedQualiPlayoffDatabase :RoomDatabase(){
    abstract fun noteDao(): ClosedQualiPlayoffDao
    companion object {
        @Volatile
        private var INSTANCE: ClosedQualiPlayoffDatabase? = null
        fun getInstance(context: Context): ClosedQualiPlayoffDatabase =
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
                ClosedQualiPlayoffDatabase::class.java, "closedqualiplayoff.db"
            ).build()
    }

}