package com.miklesam.dotamanager.ui.plainingstage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.CustomAdapter
import com.miklesam.dotamanager.adapters.OnPlayerListener
import com.miklesam.dotamanager.adapters.TeamPlayersAdapter
import com.miklesam.dotamanager.datamodels.Heroes
import com.miklesam.dotamanager.utils.showCustomToast
import kotlinx.android.synthetic.main.fragment_plaining_stage.*


class PlainingStage : Fragment(R.layout.fragment_plaining_stage), OnPlayerListener {
    private var plainingViewModel: PlainingStageViewModel? = null
    var recycler: RecyclerView? = null
    var adapter: TeamPlayersAdapter? = null
    val heroArray: ArrayList<Heroes> = ArrayList()
    var heroes: ArrayList<Int>? = null
    var direHeroes: ArrayList<Int>? = null

    interface nextFromPlaining {
        fun plainingEnded(
            heroes: ArrayList<Int>?,
            direHeroes: ArrayList<Int>?
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            heroes = arguments!!.getIntegerArrayList(("radiant")!!)
            direHeroes = arguments!!.getIntegerArrayList(("dire")!!)
            heroes?.forEach { heroId ->
                heroArray.add(Heroes.values().find { it.id == heroId }!!)
            }

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val endedListener = activity as nextFromPlaining
        plainingViewModel = ViewModelProviders.of(this).get(PlainingStageViewModel::class.java)
        recycler = view.findViewById(R.id.teamRecycler)
        recycler?.layoutManager = GridLayoutManager(context, 5)
        recycler?.setHasFixedSize(true)
        adapter = TeamPlayersAdapter(context, this)
        recycler?.adapter = adapter
        plainingViewModel?.getPlayer()?.observe(this, Observer {
            adapter?.setPlayers(it)

        })
        continue_bttn.setOnClickListener {
            if (spinner1.selectedItemPosition != spinner2.selectedItemPosition) {
                val hero1 = heroes?.get(spinner1.selectedItemPosition)
                val hero2 = heroes?.get(spinner2.selectedItemPosition)
                val hero3 = heroes?.get(spinner3.selectedItemPosition)
                val hero4 = heroes?.get(spinner4.selectedItemPosition)
                val hero5 = heroes?.get(spinner5.selectedItemPosition)
                endedListener.plainingEnded(
                    arrayListOf(
                        hero1 ?: 0,
                        hero2 ?: 0,
                        hero3 ?: 0,
                        hero4 ?: 0,
                        hero5 ?: 0
                    ),direHeroes
                )
            } else {
                showCustomToast("Разберите героев", Toast.LENGTH_SHORT)
            }

        }

        val ClipcodesText = arrayOf(
            heroArray[0].heroName,
            heroArray[1].heroName,
            heroArray[2].heroName,
            heroArray[3].heroName,
            heroArray[4].heroName
        )
        val ClipcodesImage = arrayOf(
            heroArray[0].mipmap,
            heroArray[1].mipmap,
            heroArray[2].mipmap,
            heroArray[3].mipmap,
            heroArray[4].mipmap
        )

        val customAdapter = CustomAdapter(requireContext(), ClipcodesImage, ClipcodesText)
        spinner1.adapter = customAdapter
        spinner2.adapter = customAdapter
        spinner3.adapter = customAdapter
        spinner4.adapter = customAdapter
        spinner5.adapter = customAdapter
        spinner1.setSelection(0)
        spinner2.setSelection(1)
        spinner3.setSelection(2)
        spinner4.setSelection(3)
        spinner5.setSelection(4)

    }


    override fun onPlayerClick(position: Int, holder: RecyclerView.ViewHolder) {
        Log.w("asdasdas", "asdasdqq")
    }


}

