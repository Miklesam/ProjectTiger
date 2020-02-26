package com.miklesam.dotamanager.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.miklesam.dotamanager.datamodels.Player

@Dao
interface PlayersDao {

    @Insert
    fun insertData(data: List<Player>)

    @Query("SELECT * FROM players_table ")
    fun getAllPlayers(): LiveData<List<Player>>
}