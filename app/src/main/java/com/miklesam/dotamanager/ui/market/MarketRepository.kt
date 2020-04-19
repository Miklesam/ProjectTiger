package com.miklesam.dotamanager.ui.market

import android.app.Application
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.room.PlayersDao
import com.miklesam.dotamanager.room.PlayersDatabase

class MarketRepository(application: Application){

    private var playersDao: PlayersDao
    private var allPlayers: LiveData<List<Player>>
    private lateinit var playerByNick: LiveData<List<Player>>
    init {
        val database: PlayersDatabase = PlayersDatabase.getInstance(application.applicationContext)
        playersDao = database.noteDao()
        allPlayers = playersDao.getAllPlayers()
    }

    fun getPlayers(): LiveData<List<Player>> {
        return allPlayers
    }

    fun getPlayerByName(nickNameList:List<String>): LiveData<List<Player>> {
        playerByNick=playersDao.getPlayerByNickName(nickNameList)
        return playerByNick
    }

    fun deletePlayerByNickName(nickName: String){
        playersDao.deletePlayerByNickName(nickName)
    }

}