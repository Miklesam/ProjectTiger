package com.miklesam.dotamanager.simplefragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.MarketAdapter
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.ui.choosePlayers.ChoosenPlayersViewModel
import kotlinx.android.synthetic.main.fragment_position_pager.*


class PositionsPager(
    val positionFr: Int
) : Fragment(R.layout.fragment_position_pager), OnPlayerListener {


    private val mainViewModel by viewModels<ChoosenPlayersViewModel>()

    private var adapter: MarketAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        position_recucler?.layoutManager = LinearLayoutManager(context)
        position_recucler?.setHasFixedSize(true)
        adapter = MarketAdapter(this)
        position_recucler?.adapter = adapter
        mainViewModel.getPlayers().observe(viewLifecycleOwner, Observer {
            val s11 = it.filter { it.position == positionFr.toString() }
            adapter?.setPlayers(s11)
            adapter?.notifyDataSetChanged()
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
    }

    override fun onPlayerClick(position: Int, holder: RecyclerView.ViewHolder) {
    }
}