package com.miklesam.dotamanager.multipleer.client

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miklesam.dotamanager.multipleer.getInfo

class ClientViewModel : ViewModel(), getInfo {
    lateinit var clientThread: ClientThread
    var threadToClose: Thread? = null
    private val toastMessage = MutableLiveData<String>()
    fun getToastMessage(): LiveData<String> = toastMessage
    private val progress = MutableLiveData<Int>()
    fun getProgress(): LiveData<Int> = progress
    private val gameArray = MutableLiveData<Array<Int>>()
    fun getTicTac(): LiveData<Array<Int>> = gameArray
    var myString = ""

    init {
        progress.value = 0
        gameArray.value = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 1)
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
    fun setConnect(){
        progress.value=4
    }

    override fun getInfo(mes: String) {
        myString = mes
        if (mes == "Error") {
            progress.postValue(3)
        } else if (mes == "Hello there") {
            progress.postValue(2)
            clientThread.sendMessage("Connection Establish")
        }else if(mes.substring(0,4) == "Pick"){
            Log.w("ClientModel Pick", mes)
           val turn=mes.substring(5,6).toInt()
            gameArray.value=arrayOf(turn, 0, 0, 0, 0, 0, 0, 0, 0, 1)
            val dots = mes.split(".")
            gameArray.value
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