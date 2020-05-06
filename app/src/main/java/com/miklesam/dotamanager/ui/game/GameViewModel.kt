package com.miklesam.dotamanager.ui.game

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miklesam.dotamanager.datamodels.HeroStats
import com.miklesam.dotamanager.datamodels.Player
import com.miklesam.dotamanager.datamodels.Side
import com.miklesam.dotamanager.ui.market.MarketRepository
import com.miklesam.dotamanager.utils.PrefsHelper
import kotlin.collections.ArrayList

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val allPlayersStats = MutableLiveData<List<String>>()
    private val allTowers = MutableLiveData<List<Boolean>>()
    fun getPlayersMatchStatistic(): LiveData<List<String>> = allPlayersStats
    fun getradiantTowers(): LiveData<List<Boolean>> = allTowers
    private var repository: MarketRepository = MarketRepository(application)

    val pos1: String
    val pos2: String
    val pos3: String
    val pos4: String
    val pos5: String

    init {
        pos1 = PrefsHelper.read(PrefsHelper.POSITION_1, "").toString()
        pos2 = PrefsHelper.read(PrefsHelper.POSITION_2, "").toString()
        pos3 = PrefsHelper.read(PrefsHelper.POSITION_3, "").toString()
        pos4 = PrefsHelper.read(PrefsHelper.POSITION_4, "").toString()
        pos5 = PrefsHelper.read(PrefsHelper.POSITION_5, "").toString()
    }

    val RadiantTeam = arrayListOf<HeroStats>(
        HeroStats(0, 0, 0, 1),
        HeroStats(0, 0, 0, 2),
        HeroStats(0, 0, 0, 3),
        HeroStats(0, 0, 0, 4),
        HeroStats(0, 0, 0, 5)
    )
    val DireTeam = arrayListOf<HeroStats>(
        HeroStats(0, 0, 0, 1),
        HeroStats(0, 0, 0, 2),
        HeroStats(0, 0, 0, 3),
        HeroStats(0, 0, 0, 4),
        HeroStats(0, 0, 0, 5)
    )


    val radiantTowers = Side(
        arrayListOf(true, true, true),
        arrayListOf(true, true, true),
        arrayListOf(true, true, true)
    )
    val direTowers = Side(
        arrayListOf(true, true, true),
        arrayListOf(true, true, true),
        arrayListOf(true, true, true)
    )

    fun getPlayer(): LiveData<List<Player>> {
        return repository.getPlayerByName(listOf(pos1, pos2, pos3, pos4, pos5))
    }


    private fun assignStats(): List<String> {
        val r1 = "${RadiantTeam[0].kills}/${RadiantTeam[0].death}/${RadiantTeam[0].assist}"
        val r2 = "${RadiantTeam[1].kills}/${RadiantTeam[1].death}/${RadiantTeam[1].assist}"
        val r3 = "${RadiantTeam[2].kills}/${RadiantTeam[2].death}/${RadiantTeam[2].assist}"
        val r4 = "${RadiantTeam[3].kills}/${RadiantTeam[3].death}/${RadiantTeam[3].assist}"
        val r5 = "${RadiantTeam[4].kills}/${RadiantTeam[4].death}/${RadiantTeam[4].assist}"

        val d1 = "${DireTeam[0].kills}/${DireTeam[0].death}/${DireTeam[0].assist}"
        val d2 = "${DireTeam[1].kills}/${DireTeam[1].death}/${DireTeam[1].assist}"
        val d3 = "${DireTeam[2].kills}/${DireTeam[2].death}/${DireTeam[2].assist}"
        val d4 = "${DireTeam[3].kills}/${DireTeam[3].death}/${DireTeam[3].assist}"
        val d5 = "${DireTeam[4].kills}/${DireTeam[4].death}/${DireTeam[4].assist}"

        val totalRadiantKills = RadiantTeam.map { it.kills }.sum().toString()
        val totalDireKills = DireTeam.map { it.kills }.sum().toString()
        return listOf(r1, r2, r3, r4, r5, d1, d2, d3, d4, d5, totalRadiantKills, totalDireKills)
    }

    fun calculateLineAssign(position: Array<Int>) {
        val arrayBottomRaddiant = ArrayList<HeroStats>()
        val arrayMidRaddiant = ArrayList<HeroStats>()
        val arrayTopRaddiant = ArrayList<HeroStats>()
        val arrayBottomDire = ArrayList<HeroStats>()
        val arrayMidDire = ArrayList<HeroStats>()
        val arrayTopDire = ArrayList<HeroStats>()
        for (i in 0 until 5) {
            when (position[i]) {
                0 -> arrayTopRaddiant.add(RadiantTeam[i])
                1 -> arrayMidRaddiant.add(RadiantTeam[i])
                2 -> arrayBottomRaddiant.add(RadiantTeam[i])
            }
            when (position[5 + i]) {
                3 -> arrayTopDire.add(DireTeam[i])
                4 -> arrayMidDire.add(DireTeam[i])
                5 -> arrayBottomDire.add(DireTeam[i])
            }


        }
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //calculateLineKills(arrayMidRaddiant,arrayMidDire)
            }

            var radiantTop = 0
            var radiantMid = 0
            var radiantBottom = 0

            var direTop = 0
            var direMid = 0
            var direBottom = 0


            override fun onFinish() {
                val timer = object : CountDownTimer(4000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        if (calculateLineKills(arrayMidRaddiant, arrayMidDire)) {
                            radiantMid++
                        } else {
                            direMid++
                        }
                        if (calculateLineKills(arrayTopRaddiant, arrayTopDire)) {
                            radiantTop++
                        } else {
                            direTop++
                        }
                        if (calculateLineKills(arrayBottomRaddiant, arrayBottomDire)) {
                            radiantBottom++
                        } else {
                            direBottom++
                        }
                    }

                    override fun onFinish() {
                        calculateLineTower(
                            radiantMid,
                            direMid,
                            radiantTowers.mid,
                            direTowers.mid,
                            radiantTowers,
                            direTowers
                        )
                        calculateLineTower(
                            radiantTop,
                            direTop,
                            radiantTowers.top,
                            direTowers.top,
                            radiantTowers,
                            direTowers
                        )
                        calculateLineTower(
                            radiantBottom,
                            direBottom,
                            radiantTowers.bot,
                            direTowers.bot,
                            radiantTowers,
                            direTowers
                        )
                    }
                }
                timer.start()
            }
        }
        timer.start()


    }


    private fun calculateLineKills(
        radiant: ArrayList<HeroStats>,
        dire: ArrayList<HeroStats>
    ): Boolean {
        var returningVal = false
        if (radiant.isNotEmpty() && dire.isNotEmpty()) {
            val rnds = (0 until (radiant.size + dire.size)).random()
            if (rnds > radiant.size - 1) {
                dire[(rnds - radiant.size)].kills++
                for (i in 0 until dire.size) {
                    if (i != rnds - radiant.size) {
                        dire[i].assist++
                    }
                }
                radiant[(0 until radiant.size).random()].death++
                returningVal = false
            } else {
                radiant[rnds].kills++
                for (i in 0 until radiant.size) {
                    if (i != rnds) {
                        radiant[i].assist++
                    }
                }
                dire[(0 until dire.size).random()].death++
                returningVal = true
            }
        }
        allPlayersStats.postValue(assignStats())

        return returningVal
    }

    private fun calculateLineTower(
        radiant: Int,
        dire: Int,
        radTowers: ArrayList<Boolean>,
        diresTower: ArrayList<Boolean>,
        r: Side,
        d: Side
    ) {
        Log.w("Snos =", "Radiant $radiant  Dire $dire")
        if (radiant > dire) {
            if (diresTower.isNotEmpty()) {
                diresTower.removeAt(diresTower.size - 1)
                d.updateAncient(true)
            } else {
                d.updateAncient(false)
            }

        } else {
            if (radTowers.isNotEmpty()) {
                radTowers.removeAt(radTowers.size - 1)
                r.updateAncient(true)
            } else {
                r.updateAncient(false)
            }

        }
        Log.w("Snos =", "radiantTowers $radiantTowers")
        Log.w("Snos =", "direTowers $direTowers")
        allTowers.postValue(calculateTowers())
    }

    private fun calculateTowers(): List<Boolean> {
        return listOf(
            radiantTowers.allBuilds[2],
            radiantTowers.allBuilds[1],
            radiantTowers.allBuilds[0],
            radiantTowers.allBuilds[3],
            radiantTowers.allBuilds[4],
            radiantTowers.allBuilds[5],
            radiantTowers.allBuilds[6],
            radiantTowers.allBuilds[7],
            radiantTowers.allBuilds[8],
            radiantTowers.allBuilds[9],
            direTowers.allBuilds[2],
            direTowers.allBuilds[1],
            direTowers.allBuilds[0],
            direTowers.allBuilds[3],
            direTowers.allBuilds[4],
            direTowers.allBuilds[5],
            direTowers.allBuilds[6],
            direTowers.allBuilds[7],
            direTowers.allBuilds[8],
            direTowers.allBuilds[9]
        )
    }
}
