package com.miklesam.dotamanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import com.miklesam.dotamanager.datamodels.Heroes

class MainActivity : AppCompatActivity(), FragmentMenu.MenuListener, FragmentLobby.LobbyListener {
    override fun gameClicked() {
        showGame()
    }

    override fun marketClicked() {
        showMarket()
    }

    override fun trainingClicked() {
        showTrainning()
    }

    override fun teamClicked() {
        showTeam()
    }

    override fun lobbyClicked() {
        showLobby()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        if (savedInstanceState == null) {
            showFragmentMain()
        }

    }


    private fun showFragmentMain() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentMenu()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    private fun showGame() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentGame()
        transaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showMarket() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentMarket()
        transaction.setCustomAnimations(R.anim.enter_left_to_right,R.anim.exit_left_to_right,R.anim.enter_right_to_left,R.anim.exit_right_to_left)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showTeam() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentTeam()
        transaction.setCustomAnimations(R.anim.enter_top_to_bottom,R.anim.exit_top_to_bottom,R.anim.enter_bottom_to_top,R.anim.exit_bottom_to_top)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun showTrainning() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentTraining()
        transaction.setCustomAnimations(R.anim.enter_bottom_to_top,R.anim.exit_bottom_to_top,R.anim.enter_top_to_bottom,R.anim.exit_top_to_bottom)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showLobby() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = FragmentLobby()
        transaction.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }





}
