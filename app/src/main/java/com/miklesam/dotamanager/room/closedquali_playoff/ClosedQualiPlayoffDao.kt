package com.miklesam.dotamanager.room.closedquali_playoff

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.miklesam.dotamanager.datamodels.MatchScore
import com.miklesam.dotamanager.datamodels.TournamentTeam


@Dao
interface ClosedQualiPlayoffDao {

    @Insert
    fun insertData(data: MatchScore)

    @Query("SELECT * FROM closed_playoff_table ")
    fun getAllScore(): LiveData<List<MatchScore>>

    @Query("DELETE FROM closed_playoff_table")
    fun nukeTable()

    @Update
    fun update(closedTeams: List<MatchScore>)

}