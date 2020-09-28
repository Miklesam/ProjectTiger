package com.miklesam.dotamanager.simplefragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.adapters.TeamGenerateAdapter
import com.miklesam.dotamanager.ui.choosePlayers.ChoosenPlayersViewModel
import kotlinx.android.synthetic.main.fragment_description.*

class FragmentDescription : Fragment(R.layout.fragment_description) {

    private val descViewModel by viewModels<DescriptionViewModel>()

    interface nextListener {
        fun nextClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val next = activity as nextListener
        nextBttn.setOnClickListener {
            descViewModel.writePlayers()
            next.nextClicked()
        }

        recycler_generate.layoutManager = GridLayoutManager(requireContext(), 2)
        val adapter = TeamGenerateAdapter()
        recycler_generate.adapter = adapter


        descViewModel.getAllPlayers().observe(viewLifecycleOwner, Observer {
            if (it != null && it.size == 80) {
                descViewModel.generateTeams()
            }
        })

        descViewModel.isGenerating().observe(viewLifecycleOwner, Observer {
            if (it == true) {
                pb.visibility = View.VISIBLE
                nextBttn.visibility = View.GONE
                recycler_generate.visibility = View.GONE
            } else {
                pb.visibility = View.GONE
                nextBttn.visibility = View.VISIBLE
                recycler_generate.visibility = View.VISIBLE
            }
        })

        descViewModel.getTeams().observe(viewLifecycleOwner, Observer {
            adapter.setTeams(it)
        })


/*
        longText.text="Деньги можно получать за призовые места на турнирах\n"+
                "Также деньги можно получить в магазине.\n"+
                "Деньги можно тратить на покупку игроков.\n"+
                "Продавать игроков можно за пол цены.\n"+
                "Каждый день кроме соревновательных можно проводить тренировки, они могут быть как командные, так и индивидуальные.\n"+
                "Тренироваться и учавствовать в соревнованиях можно только в полном составе. Если игрок \n"+
                "во время тндивидуальных занятий стримит, идет прирост фанатов, в других случаях идет прирост XP, так же как и при командных тренировках.\n"+
                "Заработанную XP можно тратить на улучшение характеристик определенного игрока:\n"+
                "1.Laining\n"+
                "2.Farm\n"+
                "3.Fighting\n"+
                "4.LateGame\n"+
                "Эти характеристики влияют на расчет боя.\n"+
                "Расчет сражения делится на 3 этапа:\n"+
                "1.Стадия лайнинга\n"+
                "2.Миддлегейм\n"+
                "3.Лейтгейм\n"+
                "После чего выводится победитель.\n"+
                "Вы можете создавать своих игроков, а так же изменять имена существующих\n"+
                "Пожелания, предложения а также жалобы, пожалуйста, пишите на мою почту:\n"+
                "mikle.samarkin@gmail.com"

 */
    }
}