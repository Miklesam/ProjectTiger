package com.miklesam.dotamanager.ui.teamsprofile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.ui.market.MarketRepository

class TeamsProfileViewModel(application: Application):AndroidViewModel(application){
    private val name = MutableLiveData<String>()
    fun getTeamName(): LiveData<String> = name
    private var repository: MarketRepository = MarketRepository(application)

    fun getPlayer(playerList:List<String>): LiveData<List<Player>> {
        return repository.getPlayerByName(playerList)
    }

    fun setTeamName(teamName:String){
        name.value=teamName
    }
}