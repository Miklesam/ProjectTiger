package com.miklesam.dotamanager.ui.teamsprofile

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.adapters.TeamPlayersAdapter
import com.miklesam.dotamanager.utils.TeamsAudio
import kotlinx.android.synthetic.main.fragment_teams_profile.*
import java.util.*

class FragmentTeamsProfile : Fragment(R.layout.fragment_teams_profile), OnPlayerListener {

    private var teamProfileViewModel: TeamsProfileViewModel? = null
    var recycler: RecyclerView? = null
    var adapter: TeamPlayersAdapter? = null
    lateinit var players: List<String>
    var soundPull: SoundPool?= null
    var soundOne: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        teamProfileViewModel = ViewModelProviders.of(this).get(TeamsProfileViewModel::class.java)
        if (arguments != null) {
            players = arguments!!.getStringArrayList("players")!!
            val teamNametext = arguments!!.getString("teamName")
            if (teamNametext != null) {
                teamProfileViewModel?.setTeamName(teamNametext)
                val audioAtributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

                soundPull = SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAtributes)
                    .build()
                soundOne = soundPull!!.load(context, TeamsAudio.values().find { it.teamName==teamNametext}!!.voice, 1)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        soundPull?.stop(soundOne)
        soundPull=null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callYourPick()
        recycler = view.findViewById(R.id.teamRecycler)
        recycler?.layoutManager = GridLayoutManager(context, 5)
        recycler?.setHasFixedSize(true)
        adapter = TeamPlayersAdapter(context, this)
        recycler?.adapter = adapter

        teamProfileViewModel?.getPlayer(players)?.observe(this, Observer {
            adapter?.setPlayers(it)
        })
        teamProfileViewModel?.getTeamName()
            ?.observe(this, Observer {
                teamName.text = it
            })
    }

    private fun callYourPick(){
        val timer2 = object : CountDownTimer(400, 100) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                soundPull?.play(soundOne, 1F, 1F, 0, 0, 1F)
            }
        }
        timer2.start()
    }

    override fun onPlayerClick(position: Int, holder: RecyclerView.ViewHolder) {
        Log.w("asdasdas", "asdasdqq")
    }

}