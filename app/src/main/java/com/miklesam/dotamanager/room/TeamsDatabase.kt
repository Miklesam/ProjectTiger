package com.miklesam.dotamanager.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Team

@Database(entities = [Team::class], version = 1)
abstract class TeamsDatabase :RoomDatabase(){
    abstract fun noteDao(): TeamsDao
    companion object {

        @Volatile
        private var INSTANCE: TeamsDatabase? = null

        fun getInstance(context: Context): TeamsDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TeamsDatabase::class.java, "teams.db"
            )
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            Log.w("In Market", "Insert Populate")
                            getInstance(context).noteDao().insertTeams(PREPOPULATE_TEAMS)
                        }
                    }
                })
                .build()


        val PREPOPULATE_TEAMS = listOf(
            Team("NaVi",
                R.drawable.navi_logo,
                "Navi Description",
                "Crystallize",
                "MagicaL",
                "9pasha",
                "CemaTheSlayer",
                "illias",
                R.drawable.titrof
            ),Team("Gambit",
                R.drawable.gambit_esports,
                "Gambit Description",
                "Dream",
                "gpk",
                "Shachlo",
                "XSvamp1Re",
                "fng",
                R.drawable.titrof
            )


        )

    }
}