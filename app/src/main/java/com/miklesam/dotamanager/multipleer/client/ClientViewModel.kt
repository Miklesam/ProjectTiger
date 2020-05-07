package com.miklesam.dotamanager.multipleer.client

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miklesam.dotamanager.multipleer.getInfo
import java.lang.StringBuilder

class ClientViewModel : ViewModel(), getInfo {
    lateinit var clientThread: ClientThread
    var threadToClose: Thread? = null
    private val toastMessage = MutableLiveData<String>()
    fun getToastMessage(): LiveData<String> = toastMessage
    private val progress = MutableLiveData<Int>()
    fun getProgress(): LiveData<Int> = progress
    private val gameArray = MutableLiveData<Array<Int>>()
    fun getTicTac(): LiveData<Array<Int>> = gameArray
    private val showMoveToLinning = MutableLiveData<Array<Int>>()
    fun getMoveLinning(): LiveData<Array<Int>> = showMoveToLinning
    private val allPlayersStats = MutableLiveData<List<String>>()
    fun getPlayersMatchStatistic(): LiveData<List<String>> = allPlayersStats
    private val stateGame = MutableLiveData<Int>()
    fun getStateGame(): LiveData<Int> = stateGame
    var myString = ""

    init {
        stateGame.value=0
        progress.value = 0
        gameArray.value = arrayOf(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0
        )
        showMoveToLinning.value = arrayOf(
            7, 0, 0, 0, 0, 0, 0, 0, 0, 0
        )
    }


    fun setPoint(cell: Int) {
        clientThread.sendMessage(cell.toString())
    }

    fun resetBttn() {
        clientThread.sendMessage("reset")
    }

    fun connectHost(ip: String) {
        progress.value = 1
        clientThread = ClientThread(this, ip)
        threadToClose = Thread(clientThread)
        threadToClose?.start()
    }

    fun setConnect() {
        progress.value = 4
    }

    fun setDireLaining(position: Array<Int>) {
        val sb = StringBuilder("Lain:")
        position.forEach { sb.append("$it.") }
        clientThread.sendMessage(sb.toString())
    }

    override fun getInfo(mes: String) {
        myString = mes
        if (mes == "Error") {
            progress.postValue(3)
        } else if (mes == "Hello there") {
            progress.postValue(2)
            clientThread.sendMessage("Connection Establish")
        } else if (mes.substring(0, 4) == "Pick") {
            Log.w("ClientModel Pick", mes)
            val prePayload = mes.split(":")
            val payload = prePayload[1]
            val dots = payload.split(".")
            val pickArray = arrayOf(
                dots[0].toInt(), dots[1].toInt(), dots[2].toInt(),
                dots[3].toInt(), dots[4].toInt(), dots[5].toInt(), dots[6].toInt(),
                dots[7].toInt(), dots[8].toInt(), dots[9].toInt(),
                dots[10].toInt(), dots[11].toInt(), dots[12].toInt(),
                dots[13].toInt(), dots[14].toInt(), dots[15].toInt(),
                dots[16].toInt(), dots[17].toInt(), dots[18].toInt(),
                dots[19].toInt(), dots[20].toInt(), dots[21].toInt(),
                dots[22].toInt()
            )
            gameArray.postValue(
                pickArray
            )
        } else if (mes.substring(0, 4) == "Lain") {
            val prePayload = mes.split(":")
            val payload = prePayload[1]
            val dots = payload.split(".")
            val heroArray = arrayOf(
                dots[0].toInt(), dots[1].toInt(), dots[2].toInt(),
                dots[3].toInt(), dots[4].toInt(), dots[5].toInt(), dots[6].toInt(),
                dots[7].toInt(), dots[8].toInt(), dots[9].toInt()
            )
            showMoveToLinning.postValue(
                heroArray
            )
        } else if (mes.substring(0, 4) == "Stat") {
            val prePayload = mes.split(":")
            val payload = prePayload[1]
            val dots = payload.split(".")
            val statArray = listOf(
                dots[0], dots[1], dots[2],
                dots[3], dots[4], dots[5], dots[6],
                dots[7], dots[8], dots[9], dots[10], dots[11]
            )
            Log.w("StatArray",statArray.toString())
            allPlayersStats.postValue(statArray)
        }else if (mes.substring(0, 4) == "Next") {
            Log.w("ClientModel State", mes)
            val prePayload = mes.split(":")
            val payload = prePayload[1].toInt()
            Log.w("ClientModel Statepay", payload.toString())
            stateGame.postValue(payload)
            Log.w("ClientModel Stateval", stateGame.value.toString())
        }
        else {

        }


    }

    override fun onCleared() {
        super.onCleared()
        if (threadToClose != null) {
            threadToClose!!.interrupt()
            clientThread.sendMessage("Disconnect")
        }
        Log.w("Client", "ViewOnCleared")
    }

}