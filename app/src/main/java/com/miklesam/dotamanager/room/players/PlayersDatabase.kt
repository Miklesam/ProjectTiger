package com.miklesam.dotamanager.room.players

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.miklesam.dotamanager.datamodels.Player
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


@Database(entities = [Player::class], version = 2)

abstract class PlayersDatabase : RoomDatabase() {

    abstract fun noteDao(): PlayersDao

    companion object {
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

        @Volatile
        private var INSTANCE: PlayersDatabase? = null
        //val MIGRATION_1_2 =
        //    Migration1To2()

        fun getInstance(context: Context): PlayersDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        )
                            .also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PlayersDatabase::class.java, "players_main.db"
            )
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        scope.launch {
                            Log.w("In Market", "Insert Populate")
                            getInstance(
                                context
                            )
                                .noteDao().insertData(PlayersList.VP)
                        }
                    }
                })
                //.addMigrations(MIGRATION_1_2)
                .build()


    }

    /*
    class Migration1To2() : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            scope.launch {
                INSTANCE?.noteDao()?.insertData(
                    PlayersList.Nigma
                )
            }

        }
    }

     */


}