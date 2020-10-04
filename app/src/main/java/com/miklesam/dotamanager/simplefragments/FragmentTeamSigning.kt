package com.miklesam.dotamanager.simplefragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.ui.team.TeamViewModel
import com.miklesam.dotamanager.utils.PrefsHelper
import kotlinx.android.synthetic.main.fragment_about.*
import java.util.*

class FragmentTeamSigning :Fragment(R.layout.fragment_about),TextToSpeech.OnInitListener{

    private val teamViewModel by viewModels<TeamViewModel>()
    private var tts: TextToSpeech? = null

    interface gotoLobby{
        fun toLobby()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(context, this)
        teamViewModel.getPlayersByNickNames().observe(this, Observer {
             val inputStream1 =  context?.contentResolver?.openInputStream(it[0].photo.toUri())
             val player1 = Drawable.createFromStream(inputStream1, it[0].photo)
            val inputStream2 =  context?.contentResolver?.openInputStream(it[1].photo.toUri())
            val player2 = Drawable.createFromStream(inputStream2, it[1].photo)
            val inputStream3 =  context?.contentResolver?.openInputStream(it[2].photo.toUri())
            val player3 = Drawable.createFromStream(inputStream3, it[2].photo)
            val inputStream4 =  context?.contentResolver?.openInputStream(it[3].photo.toUri())
            val player4 = Drawable.createFromStream(inputStream4, it[3].photo)
            val inputStream5 =  context?.contentResolver?.openInputStream(it[4].photo.toUri())
            val player5 = Drawable.createFromStream(inputStream5, it[4].photo)
            teamSigning.setPlayer(player1,
                player2,
                player3,
                player4,
                player5)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener = activity as gotoLobby
        answer1.setOnClickListener { listener.toLobby() }
        answer2.setOnClickListener { listener.toLobby() }
        teamNameText.setOnClickListener { speakOut() }


        val teamNaming=PrefsHelper.read(PrefsHelper.TEAM_NAME,"")
        teamNameText.text="Команда $teamNaming \n подписывает перспективный состав"

    }

    override fun onInit(p0: Int) {
        if (p0 === TextToSpeech.SUCCESS) {
            val result: Int = tts?.setLanguage(Locale.ENGLISH) ?: 0

            tts?.setPitch(1.2F) // set pitch level

            tts?.setSpeechRate(0.9F) // set speech speed rate
            if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Log.e("TTS", "Language is not supported")
            } else {
                //btnSpeak.setEnabled(true)
                //speakOut()
            }
        } else {
            Log.e("TTS", "Initilization Failed")
        }
    }

    private fun speakOut() {
        val text = PrefsHelper.read(PrefsHelper.TEAM_NAME,"")
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

}