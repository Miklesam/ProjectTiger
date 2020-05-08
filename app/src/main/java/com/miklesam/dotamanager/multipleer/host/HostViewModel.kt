package com.miklesam.dotamanager.multipleer.host

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miklesam.dotamanager.datamodels.HeroStats
import com.miklesam.dotamanager.datamodels.Side
import com.miklesam.dotamanager.multipleer.getInfo
import kotlinx.coroutines.*


import java.lang.StringBuilder

class HostViewModel : ViewModel(), getInfo {

    private val toastMessage = MutableLiveData<String>()
    private val progress = MutableLiveData<Int>()
    private val stateGame = MutableLiveData<Int>()
    private val showMoveToLinning = MutableLiveData<Array<Int>>()
    private val gameArray = MutableLiveData<Array<Int>>()
    fun getStateGame(): LiveData<Int> = stateGame
    fun getToastMessage(): LiveData<String> = toastMessage
    fun getMoveLinning(): LiveData<Array<Int>> = showMoveToLinning
    fun getProgress(): LiveData<Int> = progress
    fun getTicTac(): LiveData<Array<Int>> = gameArray
    private var threadToClose: Thread? = null
    private lateinit var serverThread: ServerThread
    private var turnNumber = 0
    private var radiantLaining = MutableLiveData<Array<Int>>()
    private var direLaining = arrayOf(5)
    var radInit = false
    var direInit = false
    private val allPlayersStats = MutableLiveData<List<String>>()
    fun getPlayersMatchStatistic(): LiveData<List<String>> = allPlayersStats
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val radiantTowers = Side(
        arrayListOf(true, true, true),
        arrayListOf(true, true, true),
        arrayListOf(true, true, true)
    )
    val direTowers = Side(
        arrayListOf(true, true, true),
        arrayListOf(true, true, true),
        arrayListOf(true, true, true)
    )
    private val allTowers = MutableLiveData<List<Boolean>>()
    fun getradiantTowers(): LiveData<List<Boolean>> = allTowers

    val RadiantTeam = arrayListOf<HeroStats>(
        HeroStats(0, 0, 0, 1),
        HeroStats(0, 0, 0, 2),
        HeroStats(0, 0, 0, 3),
        HeroStats(0, 0, 0, 4),
        HeroStats(0, 0, 0, 5)
    )
    val DireTeam = arrayListOf<HeroStats>(
        HeroStats(0, 0, 0, 1),
        HeroStats(0, 0, 0, 2),
        HeroStats(0, 0, 0, 3),
        HeroStats(0, 0, 0, 4),
        HeroStats(0, 0, 0, 5)
    )

    init {
        Log.w("View", "ViewModel is Init")
        stateGame.value = 0
        progress.value = 0
        gameArray.value = arrayOf(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0
        )
        showMoveToLinning.value = arrayOf(
            7, 0, 0, 0, 0, 0, 0, 0, 0, 0
        )
        turnNumber = 0
    }

    fun setRadiantLaining(position: Array<Int>) {
        radiantLaining.value = position
        radInit = true
        setLaining()
    }

    fun startPick() {
        val curr = gameArray.value
        curr?.set(22, 1)
        gameArray.postValue(curr)
        val sb = StringBuilder("Pick:")
        curr?.forEach { sb.append("$it.") }
        sendMessage(sb.toString())
    }


    fun setPoint(value: Int, host: Boolean) {
        val curr = gameArray.value
        if (host) {
            curr?.set(turnNumber, value)
            if (turnNumber == 11 || turnNumber == 19) {
                curr?.set(22, 1)
            } else {
                curr?.set(22, 2)
            }
        } else {
            curr?.set(turnNumber, value)
            if (turnNumber == 9 || turnNumber == 13) {
                curr?.set(22, 2)
            } else {
                curr?.set(22, 1)
            }
        }
        turnNumber++
        gameArray.postValue(curr)
        val sb = StringBuilder("Pick:")
        curr?.forEach { sb.append("$it.") }
        sendMessage(sb.toString())
    }

