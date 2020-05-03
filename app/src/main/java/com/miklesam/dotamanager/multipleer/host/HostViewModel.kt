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
    private val gameArray = MutableLiveData<Array<Int>>()
    fun getToastMessage(): LiveData<String> = toastMessage
    fun getProgress(): LiveData<Int> = progress
    fun getTicTac(): LiveData<Array<Int>> = gameArray
    private var threadToClose: Thread? = null
    private lateinit var serverThread: ServerThread
    private var turnNumber = 0

    init {
        Log.w("View", "ViewModel is Init")
        progress.value = 0
        gameArray.value = arrayOf(
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0
        )
        turnNumber = 0
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
             if(turnNumber==11||turnNumber==19){
                curr?.set(22, 1)
            }else{
                curr?.set(22, 2)
            }
        } else {
            curr?.set(turnNumber, value)
            if(turnNumber==9||turnNumber==13){
                curr?.set(22, 2)
            }else{
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
        } else {
            setPoint(mes.toInt(), false)
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