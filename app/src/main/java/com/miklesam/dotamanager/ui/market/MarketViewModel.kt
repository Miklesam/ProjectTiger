package com.miklesam.dotamanager.ui.market

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.Player

class MarketViewModel(application: Application) :AndroidViewModel(application){

    private var repository: MarketRepository = MarketRepository(application)

    fun getPlayers(): LiveData<List<Player>> {
        return repository.getPlayers()
    }


}