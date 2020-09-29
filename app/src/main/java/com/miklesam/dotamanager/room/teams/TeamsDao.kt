package com.miklesam.dotamanager.room.teams

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.miklesam.dotamanager.datamodels.Team

@Dao
interface TeamsDao {

    @Insert
    fun insertTeams(data: List<Team>)

    @Insert
    fun insertTeam(data: Team)

    @Query("SELECT * FROM teams_table ")
    fun getAllTeams(): LiveData<List<Team>>

    @Query("SELECT * FROM teams_table WHERE teamName =:teamName")
    fun getTeamByName(teamName:String): LiveData<Team>

    @Query("SELECT * FROM teams_table WHERE teamName IN(:teamName)")
    fun getTeamsByName(teamName:List<String>): LiveData<List<Team>>

    @Update
    fun updateTeams(data: List<Team>)

    @RawQuery
    fun dropTable(query: SupportSQLiteQuery):Int
}