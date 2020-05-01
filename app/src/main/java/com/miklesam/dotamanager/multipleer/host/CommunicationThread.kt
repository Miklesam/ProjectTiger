package com.miklesam.dotamanager.multipleer.host

import android.util.Log
import com.miklesam.dotamanager.multipleer.getInfo
import java.io.*
import java.net.Socket

class CommunicationThread(
    clienSocket: Socket,
    val myInfo: getInfo
) : Runnable {
    private val clientSocket: Socket = clienSocket
    private val input: BufferedReader

    init {
        input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        try {
            Log.w("StAck", "3")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun run() {
        while (!Thread.currentThread().isInterrupted) {
            try {
                val read = input.readLine()
                if (read != null) {
                    myInfo.getInfo(read)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun sendMessage(message: String?) {
        Thread(Runnable {
            try {
                val out = PrintWriter(
                    BufferedWriter(
                        OutputStreamWriter(clientSocket.getOutputStream())
                    ),
                    true
                )
                out.println(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }
}