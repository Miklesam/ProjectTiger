package com.miklesam.dotamanager.ui.market

import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.DetailsTransition
import com.miklesam.dotamanager.FragmentPlayerProfile
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.MarketAdapter
import com.miklesam.dotamanager.adapters.MarketPlayerHolder
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.datamodels.Player
import java.util.ArrayList

class FragmentMarket :Fragment(R.layout.fragment_market),OnPlayerListener{
    private lateinit var marketViewModel: MarketViewModel

    override fun onPlayerClick(position: Int,holder: MarketPlayerHolder) {
        Log.w("FragmentMarket","Clicked "+position)
        val fragment = FragmentPlayerProfile()

        //fragment.setSharedElementEnterTransition(DetailsTransition())
        //val fade = Fade()
        //fragment.setEnterTransition(fade)
        //exitTransition = fade
        //fragment.setSharedElementReturnTransition(DetailsTransition())

        val transaction=
            activity?.supportFragmentManager?.beginTransaction()
                //?.addSharedElement(holder.photo, "playerImage")
        //transaction?.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right)
        transaction?.replace(R.id.fragment_holder, fragment)
        //transaction?.addToBackStack(null)
        transaction?.commit()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        marketViewModel= ViewModelProviders.of(this).get(MarketViewModel::class.java)

        val recycler=view.findViewById<RecyclerView>(R.id.recyclerPlayers)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
        val adapter = MarketAdapter(this)
        recycler.adapter = adapter

        marketViewModel.getPlayers().observe(this, Observer {
            adapter.setPlayers(it)
        })
    }

}

