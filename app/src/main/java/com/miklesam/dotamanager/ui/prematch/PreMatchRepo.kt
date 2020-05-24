package com.miklesam.dotamanager.ui.prematch

import android.app.Application
import androidx.lifecycle.LiveData
import com.miklesam.dotamanager.datamodels.MatchScore
import com.miklesam.dotamanager.room.closedquali_playoff.ClosedQualiPlayoffDao
import com.miklesam.dotamanager.room.closedquali_playoff.ClosedQualiPlayoffDatabase

class PreMatchRepo(application: Application){
    private var playoffDao: ClosedQualiPlayoffDao
    private var allScore: LiveData<List<MatchScore>>
    init {
        val database: ClosedQualiPlayoffDatabase = ClosedQualiPlayoffDatabase.getInstance(application.applicationContext)
        playoffDao = database.noteDao()
        allScore = playoffDao.getAllScore()
    }
    fun getScore(): LiveData<List<MatchScore>> {
        return allScore
    }
    fun insertScore(score:MatchScore) {
        playoffDao.insertData(score)
    }
}