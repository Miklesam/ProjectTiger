package com.miklesam.dotamanager.room.teams

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.miklesam.dotamanager.datamodels.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(entities = [Team::class], version = 2)
abstract class TeamsDatabase : RoomDatabase() {
    abstract fun noteDao(): TeamsDao

    companion object {
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

        @Volatile
        private var INSTANCE: TeamsDatabase? = null

        //val MIGRATION_1_2 =
        //    Migration1To2()
        fun getInstance(context: Context): TeamsDatabase =
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
                TeamsDatabase::class.java, "teams.db"
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        scope.launch {
                            Log.w("In Market", "Insert Populate")
                            getInstance(
                                context
                            ).noteDao().insertTeam(TeamsList.FantasticFive)
                            getInstance(
                                context
                            ).noteDao().insertTeam(TeamsList.FRIENDS)
                            getInstance(
                                context
                            ).noteDao().insertTeam(TeamsList.IcCup)
                            getInstance(
                                context
                            ).noteDao().insertTeam(TeamsList.OldButGold)
                            getInstance(
                                context
                            ).noteDao().insertTeam(TeamsList.PowerRangers)
                            getInstance(
                                context
                            ).noteDao().insertTeam(TeamsList.RoxKis)
                            getInstance(
                                context
                            ).noteDao().insertTeam(TeamsList.SFZ)
                            getInstance(
                                context
                            ).noteDao().insertTeam(TeamsList.VegaSquadron)
                        }
                    }
                })
                //.addMigrations(MIGRATION_1_2)
                .build()
    }

    //class Migration1To2() : Migration(1,2) {
    //    override fun migrate(database: SupportSQLiteDatabase) {
    //        scope.launch {
    //            INSTANCE?.noteDao()?.insertTeam(
    //                TeamsList.Nigma
    //            )
    //        }

    //    }
    //}

}