    fun resetGame() {
        val curr = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 1)
        gameArray.postValue(curr)
        val sb = StringBuilder()
        curr.forEach { sb.append("$it.") }
        sendMessage(sb.toString())
    }

    fun startHost() {
        progress.value = 1
        serverThread = ServerThread(this)
        threadToClose = Thread(serverThread)
        threadToClose?.start()
    }

    fun setConnect() {
        progress.value = 4
    }

    override fun getInfo(mes: String) {
        if (mes == "Connection Establish") {
            progress.postValue(2)
        } else if (mes == "connwect") {
            sendMessage("Hello there")
        } else if (mes == "reset") {
            resetGame()
        } else if (mes == "Disconnect") {
            //resetGame()
        } else if (mes.length > 4 && mes.substring(0, 4) == "Lain") {
            val prePayload = mes.split(":")
            val payload = prePayload[1]
            val dots = payload.split(".")

            direLaining = arrayOf(
                dots[0].toInt(), dots[1].toInt(), dots[2].toInt(),
                dots[3].toInt(), dots[4].toInt()
            )
            direInit = true
            setLaining()
        } else {
            setPoint(mes.toInt(), false)
        }

    }

    @Synchronized
    private fun setLaining() {
        if (radInit && direInit) {
            val rada = radiantLaining.value
            val superHero = arrayOf(
                rada!![0],
                rada[1],
                rada[2],
                rada[3],
                rada[4],
                direLaining[0],
                direLaining[1],
                direLaining[2],
                direLaining[3],
                direLaining[4]
            )
            showMoveToLinning.postValue(superHero)
            val sb = StringBuilder("Lain:")
            superHero?.forEach { sb.append("$it.") }
            sendMessage(sb.toString())
            calculateLineAssign(superHero)


        }
    }

    fun calculateLineAssign(position: Array<Int>) {
        val arrayBottomRaddiant = ArrayList<HeroStats>()
        val arrayMidRaddiant = ArrayList<HeroStats>()
        val arrayTopRaddiant = ArrayList<HeroStats>()
        val arrayBottomDire = ArrayList<HeroStats>()
        val arrayMidDire = ArrayList<HeroStats>()
        val arrayTopDire = ArrayList<HeroStats>()
        for (i in 0 until 5) {
            when (position[i]) {
                0 -> arrayTopRaddiant.add(RadiantTeam[i])
                1 -> arrayMidRaddiant.add(RadiantTeam[i])
                2 -> arrayBottomRaddiant.add(RadiantTeam[i])
            }
            when (position[5 + i]) {
                0 -> arrayTopDire.add(DireTeam[i])
                1 -> arrayMidDire.add(DireTeam[i])
                2 -> arrayBottomDire.add(DireTeam[i])
            }


        }

        var radiantTop = 0
        var radiantMid = 0
        var radiantBottom = 0

        var direTop = 0
        var direMid = 0
        var direBottom = 0
        scope.launch {
            delay(3000)
        }

        scope.launch {
            for (i in 0 until 3) {
                delay(1000)
                if (calculateLineKills(arrayMidRaddiant, arrayMidDire)) {
                    radiantMid++
                } else {
                    direMid++
                }
                if (calculateLineKills(arrayTopRaddiant, arrayTopDire)) {
                    radiantTop++
                } else {
                    direTop++
                }
                if (calculateLineKills(arrayBottomRaddiant, arrayBottomDire)) {
                    radiantBottom++
                } else {
                    direBottom++
                }
                sendStats()
            }
            calculateLineTower(
                radiantMid,
                direMid,
                radiantTowers.mid,
                direTowers.mid,
                radiantTowers,
                direTowers
            )
            calculateLineTower(
                radiantTop,
                direTop,
                radiantTowers.top,
                direTowers.top,
                radiantTowers,
                direTowers
            )
            calculateLineTower(
                radiantBottom,
                direBottom,
                radiantTowers.bot,
                direTowers.bot,
                radiantTowers,
                direTowers
            )
            val t = calculateTowers()
            val sb = StringBuilder("Towe:")
            t?.forEach { sb.append("$it.") }
            sendMessage(sb.toString())

            val nextState = stateGame.value?.plus(1)
            radInit = false
            direInit = false
            delay(10000)
            stateGame.postValue(nextState)
            sendMessage("Next:$nextState")
        }
    }

    private fun sendStats() {
        val types = assignStats()
        allPlayersStats.postValue(types)
        val sb = StringBuilder("Stat:")
        types?.forEach { sb.append("$it.") }
        sendMessage(sb.toString())
    }

    private fun calculateLineTower(
        radiant: Int,
        dire: Int,
        radTowers: ArrayList<Boolean>,
        diresTower: ArrayList<Boolean>,
        r: Side,
        d: Side
    ) {
        Log.w("Snos =", "Radiant $radiant  Dire $dire")
        if (radiant > dire) {
            if (diresTower.isNotEmpty()) {
                diresTower.removeAt(diresTower.size - 1)
                d.updateAncient(true)
            } else {
                d.updateAncient(false)
            }

        } else {
            if (radTowers.isNotEmpty()) {
                radTowers.removeAt(radTowers.size - 1)
                r.updateAncient(true)
            } else {
                r.updateAncient(false)
            }

        }
        allTowers.postValue(calculateTowers())
    }

    private fun calculateTowers(): List<Boolean> {
        return listOf(
            radiantTowers.allBuilds[2],
            radiantTowers.allBuilds[1],
            radiantTowers.allBuilds[0],
            radiantTowers.allBuilds[3],
            radiantTowers.allBuilds[4],
            radiantTowers.allBuilds[5],
            radiantTowers.allBuilds[6],
            radiantTowers.allBuilds[7],
            radiantTowers.allBuilds[8],
            radiantTowers.allBuilds[9],
            direTowers.allBuilds[2],
            direTowers.allBuilds[1],
            direTowers.allBuilds[0],
            direTowers.allBuilds[3],
            direTowers.allBuilds[4],
            direTowers.allBuilds[5],
            direTowers.allBuilds[6],
            direTowers.allBuilds[7],
            direTowers.allBuilds[8],
            direTowers.allBuilds[9]
        )
    }

    private fun calculateLineKills(
        radiant: ArrayList<HeroStats>,
        dire: ArrayList<HeroStats>
    ): Boolean {
        var returningVal = false
        if (radiant.isNotEmpty() && dire.isNotEmpty()) {
            val rnds = (0 until (radiant.size + dire.size)).random()
            if (rnds > radiant.size - 1) {
                dire[(rnds - radiant.size)].kills++
                for (i in 0 until dire.size) {
                    if (i != rnds - radiant.size) {
                        dire[i].assist++
                    }
                }
                radiant[(0 until radiant.size).random()].death++
                returningVal = false
            } else {
                radiant[rnds].kills++
                for (i in 0 until radiant.size) {
                    if (i != rnds) {
                        radiant[i].assist++
                    }
                }
                dire[(0 until dire.size).random()].death++
                returningVal = true
            }
        }
        return returningVal
    }

    private fun assignStats(): List<String> {
        val r1 = "${RadiantTeam[0].kills}/${RadiantTeam[0].death}/${RadiantTeam[0].assist}"
        val r2 = "${RadiantTeam[1].kills}/${RadiantTeam[1].death}/${RadiantTeam[1].assist}"
        val r3 = "${RadiantTeam[2].kills}/${RadiantTeam[2].death}/${RadiantTeam[2].assist}"
        val r4 = "${RadiantTeam[3].kills}/${RadiantTeam[3].death}/${RadiantTeam[3].assist}"
        val r5 = "${RadiantTeam[4].kills}/${RadiantTeam[4].death}/${RadiantTeam[4].assist}"

        val d1 = "${DireTeam[0].kills}/${DireTeam[0].death}/${DireTeam[0].assist}"
        val d2 = "${DireTeam[1].kills}/${DireTeam[1].death}/${DireTeam[1].assist}"
        val d3 = "${DireTeam[2].kills}/${DireTeam[2].death}/${DireTeam[2].assist}"
        val d4 = "${DireTeam[3].kills}/${DireTeam[3].death}/${DireTeam[3].assist}"
        val d5 = "${DireTeam[4].kills}/${DireTeam[4].death}/${DireTeam[4].assist}"

        val totalRadiantKills = RadiantTeam.map { it.kills }.sum().toString()
        val totalDireKills = DireTeam.map { it.kills }.sum().toString()
        val types =
            listOf(r1, r2, r3, r4, r5, d1, d2, d3, d4, d5, totalRadiantKills, totalDireKills)
        return types
    }

    override fun onCleared() {
        super.onCleared()
        if (threadToClose != null) {
            threadToClose!!.interrupt()
            serverThread.serverSocket.close()
        }
        Log.w("View", "ViewModel is Cleared")
    }

    private fun sendMessage(s: String) {
        serverThread.sendMessage(s)
    }

}