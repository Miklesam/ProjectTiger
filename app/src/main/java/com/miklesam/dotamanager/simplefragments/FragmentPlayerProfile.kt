package com.miklesam.dotamanager.simplefragments

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Heroes
import com.miklesam.dotamanager.datamodels.Player
import kotlinx.android.synthetic.main.fragment_player_profile.*

class FragmentPlayerProfile :Fragment(R.layout.fragment_player_profile){

    var player: Player? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!==null){
            player= arguments!!.get("player") as Player?
            Log.w("FragmentProfile", "player is"+player.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerName.text=player!!.nickname
        Glide.with(this)
            .load(player!!.photo)
            .into(image)
        playerFullName.text=player!!.name
        microControlNum.text= player!!.microcontrol.toString()
        macroControlNum.text=player!!.macrocontrol.toString()
        creativityNum.text=player!!.creativity.toString()
        heroPoolNum.text=player!!.heropool.toString()
        motivationNum.text=player!!.motivation.toString()
        mediationNum.text=player!!.mediafans.toString()
        laningNum.text=player!!.laining.toString()
        fightingNum.text=player!!.fighting.toString()
        tacticNum.text=player!!.tactics.toString()
        moralNum.text=player!!.morals.toString()
        val inputStream =  context?.contentResolver?.openInputStream(player!!.flag.toUri())
        val drawable = Drawable.createFromStream(inputStream, player!!.flag)
        backFlag.background=drawable
        signature1.setImageResource(Heroes.values()[player!!.signature1].image_pick)
        signature2.setImageResource(Heroes.values()[player!!.signature2].image_pick)
        signature3.setImageResource(Heroes.values()[player!!.signature3].image_pick)
    }
}