package com.miklesam.dotamanager.ui.prematch

import android.app.Application
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.ui.closedquali.ClosedRepository
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.utils.plusDay
import kotlinx.android.synthetic.main.fragment_prematch.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class PreMatch : Fragment(R.layout.fragment_prematch) {

    interface afterCalculate {
        fun calculateTolobby()
        fun playGame()
    }

    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuListener = activity as afterCalculate
        val preVM = ViewModelProviders.of(this).get(PreMatchVM::class.java)
        val enemy = PrefsHelper.read(PrefsHelper.ENEMY_NAME, "")
        preVM.getState().observe(this, Observer {
            if (!it) {
                playMatch.visibility = VISIBLE
                calculateMatch.visibility = VISIBLE
                yourTeamScore.visibility = GONE
                enemyTeamScore.visibility = GONE
                matchResult.visibility = GONE
                nextAfterMatch.visibility = GONE
            } else {
                playMatch.visibility = GONE
                calculateMatch.visibility = GONE
                yourTeamScore.visibility = VISIBLE
                enemyTeamScore.visibility = VISIBLE
                matchResult.visibility = VISIBLE
                nextAfterMatch.visibility = VISIBLE
            }
        })
        scope.launch {
            ClosedRepository(activity!!.application).nukeClosed()
        }

        enemy?.let {
            preVM.getTeamByName(it).observe(this, Observer {
                Glide.with(this)
                    .load(it.teamLogo)
                    .into(enemyImage)
                enemyTeamName.text = it.teamName
            })
        }

        preVM.getWinner().observe(this, Observer {
            if (it) {
                matchResult.text = "Radiant Victory"
                matchResult.setTextColor(ContextCompat.getColor(context!!, R.color.radiant_victory))
            } else {
                matchResult.text = "Dire Victory"
                matchResult.setTextColor(ContextCompat.getColor(context!!, R.color.dire_victory))
            }
        })
        preVM.getRadScore().observe(this, Observer {
            yourTeamScore.text = it.toString()
        })
        preVM.getDireScore().observe(this, Observer {
            enemyTeamScore.text = it.toString()
        })
        calculateMatch.setOnClickListener {
            preVM.setCalculate(true)
            val rndsRad = (0..45).random()
            val rndsDire = (0..45).random()
            preVM.setRadiant(rndsRad)
            preVM.setDire(rndsDire)
            if (rndsRad > rndsDire) {
                preVM.setWinner(true)
            } else {
                preVM.setWinner(false)
            }
        }

        nextAfterMatch.setOnClickListener {
            plusDay()
            menuListener.calculateTolobby()
        }

        playMatch.setOnClickListener { menuListener.playGame() }
    }
}