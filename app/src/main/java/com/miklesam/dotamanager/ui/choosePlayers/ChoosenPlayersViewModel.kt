package com.miklesam.dotamanager.ui.choosePlayers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.ui.market.MarketRepository

class ChoosenPlayersViewModel(application: Application) :AndroidViewModel(application){
    private var repository: MarketRepository = MarketRepository(application)

    fun getPlayers(): LiveData<List<Player>> {
        return repository.getPlayers()
    }
}