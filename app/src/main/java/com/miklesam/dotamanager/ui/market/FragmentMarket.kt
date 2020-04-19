package com.miklesam.dotamanager.ui.market

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.MarketAdapter
import com.miklesam.dotamanager.adapters.MarketPlayerHolder
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.datamodels.Player

class FragmentMarket :Fragment(R.layout.fragment_market),OnPlayerListener{
    private var marketViewModel: MarketViewModel?=null
    var playerChooses:playerChoose?=null
    var players: List<Player>? = null
    var recycler:RecyclerView?=null
    var adapter : MarketAdapter?=null

    interface playerChoose{
        fun onPlayerClickFragment(player :Player,holder: MarketPlayerHolder)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        marketViewModel= ViewModelProviders.of(this).get(MarketViewModel::class.java)
        playerChooses= activity as playerChoose
        recycler=view.findViewById(R.id.recyclerPlayers)
        recycler?.layoutManager = LinearLayoutManager(context)
        recycler?.setHasFixedSize(true)
        adapter = MarketAdapter(this)
        recycler?.adapter = adapter

        marketViewModel?.getPlayers()?.observe(this, Observer {
            players=it
            adapter?.setPlayers(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        marketViewModel=null
        playerChooses=null
        players=null
        recycler=null
        adapter=null
    }

    override fun onPlayerClick(position: Int, holder: RecyclerView.ViewHolder) {
        Log.w("FragmentMarket","Clicked "+position)
        players?.get(position)?.let { playerChooses?.onPlayerClickFragment(it,holder as MarketPlayerHolder) }
    }
}

