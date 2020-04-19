package com.miklesam.dotamanager.simplefragments

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.BuildConfig
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.utils.PrefsHelper.POSITION_1
import com.miklesam.dotamanager.utils.PrefsHelper.POSITION_2
import com.miklesam.dotamanager.utils.PrefsHelper.POSITION_3
import com.miklesam.dotamanager.utils.PrefsHelper.POSITION_4
import com.miklesam.dotamanager.utils.PrefsHelper.POSITION_5
import com.miklesam.dotamanager.utils.PrefsHelper.SHOW_CONTINUE
import com.miklesam.dotamanager.utils.showCustomToast
import kotlinx.android.synthetic.main.fragment_menu.*

class FragmentMenu :Fragment(R.layout.fragment_menu){

    interface MenuListener {
        fun newGameClicked()
        fun contunueClicked()
        fun achievementsClicked()
        fun teamsClicked()
        fun aboutClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuListener = activity as MenuListener
        val bttn=view.findViewById<Button>(R.id.playGame)
        bttn.setOnClickListener {
            PrefsHelper.write(SHOW_CONTINUE,"0")
            PrefsHelper.write(POSITION_1,"")
            PrefsHelper.write(POSITION_2,"")
            PrefsHelper.write(POSITION_3,"")
            PrefsHelper.write(POSITION_4,"")
            PrefsHelper.write(POSITION_5,"")
            menuListener.newGameClicked()
        }
        teams.setOnClickListener { menuListener.teamsClicked() }


        multipleer.setOnClickListener {
            showCustomToast("In Progress",Toast.LENGTH_SHORT)
        }
        achievments.setOnClickListener { menuListener.achievementsClicked() }
        about.setOnClickListener {
            //showCustomToast("In Progress",Toast.LENGTH_SHORT)
            menuListener.aboutClicked()
        }



        versionText.text= BuildConfig.VERSION_NAME
        if(PrefsHelper.read(SHOW_CONTINUE,"0").equals("1")){
            continue_bttn.visibility=VISIBLE
        }else{
            continue_bttn.visibility=GONE
        }

        continue_bttn.setOnClickListener { menuListener.contunueClicked() }
    }
}