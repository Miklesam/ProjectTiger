package com.miklesam.dotamanager.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.miklesam.dotamanager.datamodels.Player

@Database(entities = [Player::class], version = 1)

abstract class PlayersDatabase : RoomDatabase() {

    abstract fun noteDao(): PlayersDao

    companion object {

        @Volatile private var INSTANCE: PlayersDatabase? = null

        fun getInstance(context: Context): PlayersDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                PlayersDatabase::class.java, "players.db")
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        ioThread {
                            Log.w("In Market", "Insert Populate")
                            getInstance(context).noteDao().insertData(PREPOPULATE_DATA)
                        }
                    }
                })
                .build()

        val PREPOPULATE_DATA = listOf(Player("Solo", "Alexey Berezin","5","322"), Player("CemaTheSlayer", "Semen Cryvulya","4","655"))
    }
}