package com.miklesam.dotamanager.multipleer.host

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miklesam.dotamanager.R
import kotlinx.android.synthetic.main.fragment_host.*
import java.util.*

class FragmentHost : Fragment(R.layout.fragment_host) {
    private lateinit var hostViewModel: HostViewModel
    interface hostListener{
        fun hostOk()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener = activity as hostListener
        hostViewModel = ViewModelProviders.of(requireActivity()).get(HostViewModel::class.java)
        start_host_bttn.setOnClickListener { hostViewModel.startHost() }

        hostViewModel.getToastMessage().observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        hostViewModel.getProgress().observe(this, Observer {
            when (it) {
                0 -> {
                    start_host_bttn.visibility = VISIBLE
                    pb.visibility = GONE
                    waiting_for_player.visibility = GONE
                }
                1 -> {
                    start_host_bttn.visibility = GONE
                    pb.visibility = VISIBLE
                    waiting_for_player.visibility = VISIBLE
                }
                2 -> {
                    start_host_bttn.visibility = GONE
                    pb.visibility = GONE
                    waiting_for_player.visibility = GONE
                    ip_address.visibility= GONE
                    //Start Another Fragment
                    listener.hostOk()
                    hostViewModel.setConnect()
                }
            }
        })

        val wm = activity?.application?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInf = wm.connectionInfo
        val ipAddress = wifiInf.ipAddress
        val ip = String.format(
            Locale.getDefault(),
            "%d.%d.%d.%d",
            ipAddress and 0xff,
            ipAddress shr 8 and 0xff,
            ipAddress shr 16 and 0xff,
            ipAddress shr 24 and 0xff
        )
        val ipMessage = "your ip address is $ip"
        ip_address.text = ipMessage
    }

}