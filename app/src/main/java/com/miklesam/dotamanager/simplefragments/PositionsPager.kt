package com.miklesam.dotamanager.simplefragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.games.Player
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.MarketAdapter
import com.miklesam.dotamanager.adapters.OnPlayerChooseListener
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.ui.choosePlayers.ChoosenPlayersViewModel
import kotlinx.android.synthetic.main.fragment_position_pager.*


class PositionsPager(
    val positionFr: Int,
    val listener: OnPlayerChooseListener
) : Fragment(R.layout.fragment_position_pager), OnPlayerListener {

    var listPlayers: List<com.miklesam.dotamanager.datamodels.Player>? = null


    private val mainViewModel by viewModels<ChoosenPlayersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position_recucler?.layoutManager = LinearLayoutManager(requireContext())
        position_recucler?.setHasFixedSize(true)
        val adapter = MarketAdapter(this)
        position_recucler?.adapter = adapter
        mainViewModel.getPlayers().observe(viewLifecycleOwner, Observer {
            listPlayers = it.filter { it.position == positionFr.toString() }
            adapter.setPlayers(listPlayers!!)
            adapter.notifyDataSetChanged()
        })


    }

    override fun onDestroyView() {
        listPlayers = null
        super.onDestroyView()
    }

    override fun onPlayerClick(position: Int, holder: RecyclerView.ViewHolder) {
        listener.onPlayerClick(positionFr, listPlayers?.get(position)?.nickname ?: "")
    }
}