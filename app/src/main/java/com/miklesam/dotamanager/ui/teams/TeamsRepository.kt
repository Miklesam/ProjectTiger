package com.miklesam.dotamanager.ui.teams

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.room.teams.TeamsDao
import com.miklesam.dotamanager.room.teams.TeamsDatabase

class TeamsRepository(application: Application) {
    private var teamsDao: TeamsDao
    private var allTeams: LiveData<List<Team>>

    init {
        val database: TeamsDatabase = TeamsDatabase.getInstance(application.applicationContext)
        teamsDao = database.noteDao()
        allTeams = teamsDao.getAllTeams()
    }

    fun getTeams(): LiveData<List<Team>> {
        return allTeams
    }

    fun getTeamByName(name: String): LiveData<Team> {
        return teamsDao.getTeamByName(name)
    }

    fun getTeamsByName(teamName: List<String>): LiveData<List<Team>> {
        return teamsDao.getTeamsByName(teamName)
    }

    fun updateTeams(teams: List<Team>) {
        return teamsDao.updateTeams(teams)
    }

    fun dropTable() {
        val query = SimpleSQLiteQuery("DROP TABLE teams_table")
        teamsDao.dropTable(query);
    }

}