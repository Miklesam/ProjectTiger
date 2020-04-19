package com.miklesam.dotamanager.ui.teams

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.Team

class TeamsViewModel(application: Application) : AndroidViewModel(application){
    private var repository: TeamsRepository = TeamsRepository(application)
    fun getTeams(): LiveData<List<Team>> {
        return repository.getTeams()
    }
}