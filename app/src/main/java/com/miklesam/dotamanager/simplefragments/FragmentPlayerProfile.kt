package com.miklesam.dotamanager.simplefragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Heroes
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.utils.DataConverter
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
        //image.setImageResource(player!!.photo)
        image.setImageBitmap(DataConverter.convertByteArray2Image(player!!.photo))
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
        //backFlag.background= context?.let { AppCompatResources.getDrawable(it, player!!.flag) }
        backFlag.background=context?.let{ BitmapDrawable(DataConverter.convertByteArray2Image(player!!.flag)) }
        signature1.setImageResource(Heroes.values()[player!!.signature1].image_pick)
        signature2.setImageResource(Heroes.values()[player!!.signature2].image_pick)
        signature3.setImageResource(Heroes.values()[player!!.signature3].image_pick)
    }
}