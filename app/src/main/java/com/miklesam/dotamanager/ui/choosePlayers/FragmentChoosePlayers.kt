package com.miklesam.dotamanager.ui.choosePlayers

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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.adapters.SectionsPagerAdapter
import com.miklesam.dotamanager.datamodels.Heroes
import com.miklesam.dotamanager.utils.showCustomToast
import kotlinx.android.synthetic.main.fragment_choose_players.*
import kotlinx.android.synthetic.main.fragment_choose_players.backFlag
import kotlinx.android.synthetic.main.fragment_choose_players.image
import kotlinx.android.synthetic.main.fragment_choose_players.pager_positions
import kotlinx.android.synthetic.main.fragment_choose_players.playerFullName
import kotlinx.android.synthetic.main.fragment_choose_players.playerName
import kotlinx.android.synthetic.main.fragment_choose_players.signature1
import kotlinx.android.synthetic.main.fragment_choose_players.signature2
import kotlinx.android.synthetic.main.fragment_choose_players.signature3
import kotlinx.android.synthetic.main.fragment_choose_players.tabs

class FragmentChoosePlayers : Fragment(R.layout.fragment_choose_players) {
    private var choosenViewModel: ChoosenPlayersViewModel? = null
    var player: com.miklesam.dotamanager.datamodels.Player? = null
    var playerSortedList: List<com.miklesam.dotamanager.datamodels.Player>? = null
    var player1picked = false
    var player2picked = false
    var player3picked = false
    var player4picked = false
    var player5picked = false
    var pager_positionS: ViewPager? = null

    interface nextChoosenListener {
        fun nextChoosenClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        choosenViewModel = ViewModelProviders.of(this).get(ChoosenPlayersViewModel::class.java)
        val nextChoosen = activity as nextChoosenListener
        pager_positionS = view.findViewById(R.id.pager_positions)

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
        })
        cancelBttn.setOnClickListener {
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


        val sectionsPagerAdapter = SectionsPagerAdapter(parentFragmentManager)
        (pager_positionS as ViewPager).adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(pager_positions)


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


    override fun onDestroyView() {
        super.onDestroyView()
        pager_positionS = null
    }
}