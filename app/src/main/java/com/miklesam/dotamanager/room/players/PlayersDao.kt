package com.miklesam.dotamanager.room.players

import androidx.lifecycle.LiveData
import androidx.room.*
import com.miklesam.dotamanager.datamodels.Player

@Dao
interface PlayersDao {

    @Insert
    fun insertData(data: List<Player>)

    @Query("SELECT * FROM players_main_table ")
    fun getAllPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM players_main_table WHERE nickname IN(:nickNameList)")
    fun getPlayerByNickName(nickNameList:List<String>): LiveData<List<Player>>

    //@Query("UPDATE players_main_table SET microcontrol=:micro WHERE nickname =:nickk")
    //fun updaatePlayerByNickName(micro: Int,nickk:String)

    @Query("DELETE FROM players_main_table WHERE nickname = :nickName")
    fun deletePlayerByNickName(nickName: String)

    @Update
    fun updatePlayer(player:Player)

    @Delete
    fun deletePlayer(player: Player)

}