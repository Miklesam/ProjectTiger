package com.miklesam.dotamanager.ui.pickstage

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Heroes
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.utils.showCustomToast
import kotlinx.android.synthetic.main.pick_stage.*


class PickStage : Fragment(R.layout.pick_stage) {
    var Heros_icon =
        arrayOfNulls<ImageView>(117)
    val Pick_stage =
        arrayOfNulls<ImageView>(22)
    var arrayHero: MutableList<Heroes>? = null
    var block = false
    var pick_state = 0
    var timer: CountDownTimer? = null
    val radiantPicks: ArrayList<Int> = ArrayList()
    val direPicks: ArrayList<Int> = ArrayList()
    var player: MediaPlayer? = null
    var soundPull: SoundPool?= null
    var soundOne: Int = 0

    interface nextFromPick {
        fun pickEnded(
            radiant: ArrayList<Int>,
            direPicks: ArrayList<Int>
        )
    }

    override fun onPause() {
        Log.w("Pick"," Freagment Pick Pause")
        super.onPause()
        player?.pause()
    }

    override fun onStop() {
        super.onStop()
        Log.w("Pick","Freagment PickStop")
        player?.stop()
        player?.release()
        player=null
        timer?.cancel()
        timer=null
        soundPull?.stop(soundOne)
        soundPull=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val audioAtributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPull = SoundPool.Builder()
            .setMaxStreams(6)
            .setAudioAttributes(audioAtributes)
            .build()
        soundOne = soundPull!!.load(context, R.raw.your_turn_to_pick, 1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val endedListener = activity as nextFromPick
        arrayHero = Heroes.values().toMutableList()
        initViews()
        val enemy=  PrefsHelper.read(PrefsHelper.ENEMY_NAME,"")
        EnemyteamName.text=enemy
        player = MediaPlayer.create(context, R.raw.pick_music)
        player?.setOnCompletionListener { player?.start() }
        player?.start()

        Yourteam.text = PrefsHelper.read(PrefsHelper.TEAM_NAME, "")
        Plan_state.setOnClickListener { endedListener.pickEnded(radiantPicks,direPicks) }
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeleft.text = ("" + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                if (pick_state != 22) {
                    block = true
                    randomPlayerPick()
                }
            }
        }
        timer?.start()

        for (i in 0 until 117) {
            Heros_icon[i]!!.setOnClickListener {
                if (!block) {
                    block = true
                    if (arrayHero!!.contains(Heroes.values().find { it.id == i })) {
                        Heros_icon[i]!!.setImageResource(
                            Heroes.values().find { it.id == i }!!.largeBan
                        )
                        val chooseHero = Heroes.values().find { it.id == i }
                        if (pick_state == 8 || pick_state == 11 || pick_state == 15 || pick_state == 17 || pick_state == 20) {
                            radiantPicks.add(chooseHero!!.id)
                            Pick_stage[pick_state]?.setImageResource(chooseHero!!.image_pick)
                        } else {
                            Pick_stage[pick_state]?.setImageResource(chooseHero!!.minBan)
                            block = false
                        }

                        arrayHero!!.remove(chooseHero)
                        pick_state++
                        if (pick_state != 12 && pick_state != 20) {
                            randomComputerPick()
                        } else {
                            block = false
                        }
                        //timer?.cancel()
                    } else {
                        showCustomToast("Забанен", Toast.LENGTH_SHORT)
                        block = false
                    }
                }


            }

        }
        Help.text = "Ваша очередь"
        callYourPick()
    }

