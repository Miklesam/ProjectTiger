package com.miklesam.dotamanager.ui.market

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.room.players.PlayersDao
import com.miklesam.dotamanager.room.players.PlayersDatabase

class MarketRepository(application: Application) {

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

    fun getPlayerByName(nickNameList: List<String>): LiveData<List<Player>> {
        playerByNick = playersDao.getPlayerByNickName(nickNameList)
        return playerByNick
    }

    fun deletePlayerByNickName(nickName: String) {
        playersDao.deletePlayerByNickName(nickName)
    }

    fun updatePlayers(players: List<Player>) {
        playersDao.updateAllPlayers(players)
    }

    fun dropTable() {
        val query = SimpleSQLiteQuery("DROP TABLE players_main_table")
        playersDao.dropTable(query);
    }
}