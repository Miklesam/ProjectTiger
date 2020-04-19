package com.miklesam.dotamanager.simplefragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.utils.PrefsHelper
import kotlinx.android.synthetic.main.fragment_newgame.*


class FragmentNewGame :Fragment(R.layout.fragment_newgame){

    interface startListener {
        fun startClickedClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val startListener = activity as startListener
        startBttn.setOnClickListener {
            val toast = Toast(context)
            toast.duration = Toast.LENGTH_LONG
            val inflater =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view: View = inflater.inflate(R.layout.your_custom_layout, null)
            val customToastText = view.findViewById<TextView>(R.id.customToastText)
            toast.view = view

            if (editText.text.isNotEmpty()){
                PrefsHelper.write(
                    PrefsHelper.TEAM_NAME,editText.text.toString())
                startListener.startClickedClicked()
            }else{
                customToastText.text="Type your team name"
                toast.show()

            }
        }

    }
}