    private fun initViews() {
        Heros_icon[0] = Abadon
        Heros_icon[1] = Alcemic
        Heros_icon[2] = Axe
        Heros_icon[3] = Beastmaster
        Heros_icon[4] = Brewmaster
        Heros_icon[5] = Bristleback
        Heros_icon[6] = Centaur
        Heros_icon[7] = Chaos
        Heros_icon[8] = Clockwerk
        Heros_icon[9] = Doom


        Heros_icon[10] = DK
        Heros_icon[11] = EarthSpirit
        Heros_icon[12] = EarthShaker
        Heros_icon[13] = ElderTitan
        Heros_icon[14] = Huskar
        Heros_icon[15] = Io
        Heros_icon[16] = Kunnka
        Heros_icon[17] = LC
        Heros_icon[18] = Lifestealer
        Heros_icon[19] = Lycan



        Heros_icon[20] = Magnus
        Heros_icon[21] = Mars

        Heros_icon[22] = NS
        Heros_icon[23] = Omniknight
        Heros_icon[24] = Phoenix
        Heros_icon[25] = Pudge
        Heros_icon[26] = SandKing
        Heros_icon[27] = Slardar
        Heros_icon[28] = SpiritBreaker
        Heros_icon[29] = Sven
        Heros_icon[30] = Tidehunter

        Heros_icon[31] = Timbersaw
        Heros_icon[32] = Tiny
        Heros_icon[33] = Treant
        Heros_icon[34] = Tusk
        Heros_icon[35] = Underlord
        Heros_icon[36] = Undying
        Heros_icon[37] = WraithKing

        Heros_icon[38] = AntiMag
        Heros_icon[39] = ArcWarden
        Heros_icon[40] = Bloodweeker
        Heros_icon[41] = BountyHunter
        Heros_icon[42] = Broodmother
        Heros_icon[43] = Clinkz
        Heros_icon[44] = Drow
        Heros_icon[45] = Ember
        Heros_icon[46] = FacelessVoid
        Heros_icon[47] = Gyrocopter

        Heros_icon[48] = Juggernaut
        Heros_icon[49] = LoneDruid
        Heros_icon[50] = Luna
        Heros_icon[51] = Medusa
        Heros_icon[52] = Meepo
        Heros_icon[53] = Mirana
        Heros_icon[54] = MonkeyKing
        Heros_icon[55] = Morpling
        Heros_icon[56] = Naga
        Heros_icon[57] = Nyx
        Heros_icon[58] = Pangolier
        Heros_icon[59] = PhantomAssasin



        Heros_icon[60] = PhantomLancer
        Heros_icon[61] = Razor
        Heros_icon[62] = Riki
        Heros_icon[63] = ShadowFiend
        Heros_icon[64] = Slark
        Heros_icon[65] = Sniper
        Heros_icon[66] = Spectre
        Heros_icon[67] = TemplarAssasin
        Heros_icon[68] = Terroblade
        Heros_icon[69] = TrollWarlord
        Heros_icon[70] = Ursa
        Heros_icon[71] = VengefulSpirit


        Heros_icon[72] = Venomancer
        Heros_icon[73] = Viper
        Heros_icon[74] = Wiver
        Heros_icon[75] = AA
        Heros_icon[76] = Bane
        Heros_icon[77] = Batrider
        Heros_icon[78] = Chen
        Heros_icon[79] = CM
        Heros_icon[80] = Darkseer
        Heros_icon[81] = DarkWillow
        Heros_icon[82] = Dazzle
        Heros_icon[83] = DeathProphet

        Heros_icon[84] = Disruptor
        Heros_icon[85] = Enchantress
        Heros_icon[86] = Enigma
        Heros_icon[87] = Grimstroke
        Heros_icon[88] = Invoker
        Heros_icon[89] = Jakiro
        Heros_icon[90] = KeeperoftheLight
        Heros_icon[91] = Lechrak
        Heros_icon[92] = Lich
        Heros_icon[93] = Lina
        Heros_icon[94] = Lion
        Heros_icon[95] = NaturesPhrophet


        Heros_icon[96] = Necrophos
        Heros_icon[97] = OgreMagi
        Heros_icon[98] = Oracle
        Heros_icon[99] = OD
        Heros_icon[100] = Puck
        Heros_icon[101] = Pugna
        Heros_icon[102] = Qop
        Heros_icon[103] = Rubick
        Heros_icon[104] = ShadowDemon
        Heros_icon[105] = ShadowShaman
        Heros_icon[106] = Silencer
        Heros_icon[107] = Skymag


        Heros_icon[108] = StormSpirit
        Heros_icon[109] = Techies
        Heros_icon[110] = Tinker
        Heros_icon[111] = Visage
        Heros_icon[112] = Warlock
        Heros_icon[113] = Windranger
        Heros_icon[114] = WW
        Heros_icon[115] = WichDoctor
        Heros_icon[116] = Zeus


        Heros_icon[0]!!.setImageResource(Heroes.ABADON.icon)
        Heros_icon[1]!!.setImageResource(Heroes.ALCHEMIC.icon)
        Heros_icon[2]!!.setImageResource(Heroes.AXE.icon)
        Heros_icon[3]!!.setImageResource(Heroes.BEASTMASTER.icon)
        Heros_icon[4]!!.setImageResource(Heroes.BREWMASTER.icon)
        Heros_icon[5]!!.setImageResource(Heroes.BRISTLEBACK.icon)
        Heros_icon[6]!!.setImageResource(Heroes.CENTAUR.icon)
        Heros_icon[7]!!.setImageResource(Heroes.CHAOS.icon)
        Heros_icon[8]!!.setImageResource(Heroes.CLOCKWERK.icon)
        Heros_icon[9]!!.setImageResource(Heroes.DOOM.icon)

        Heros_icon[10]!!.setImageResource(Heroes.DK.icon)
        Heros_icon[11]!!.setImageResource(Heroes.EARTH_SPIRIT.icon)
        Heros_icon[12]!!.setImageResource(Heroes.EARTH_SHAKER.icon)
        Heros_icon[13]!!.setImageResource(Heroes.ELDER_TITAN.icon)
        Heros_icon[14]!!.setImageResource(Heroes.HUSKAR.icon)
        Heros_icon[15]!!.setImageResource(Heroes.IO.icon)
        Heros_icon[16]!!.setImageResource(Heroes.KUNNKA.icon)
        Heros_icon[17]!!.setImageResource(Heroes.LC.icon)
        Heros_icon[18]!!.setImageResource(Heroes.LIFESTEALER.icon)
        Heros_icon[19]!!.setImageResource(Heroes.LYCAN.icon)



        Heros_icon[20]!!.setImageResource(Heroes.MAGNUS.icon)
        Heros_icon[21]!!.setImageResource(Heroes.MARS.icon)
        Heros_icon[22]!!.setImageResource(Heroes.NS.icon)
        Heros_icon[23]!!.setImageResource(Heroes.OMNIKNIGHT.icon)
        Heros_icon[24]!!.setImageResource(Heroes.PHOENIX.icon)
        Heros_icon[25]!!.setImageResource(Heroes.PUDGE.icon)
        Heros_icon[26]!!.setImageResource(Heroes.SAND_KING.icon)
        Heros_icon[27]!!.setImageResource(Heroes.SLARDAR.icon)
        Heros_icon[28]!!.setImageResource(Heroes.SPIRIT_BREAKER.icon)
        Heros_icon[29]!!.setImageResource(Heroes.SVEN.icon)
        Heros_icon[30]!!.setImageResource(Heroes.TIDEHUNTER.icon)


        Heros_icon[31]!!.setImageResource(Heroes.TIMBERSAW.icon)
        Heros_icon[32]!!.setImageResource(Heroes.TINY.icon)
        Heros_icon[33]!!.setImageResource(Heroes.TREANT.icon)
        Heros_icon[34]!!.setImageResource(Heroes.TUSK.icon)
        Heros_icon[35]!!.setImageResource(Heroes.UNDERLORD.icon)
        Heros_icon[36]!!.setImageResource(Heroes.UNDYING.icon)
        Heros_icon[37]!!.setImageResource(Heroes.WK.icon)


        Heros_icon[38]!!.setImageResource(Heroes.ANTIMAGE.icon)
        Heros_icon[39]!!.setImageResource(Heroes.ARC_WARDEN.icon)
        Heros_icon[40]!!.setImageResource(Heroes.BLOODSEEKER.icon)
        Heros_icon[41]!!.setImageResource(Heroes.BOUNTY.icon)
        Heros_icon[42]!!.setImageResource(Heroes.BROODMOTHER.icon)
        Heros_icon[43]!!.setImageResource(Heroes.CLINKZ.icon)
        Heros_icon[44]!!.setImageResource(Heroes.DROW.icon)
        Heros_icon[45]!!.setImageResource(Heroes.EMBER.icon)
        Heros_icon[46]!!.setImageResource(Heroes.FACELESS_VOID.icon)
        Heros_icon[47]!!.setImageResource(Heroes.GYROCOPTER.icon)


        Heros_icon[48]!!.setImageResource(Heroes.JUGGERNAUT.icon)
        Heros_icon[49]!!.setImageResource(Heroes.LONE_DRUID.icon)
        Heros_icon[50]!!.setImageResource(Heroes.LUNA.icon)
        Heros_icon[51]!!.setImageResource(Heroes.MEDUSA.icon)
        Heros_icon[52]!!.setImageResource(Heroes.MEEPO.icon)
        Heros_icon[53]!!.setImageResource(Heroes.MIRANA.icon)
        Heros_icon[54]!!.setImageResource(Heroes.MONKEY_KING.icon)
        Heros_icon[55]!!.setImageResource(Heroes.MORPHLING.icon)
        Heros_icon[56]!!.setImageResource(Heroes.NAGA.icon)
        Heros_icon[57]!!.setImageResource(Heroes.NYX.icon)
        Heros_icon[58]!!.setImageResource(Heroes.PANGOLIER.icon)
        Heros_icon[59]!!.setImageResource(Heroes.PA.icon)


        Heros_icon[60]!!.setImageResource(Heroes.PHANTOM_LANCER.icon)
        Heros_icon[61]!!.setImageResource(Heroes.RAZOR.icon)
        Heros_icon[62]!!.setImageResource(Heroes.RIKI.icon)
        Heros_icon[63]!!.setImageResource(Heroes.SHADOW_FIEND.icon)
        Heros_icon[64]!!.setImageResource(Heroes.SLARK.icon)
        Heros_icon[65]!!.setImageResource(Heroes.SNIPER.icon)
        Heros_icon[66]!!.setImageResource(Heroes.SPECTRE.icon)
        Heros_icon[67]!!.setImageResource(Heroes.TEMPLAR_ASSASIN.icon)
        Heros_icon[68]!!.setImageResource(Heroes.TERROBLADE.icon)
        Heros_icon[69]!!.setImageResource(Heroes.TROLL_WARLORD.icon)
        Heros_icon[70]!!.setImageResource(Heroes.URSA.icon)
        Heros_icon[71]!!.setImageResource(Heroes.VENOMANCER.icon)


        Heros_icon[72]!!.setImageResource(Heroes.VENGEFUL.icon)
        Heros_icon[73]!!.setImageResource(Heroes.VIPER.icon)
        Heros_icon[74]!!.setImageResource(Heroes.WIVER.icon)
        Heros_icon[75]!!.setImageResource(Heroes.AA.icon)
        Heros_icon[76]!!.setImageResource(Heroes.BANE.icon)
        Heros_icon[77]!!.setImageResource(Heroes.BATHRIDER.icon)
        Heros_icon[78]!!.setImageResource(Heroes.CHEN.icon)
        Heros_icon[79]!!.setImageResource(Heroes.CM.icon)
        Heros_icon[80]!!.setImageResource(Heroes.DARKSEER.icon)
        Heros_icon[81]!!.setImageResource(Heroes.DARK_WILLOW.icon)
        Heros_icon[82]!!.setImageResource(Heroes.DAZZLE.icon)
        Heros_icon[83]!!.setImageResource(Heroes.DEATH_PROPHET.icon)


        Heros_icon[84]!!.setImageResource(Heroes.DISRUPTOR.icon)
        Heros_icon[85]!!.setImageResource(Heroes.ENCHANTRESS.icon)
        Heros_icon[86]!!.setImageResource(Heroes.ENIGMA.icon)
        Heros_icon[87]!!.setImageResource(Heroes.GRIMSTROKE.icon)
        Heros_icon[88]!!.setImageResource(Heroes.INVOKER.icon)
        Heros_icon[89]!!.setImageResource(Heroes.JAKIRO.icon)
        Heros_icon[90]!!.setImageResource(Heroes.KEEPER_OF_THE_LIGHT.icon)
        Heros_icon[91]!!.setImageResource(Heroes.LESHRAC.icon)
        Heros_icon[92]!!.setImageResource(Heroes.LICH.icon)
        Heros_icon[93]!!.setImageResource(Heroes.LINA.icon)
        Heros_icon[94]!!.setImageResource(Heroes.LION.icon)
        Heros_icon[95]!!.setImageResource(Heroes.NATURES_PROPHET.icon)


        Heros_icon[96]!!.setImageResource(Heroes.NECROPHOS.icon)
        Heros_icon[97]!!.setImageResource(Heroes.OGREMAGI.icon)
        Heros_icon[98]!!.setImageResource(Heroes.ORACLE.icon)
        Heros_icon[99]!!.setImageResource(Heroes.OD.icon)
        Heros_icon[100]!!.setImageResource(Heroes.PUCK.icon)
        Heros_icon[101]!!.setImageResource(Heroes.PUGNA.icon)
        Heros_icon[102]!!.setImageResource(Heroes.QOP.icon)
        Heros_icon[103]!!.setImageResource(Heroes.RUBICK.icon)
        Heros_icon[104]!!.setImageResource(Heroes.SHADOW_DEMON.icon)
        Heros_icon[105]!!.setImageResource(Heroes.SHADOW_SHAMAN.icon)
        Heros_icon[106]!!.setImageResource(Heroes.SILENCER.icon)
        Heros_icon[107]!!.setImageResource(Heroes.SKY_MAG.icon)



        Heros_icon[108]!!.setImageResource(Heroes.STORM_SPIRIT.icon)
        Heros_icon[109]!!.setImageResource(Heroes.TECHIES.icon)
        Heros_icon[110]!!.setImageResource(Heroes.TINKER.icon)
        Heros_icon[111]!!.setImageResource(Heroes.VISAGE.icon)
        Heros_icon[112]!!.setImageResource(Heroes.WARLOCK.icon)
        Heros_icon[113]!!.setImageResource(Heroes.WINDRANGER.icon)
        Heros_icon[114]!!.setImageResource(Heroes.WYNTER_WYWERN.icon)
        Heros_icon[115]!!.setImageResource(Heroes.WITCH_DOCTOR.icon)
        Heros_icon[116]!!.setImageResource(Heroes.ZEUS.icon)

        Pick_stage[0] = ban1
        Pick_stage[1] = ban2
        Pick_stage[2] = ban3
        Pick_stage[3] = ban4
        Pick_stage[4] = ban5
        Pick_stage[5] = ban6
        Pick_stage[6] = ban7
        Pick_stage[7] = ban8
        Pick_stage[8] = pick1
        Pick_stage[9] = pick2
        Pick_stage[10] = pick3
        Pick_stage[11] = pick4
        Pick_stage[12] = ban9
        Pick_stage[13] = ban10
        Pick_stage[14] = pick5
        Pick_stage[15] = pick6
        Pick_stage[16] = pick7
        Pick_stage[17] = pick8
        Pick_stage[18] = ban12
        Pick_stage[19] = ban11
        Pick_stage[20] = pick9
        Pick_stage[21] = pick10
    }

