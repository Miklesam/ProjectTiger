package com.miklesam.dotamanager.multipleer.host

import android.util.Log
import com.miklesam.dotamanager.multipleer.getInfo
import java.io.*
import java.net.ServerSocket

class ServerThread(val gi: getInfo) : Runnable {
    lateinit var serverSocket: ServerSocket
    private lateinit var threadComm: Thread
    private lateinit var commThread: CommunicationThread

    override fun run() {
        try {
            serverSocket = ServerSocket(3003)
        } catch (e: IOException) {
        }
        Log.w("StAck", "1")
        while (!Thread.currentThread().isInterrupted) {
            try {
                val socket = serverSocket.accept()
                commThread =
                    CommunicationThread(
                        socket,
                        gi
                    )
                threadComm = Thread(commThread)
                threadComm.start()
                Log.w("StAck", "2")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    fun sendMessage(message:String){
        commThread.sendMessage(message)
    }
}

