package com.miklesam.dotamanager.ui.choosePlayers

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.MarketAdapter
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.datamodels.Heroes
import com.miklesam.dotamanager.utils.showCustomToast
import kotlinx.android.synthetic.main.fragment_choose_players.*
import kotlinx.android.synthetic.main.fragment_choose_players.backFlag
import kotlinx.android.synthetic.main.fragment_choose_players.creativityNum
import kotlinx.android.synthetic.main.fragment_choose_players.fightingNum
import kotlinx.android.synthetic.main.fragment_choose_players.heroPoolNum
import kotlinx.android.synthetic.main.fragment_choose_players.image
import kotlinx.android.synthetic.main.fragment_choose_players.laningNum
import kotlinx.android.synthetic.main.fragment_choose_players.macroControlNum
import kotlinx.android.synthetic.main.fragment_choose_players.mediationNum
import kotlinx.android.synthetic.main.fragment_choose_players.microControlNum
import kotlinx.android.synthetic.main.fragment_choose_players.moralNum
import kotlinx.android.synthetic.main.fragment_choose_players.motivationNum
import kotlinx.android.synthetic.main.fragment_choose_players.playerFullName
import kotlinx.android.synthetic.main.fragment_choose_players.playerName
import kotlinx.android.synthetic.main.fragment_choose_players.signature1
import kotlinx.android.synthetic.main.fragment_choose_players.signature2
import kotlinx.android.synthetic.main.fragment_choose_players.signature3
import kotlinx.android.synthetic.main.fragment_choose_players.tacticNum
import kotlinx.android.synthetic.main.fragment_player_profile.*

class FragmentChoosePlayers : Fragment(R.layout.fragment_choose_players), OnPlayerListener {
    private var choosenViewModel: ChoosenPlayersViewModel? = null
    var recycler: RecyclerView? = null
    var adapter: MarketAdapter? = null
    var player: com.miklesam.dotamanager.datamodels.Player? = null
    var playerSortedList: List<com.miklesam.dotamanager.datamodels.Player>? = null
    var player1picked = false
    var player2picked = false
    var player3picked = false
    var player4picked = false
    var player5picked = false

    interface nextChoosenListener {
        fun nextChoosenClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        choosenViewModel = ViewModelProviders.of(this).get(ChoosenPlayersViewModel::class.java)
        val nextChoosen = activity as nextChoosenListener

        recycler = view.findViewById(R.id.recyclerPlayers)
        recycler?.layoutManager = LinearLayoutManager(context)
        recycler?.setHasFixedSize(true)
        adapter = MarketAdapter(this)
        recycler?.adapter = adapter
        teamName.text = PrefsHelper.read(
            PrefsHelper.TEAM_NAME, "Your team name"
        )

        nextBttn.setOnClickListener {
            PrefsHelper.write(PrefsHelper.SHOW_CONTINUE, "1")
            PrefsHelper.write(PrefsHelper.CAREER_DAY, "0")
            PrefsHelper.write(PrefsHelper.CAREER_MONTH, "0")
            PrefsHelper.write(PrefsHelper.CAREER_YEAR, "0")
            nextChoosen.nextChoosenClicked()
        }

        choosenViewModel?.getPlayers()?.observe(viewLifecycleOwner, Observer { playerList ->
            Log.w("Choosen", playerList.toString())
            playerSortedList = playerList
            adapter?.setPlayers(playerList)
        })
        cancelBttn.setOnClickListener {
            recycler?.visibility = VISIBLE
            playerInfo.visibility = GONE
            bttn_layout.visibility = GONE
        }

        position1Bttn.setOnClickListener {
            if (!checkContains()) {
                player?.nickname?.let { it1 -> PrefsHelper.write(PrefsHelper.POSITION_1, it1) }
                position1.text = player?.nickname
                player1picked = true
                checkNextBttn()
            } else {
                showCustomToast("Уже в команде", Toast.LENGTH_SHORT)
            }

        }

        position2Bttn.setOnClickListener {
            if (!checkContains()) {
                player?.nickname?.let { it1 -> PrefsHelper.write(PrefsHelper.POSITION_2, it1) }
                position2.text = player?.nickname
                player2picked = true
                checkNextBttn()
            } else {
                showCustomToast("Уже в команде", Toast.LENGTH_SHORT)
            }
        }

        position3Bttn.setOnClickListener {
            if (!checkContains()) {
                player?.nickname?.let { it1 -> PrefsHelper.write(PrefsHelper.POSITION_3, it1) }
                position3.text = player?.nickname
                player3picked = true
                checkNextBttn()
            } else {
                showCustomToast("Уже в команде", Toast.LENGTH_SHORT)
            }
        }

        position4Bttn.setOnClickListener {
            if (!checkContains()) {
                player?.nickname?.let { it1 -> PrefsHelper.write(PrefsHelper.POSITION_4, it1) }
                position4.text = player?.nickname
                player4picked = true
                checkNextBttn()
            } else {
                showCustomToast("Уже в команде", Toast.LENGTH_SHORT)
            }
        }

        position5Bttn.setOnClickListener {
            if (!checkContains()) {
                player?.nickname?.let { it1 -> PrefsHelper.write(PrefsHelper.POSITION_5, it1) }
                position5.text = player?.nickname
                player5picked = true
                checkNextBttn()
            } else {
                showCustomToast("Уже в команде", Toast.LENGTH_SHORT)
            }
        }


    }

    fun checkContains(): Boolean {
        return player?.nickname.equals(PrefsHelper.read(PrefsHelper.POSITION_1, "")) ||
                player?.nickname.equals(PrefsHelper.read(PrefsHelper.POSITION_2, "")) ||
                player?.nickname.equals(PrefsHelper.read(PrefsHelper.POSITION_3, "")) ||
                player?.nickname.equals(PrefsHelper.read(PrefsHelper.POSITION_4, "")) ||
                player?.nickname.equals(PrefsHelper.read(PrefsHelper.POSITION_5, ""))
    }

    fun checkNextBttn() {
        if (player1picked && player2picked && player3picked && player4picked && player5picked) nextBttn.visibility =
            VISIBLE
    }

    override fun onPlayerClick(position: Int, holder: RecyclerView.ViewHolder) {
        Log.w("Click", "position $position")
        recycler?.visibility = GONE
        playerInfo.visibility = VISIBLE
        bttn_layout.visibility = VISIBLE
        player = playerSortedList?.get(position)
        playerName.text = player!!.nickname
        Glide.with(this)
            .load(player!!.photo)
            .into(image)
        playerFullName.text = player!!.name
        //microControlNum.text = player!!.microcontrol.toString()
        //macroControlNum.text = player!!.macrocontrol.toString()
        //creativityNum.text = player!!.creativity.toString()
        //heroPoolNum.text = player!!.heropool.toString()
       //motivationNum.text = player!!.motivation.toString()
        //mediationNum.text = player!!.mediafans.toString()
      //  laningNum.text = player!!.laining.toString()
       // fightingNum.text = player!!.fighting.toString()
      //  tacticNum.text = player!!.tactics.toString()
      // moralNum.text = player!!.morals.toString()
        val inputStream = context?.contentResolver?.openInputStream(player!!.flag.toUri())
        val drawable = Drawable.createFromStream(inputStream, player!!.flag)
        backFlag.background = drawable
        signature1.setImageResource(Heroes.values()[player!!.signature1].image_pick)
        signature2.setImageResource(Heroes.values()[player!!.signature2].image_pick)
        signature3.setImageResource(Heroes.values()[player!!.signature3].image_pick)


    }
}