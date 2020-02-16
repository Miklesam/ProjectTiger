package com.miklesam.dotamanager

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.adapters.MarketAdapter
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.datamodels.Player
import java.util.ArrayList

class FragmentMarket :Fragment(R.layout.fragment_market),OnPlayerListener{
    override fun onPlayerClick(position: Int) {
        Log.w("FragmentMarket","Clicked "+position)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler=view.findViewById<RecyclerView>(R.id.recyclerPlayers)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
        val adapter = MarketAdapter(this)
        recycler.adapter = adapter
        val players=ArrayList<Player>()
        players.add(Player("Solo","Алексей\nБерезин","5","1500"))
        players.add(Player("No[o]ne","Владимир\nМиненко","2","350"))
        players.add(Player("Fng","Артем\nБаршак","5","1350"))
        players.add(Player("Nix","Александр\nЛевин","2","650"))
        adapter.setPlayers(players)

    }
}

