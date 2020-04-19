package com.miklesam.dotamanager.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.miklesam.dotamanager.datamodels.Team

@Dao
interface TeamsDao {

    @Insert
    fun insertTeams(data: List<Team>)

    @Insert
    fun insertTeam(data: Team)

    @Query("SELECT * FROM teams_table ")
    fun getAllTeams(): LiveData<List<Team>>
}