    private fun callYourPick(){
       val timer2 = object : CountDownTimer(500, 100) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                soundPull?.play(soundOne, 1F, 1F, 0, 0, 1F)
            }
        }
        timer2.start()
    }


    private fun randomComputerPick() {
        val rnds = (0 until arrayHero!!.size).random()
        val what = arrayHero!![rnds]
        Heros_icon[what.id]?.setImageResource(what!!.largeBan)
        if (pick_state == 9 || pick_state == 10 || pick_state == 14 || pick_state == 16 || pick_state == 21) {
            direPicks.add(what.id)
            Pick_stage[pick_state]?.setImageResource(what!!.image_pick)
        } else {
            Pick_stage[pick_state]?.setImageResource(what!!.minBan)
        }
        arrayHero!!.remove(what)
        pick_state++
        if (pick_state == 10 || pick_state == 14) {
            randomComputerPick()
        }
        soundPull?.play(soundOne, 1F, 1F, 0, 0, 1F)
        timer!!.start()
        block = false
        if (pick_state == 22) {
            timer?.cancel()
            Plan_state.visibility = VISIBLE
            block = true
        }
    }

    private fun randomPlayerPick() {
        val rnds = (0 until arrayHero!!.size).random()
        val what = arrayHero!![rnds]
        Heros_icon[what.id]?.setImageResource(what!!.largeBan)
        if (pick_state == 8 || pick_state == 11 || pick_state == 15 || pick_state == 17 || pick_state == 20) {
            Pick_stage[pick_state]?.setImageResource(what!!.image_pick)
            radiantPicks.add(what!!.id)
        } else {
            Pick_stage[pick_state]?.setImageResource(what!!.minBan)
        }
        pick_state++
        arrayHero!!.remove(what)
        block = false
        if (pick_state != 12 && pick_state != 20) {
            randomComputerPick()
        } else {
            timer?.start()
        }
    }

}