package com.miklesam.dotamanager.simplefragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.datamodels.Team
import com.miklesam.dotamanager.room.players.PlayersDatabase
import com.miklesam.dotamanager.room.players.PlayersList
import com.miklesam.dotamanager.room.teams.TeamsDatabase
import com.miklesam.dotamanager.room.teams.TeamsList
import com.miklesam.dotamanager.ui.market.MarketRepository
import com.miklesam.dotamanager.ui.teams.TeamsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class DescriptionViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: TeamsRepository = TeamsRepository(application)
    private var playerRepository: MarketRepository = MarketRepository(application)
    private val generating = MutableLiveData<Boolean>()
    private val listToupdate = MutableLiveData<List<Player>>()
    fun isGenerating(): LiveData<Boolean> = generating

    fun getTeams(): LiveData<List<Team>> {
        return repository.getTeams()
    }

    fun getAllPlayers(): LiveData<List<Player>> {
        return playerRepository.getPlayers()
    }


    fun generateTeams() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                generating.postValue(true)
                val currentPlayers =
                    playerRepository.getPlayers().value
                val players1Positions =
                    currentPlayers?.filter { it.position == "1" }?.toMutableList()
                val players2Positions =
                    currentPlayers?.filter { it.position == "2" }?.toMutableList()
                val players3Positions =
                    currentPlayers?.filter { it.position == "3" }?.toMutableList()
                val players4Positions =
                    currentPlayers?.filter { it.position == "4" }?.toMutableList()
                val players5Positions =
                    currentPlayers?.filter { it.position == "5" }?.toMutableList()

                Log.w("Now Players", currentPlayers?.size.toString())
                while (repository.getTeams().value?.size ?: 0 < 8) {
                    delay(100)
                    Log.w("DelayTeams", (repository.getTeams().value?.size ?: 0).toString())
                }
                val currentTeams = repository.getTeams().value
                Log.w("Now Teams", currentTeams?.size.toString())

                for (i in 0 until (currentTeams?.size ?: 0)) {
                    val randInt = players1Positions?.size?.let { Random.nextInt(it) }
                    currentTeams?.get(i)?.playerPosition1 =
                        randInt?.let { players1Positions[it].nickname }
                    randInt?.let {
                        players1Positions.get(it).currentTeam = currentTeams?.get(i)?.teamName
                    }
                    randInt?.let { players1Positions.removeAt(it) }
                }

                for (i in 0 until (currentTeams?.size ?: 0)) {
                    val randInt = players2Positions?.size?.let { Random.nextInt(it) }
                    currentTeams?.get(i)?.playerPosition2 =
                        randInt?.let { players2Positions[it].nickname }
                    randInt?.let {
                        players2Positions.get(it).currentTeam = currentTeams?.get(i)?.teamName
                    }
                    randInt?.let { players2Positions.removeAt(it) }
                }

                for (i in 0 until (currentTeams?.size ?: 0)) {
                    val randInt = players3Positions?.size?.let { Random.nextInt(it) }
                    currentTeams?.get(i)?.playerPosition3 =
                        randInt?.let { players3Positions[it].nickname }
                    randInt?.let {
                        players3Positions.get(it).currentTeam = currentTeams?.get(i)?.teamName
                    }
                    randInt?.let { players3Positions.removeAt(it) }
                }

                for (i in 0 until (currentTeams?.size ?: 0)) {
                    val randInt = players4Positions?.size?.let { Random.nextInt(it) }
                    currentTeams?.get(i)?.playerPosition4 =
                        randInt?.let { players4Positions[it].nickname }
                    randInt?.let {
                        players4Positions.get(it).currentTeam = currentTeams?.get(i)?.teamName
                    }
                    randInt?.let { players4Positions.removeAt(it) }
                }

                for (i in 0 until (currentTeams?.size ?: 0)) {
                    val randInt = players5Positions?.size?.let { Random.nextInt(it) }
                    currentTeams?.get(i)?.playerPosition5 =
                        randInt?.let { players5Positions[it].nickname }
                    randInt?.let {
                        players5Positions.get(it).currentTeam = currentTeams?.get(i)?.teamName
                    }
                    randInt?.let { players5Positions.removeAt(it) }
                }

                if (currentTeams != null) {
                    repository.updateTeams(currentTeams)
                }

                listToupdate.postValue(currentPlayers)
                //delay(2000)
                generating.postValue(false)
            }
        }
    }

    fun writePlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                listToupdate.value?.let { playerRepository.updatePlayers(it) }
            }
        }
    }

}