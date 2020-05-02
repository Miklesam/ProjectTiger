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
    interface clientListener{
        fun clientOk()
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener= activity as clientListener
        clientViewModel =
            ViewModelProviders.of(requireActivity()).get(ClientViewModel::class.java)

        editText.setText("192.168.1.73")
        connect_to_host_bttn.setOnClickListener {
            hideKeyboard()
            clientViewModel.connectHost(editText.text.toString())

        }
        //send_msg.setOnClickListener { clientViewModel.sendToHost() }

        clientViewModel.getToastMessage().observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
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
                    //
                    listener.clientOk()
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