package com.miklesam.dotamanager.multipleer.client

import android.util.Log
import com.miklesam.dotamanager.multipleer.getInfo
import java.io.*
import java.net.ConnectException
import java.net.InetAddress
import java.net.Socket

class ClientThread(val gi: getInfo, val ip: String) : Runnable {
    lateinit var socket: Socket

    override fun run() {
        try {
            Log.w("Client", "try to connect $ip")
            val serverAddr = InetAddress.getByName(ip)
            socket = Socket(serverAddr, 3003)
            sendMessage("connwect")
            while (!Thread.currentThread().isInterrupted) {
                val input =
                    BufferedReader(InputStreamReader(socket.getInputStream()))
                val message: String = input.readLine()
                Log.w("Client", "message is $message")
                gi.getInfo(message)
            }

        } catch (e: ConnectException) {
            e.printStackTrace()
            gi.getInfo("Error")
        }catch (e1: Throwable) {
            e1.printStackTrace()
            gi.getInfo("Error")
        }

    }

    fun sendMessage(message: String?) {
        Thread(Runnable {
            try {
                    val out = PrintWriter(
                        BufferedWriter(
                            OutputStreamWriter(socket.getOutputStream())
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