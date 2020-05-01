package com.miklesam.dotamanager.simplefragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import kotlinx.android.synthetic.main.fragment_multipleer.*

class FragmentMultipleer :Fragment(R.layout.fragment_multipleer){
    interface MultioleerListener {
        fun hostClicked()
        fun clientClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuListener = activity as MultioleerListener
        host_bttn.setOnClickListener { menuListener.hostClicked() }
        connect_to_host.setOnClickListener { menuListener.clientClicked() }
    }
}