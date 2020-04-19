package com.miklesam.dotamanager.simplefragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import kotlinx.android.synthetic.main.fragment_description.*

class FragmentDescription :Fragment(R.layout.fragment_description){
    @SuppressLint("SetTextI18n")

    interface nextListener {
        fun nextClicked()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val next=activity as nextListener
        nextBttn.setOnClickListener { next.nextClicked() }

        longText.text="Здесь будет описание игры"
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