package com.miklesam.dotamanager.multipleer.client

import android.annotation.SuppressLint
import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_client.*


class FragmentClient : Fragment(R.layout.fragment_client) {
    private lateinit var clientViewModel: ClientViewModel
    var myTurn = false
    var arrayTrust = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    interface clientListener{
        fun connectOk()
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener= activity as clientListener
        clientViewModel =
            ViewModelProviders.of(this).get(ClientViewModel::class.java)

        val viewArray = arrayOf(
            button_00, button_01, button_02,
            button_10, button_11, button_12,
            button_20, button_21, button_22
        )
        editText.setText("192.168.1.73")
        connect_to_host_bttn.setOnClickListener {
            hideKeyboard()
            clientViewModel.connectHost(editText.text.toString())

        }

        button_resetClient.setOnClickListener {clientViewModel.resetBttn()  }

        //send_msg.setOnClickListener { clientViewModel.sendToHost() }

        clientViewModel.getToastMessage().observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        for (i in 0 until 9) {
            viewArray[i].setOnClickListener {
                if (myTurn && arrayTrust[i] == 0) clientViewModel.setPoint(
                    i
                )
            }
        }

        clientViewModel.getTicTac().observe(this, Observer {
            arrayTrust = it
            when (it[9]) {
                1 -> myTurn = false
                2 -> myTurn = true
            }
            for (i in 0..8) {
                when (it[i]) {
                    0 -> viewArray[i].text = ""
                    1 -> viewArray[i].text = "X"
                    2 -> viewArray[i].text = "0"
                }
            }
        })


        clientViewModel.getProgress().observe(this, Observer {
            when (it) {
                0 -> {
                    pb.visibility = View.GONE
                    waiting_for_host.visibility = View.GONE
                    connect_to_host_bttn.visibility = View.VISIBLE
                }
                1 -> {
                    pb.visibility = View.VISIBLE
                    waiting_for_host.visibility = View.VISIBLE
                    connect_to_host_bttn.visibility = View.GONE
                    editText.visibility = View.GONE
                }
                2 -> {
                    pb.visibility = View.GONE
                    waiting_for_host.visibility = View.GONE
                    connect_to_host_bttn.visibility = View.GONE
                    gameLine.visibility = View.VISIBLE
                    //
                    listener.connectOk()
                    clientViewModel.setConnect()
                }
                3 -> {
                    pb.visibility = View.GONE
                    waiting_for_host.visibility = View.GONE
                    connect_to_host_bttn.visibility = View.VISIBLE
                    editText.visibility = View.VISIBLE
                }

            }
        })

    }
}