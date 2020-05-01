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
    private var threadToClose: Thread?=null
    private lateinit var serverThread: ServerThread
    init {
        Log.w("View", "ViewModel is Init")
        progress.value = 0
        gameArray.value=arrayOf(0,0,0,0,0,0,0,0,0,1)
    }

    fun setPoint(cell:Int,host:Boolean){
        val curr=gameArray.value
        if(host){
            curr?.set(cell, 1)
            curr?.set(9, 2)
        }else{
            curr?.set(cell, 2)
            curr?.set(9, 1)
        }

        gameArray.postValue(curr)
        val sb=StringBuilder()
        curr?.forEach { sb.append("$it.") }
        sendMessage(sb.toString())
    }

    fun resetGame(){
        val curr=arrayOf(0,0,0,0,0,0,0,0,0,1)
        gameArray.postValue(curr)
        val sb=StringBuilder()
        curr.forEach { sb.append("$it.") }
        sendMessage(sb.toString())
    }

    fun startHost() {
        progress.value = 1
        serverThread = ServerThread(this)
        threadToClose = Thread(serverThread)
        threadToClose?.start()
    }

    fun setConnect(){
        progress.value=4
    }

    override fun getInfo(mes: String) {
        if(mes=="Connection Establish"){
            progress.postValue(2)
        }else if(mes=="connwect"){
            sendMessage("Hello there")
        }else if(mes=="reset"){
            resetGame()
        }
        else if(mes=="Disconnect"){
            //resetGame()
        }
        else{
            setPoint(mes.toInt(),false)
        }

    }

    override fun onCleared() {
        super.onCleared()
        if(threadToClose!=null){
            threadToClose!!.interrupt()
            serverThread.serverSocket.close()
        }

        Log.w("View", "ViewModel is Cleared")
    }

    private fun sendMessage(s:String){
        serverThread.sendMessage(s)
    }

}