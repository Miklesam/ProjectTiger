package com.miklesam.dotamanager.multipleer.host

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miklesam.dotamanager.multipleer.getInfo


import java.lang.StringBuilder

class HostViewModel : ViewModel(), getInfo {

    private val toastMessage = MutableLiveData<String>()
    private val progress = MutableLiveData<Int>()
    private val showMoveToLinning = MutableLiveData<Array<Int>>()
    private val gameArray = MutableLiveData<Array<Int>>()
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

    init {
        Log.w("View", "ViewModel is Init")
        progress.value = 0
        gameArray.value = arrayOf(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0
        )
        showMoveToLinning.value=arrayOf(
            7, 0, 0, 0, 0, 0, 0, 0, 0, 0
        )
        turnNumber = 0
    }

    fun setRadiantLaining(position: Array<Int>) {
        radiantLaining.value = position
        radInit=true
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

                direLaining= arrayOf(
                    dots[0].toInt(), dots[1].toInt(), dots[2].toInt(),
                    dots[3].toInt(), dots[4].toInt()
                )
                direInit=true
                setLaining()
        } else {
            setPoint(mes.toInt(), false)
        }

    }
    @Synchronized
    private fun setLaining() {
        if(radInit&&direInit){
            val rada=radiantLaining.value
            val superHero=arrayOf(rada!![0], rada[1], rada[2], rada[3], rada[4],direLaining[0],direLaining[1],direLaining[2],direLaining[3],direLaining[4])
            showMoveToLinning.postValue(superHero)
            val sb = StringBuilder("Lain:")
            superHero?.forEach { sb.append("$it.") }
            sendMessage(sb.toString())

        }
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