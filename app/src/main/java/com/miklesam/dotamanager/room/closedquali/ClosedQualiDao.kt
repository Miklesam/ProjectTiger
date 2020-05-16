package com.miklesam.dotamanager.room.closedquali

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.miklesam.dotamanager.datamodels.TournamentTeam


@Dao
interface ClosedQualiDao {

    @Insert
    fun insertData(data: List<TournamentTeam>)

    @Query("SELECT * FROM closed_qualifid_table ")
    fun getAllClosedTeams(): LiveData<List<TournamentTeam>>

    @Query("DELETE FROM closed_qualifid_table")
    fun nukeTable()

    @Update
    fun update(closedTeams: List<TournamentTeam>)

}