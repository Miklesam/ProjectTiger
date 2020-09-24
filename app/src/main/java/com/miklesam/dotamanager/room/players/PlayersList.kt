package com.miklesam.dotamanager.room.players

import com.miklesam.dotamanager.datamodels.Heroes
import com.miklesam.dotamanager.datamodels.Player

class PlayersList {

    companion object {
        private const val CATEGORY_IMAGE_DIR =
            "android.resource://com.miklesam.dotamanager/drawable/"
        val VP = listOf(
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Solo",
                "Alexey Berezin",
                "5",
                CATEGORY_IMAGE_DIR + "solo_face",
                Heroes.DISRUPTOR.id,
                Heroes.BANE.id,
                Heroes.SHADOW_DEMON.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "ALWAYSWANNAFLY",
                "Andrey Bondarenko",
                "5",
                CATEGORY_IMAGE_DIR + "alwayswannafly_face",
                Heroes.AA.id,
                Heroes.BANE.id,
                Heroes.DAZZLE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "kazahstan4020",
                "BLACKARXANGEL",
                "Vladislav Ivashchenko",
                "5",
                CATEGORY_IMAGE_DIR + "blackarxangel_face",
                Heroes.LICH.id,
                Heroes.BANE.id,
                Heroes.WITCH_DOCTOR.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "eine",
                "Albert Garaev",
                "5",
                CATEGORY_IMAGE_DIR + "eine_face",
                Heroes.EARTH_SHAKER.id,
                Heroes.DISRUPTOR.id,
                Heroes.UNDYING.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "belarus4020",
                "fng",
                "Artsiom Barshak",
                "5",
                CATEGORY_IMAGE_DIR + "fng_face",
                Heroes.BANE.id,
                Heroes.VENGEFUL.id,
                Heroes.TREANT.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Goblak",
                "Artur Kostenko",
                "5",
                CATEGORY_IMAGE_DIR + "goblak_face",
                Heroes.CHEN.id,
                Heroes.TREANT.id,
                Heroes.BANE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "illias",
                "Illias Ganeev",
                "5",
                CATEGORY_IMAGE_DIR + "illias_face",
                Heroes.CM.id,
                Heroes.UNDYING.id,
                Heroes.AA.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Miposhka",
                "Yaroslav Naidenov",
                "5",
                CATEGORY_IMAGE_DIR + "miposhka_faces",
                Heroes.DOOM.id,
                Heroes.MIRANA.id,
                Heroes.OGREMAGI.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Misha",
                "Mikhail Agatov",
                "5",
                CATEGORY_IMAGE_DIR + "misha_face",
                Heroes.BANE.id,
                Heroes.CHEN.id,
                Heroes.ORACLE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Nofear",
                "Alexander Churochkin",
                "5",
                CATEGORY_IMAGE_DIR + "nofear_face",
                Heroes.EARTH_SHAKER.id,
                Heroes.DAZZLE.id,
                Heroes.CHEN.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "NS",
                "Yaroslav Kuznetsov",
                "5",
                CATEGORY_IMAGE_DIR + "ns_face",
                Heroes.DISRUPTOR.id,
                Heroes.SHADOW_DEMON.id,
                Heroes.BANE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "PGG",
                "Vladimir Anosov",
                "5",
                CATEGORY_IMAGE_DIR + "pgg_face",
                Heroes.ENIGMA.id,
                Heroes.VENGEFUL.id,
                Heroes.TUSK.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "SoNNeikO",
                "Akbar Butaev",
                "5",
                CATEGORY_IMAGE_DIR + "sonneiko_face",
                Heroes.IO.id,
                Heroes.WYNTER_WYWERN.id,
                Heroes.ORACLE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "sQreen",
                "Khaled El-Khabbash",
                "5",
                CATEGORY_IMAGE_DIR + "sqreen_face",
                Heroes.TUSK.id,
                Heroes.NS.id,
                Heroes.SPIRIT_BREAKER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "VANSKOR",
                "Ivan Skorokhod",
                "5",
                CATEGORY_IMAGE_DIR + "vanscor_face",
                Heroes.RUBICK.id,
                Heroes.IO.id,
                Heroes.SHADOW_DEMON.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "yol",
                "Vladimir Basov",
                "5",
                CATEGORY_IMAGE_DIR + "yol_face",
                Heroes.DAZZLE.id,
                Heroes.BANE.id,
                Heroes.SHADOW_DEMON.id
            )
            ,
            Player(
                CATEGORY_IMAGE_DIR + "kirgistan4020",
                "Zayac",
                "Bakyt Emilzhanov",
                "4",
                CATEGORY_IMAGE_DIR + "zayc_face",
                Heroes.EARTH_SPIRIT.id,
                Heroes.TREANT.id,
                Heroes.NYX.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "kazahstan4020",
                "XSvamp1Re",
                "Danial Alibaev",
                "4",
                CATEGORY_IMAGE_DIR + "xsvampire_face",
                Heroes.TUSK.id,
                Heroes.EARTH_SPIRIT.id,
                Heroes.EARTH_SHAKER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "velheor",
                "Fedor Rusihin",
                "4",
                CATEGORY_IMAGE_DIR + "velheor_face",
                Heroes.TUSK.id,
                Heroes.RUBICK.id,
                Heroes.ELDER_TITAN.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "belarus4020",
                "so bad",
                "Vitaliy Oshmankevich",
                "4",
                CATEGORY_IMAGE_DIR + "so_bad_face",
                Heroes.DARK_WILLOW.id,
                Heroes.RUBICK.id,
                Heroes.SKY_MAG.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Sh4dowehhh",
                "Eugeniy Alekseev",
                "4",
                CATEGORY_IMAGE_DIR + "shadowehhh_face",
                Heroes.DISRUPTOR.id,
                Heroes.UNDYING.id,
                Heroes.ABADON.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "sayuw",
                "Oleg Kalenbet",
                "4",
                CATEGORY_IMAGE_DIR + "sayuw_face",
                Heroes.TUSK.id,
                Heroes.WINDRANGER.id,
                Heroes.EARTH_SPIRIT.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Save-",
                "Vitalie Melnic",
                "4",
                CATEGORY_IMAGE_DIR + "save_face",
                Heroes.TINY.id,
                Heroes.RUBICK.id,
                Heroes.SHADOW_DEMON.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "RodjER",
                "Vladimir Nikogosyan",
                "4",
                CATEGORY_IMAGE_DIR + "rofjer_face",
                Heroes.CHEN.id,
                Heroes.SAND_KING.id,
                Heroes.EARTH_SPIRIT.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Lil",
                "Ilya Ilyuk",
                "4",
                CATEGORY_IMAGE_DIR + "lil_face",
                Heroes.VISAGE.id,
                Heroes.SKY_MAG.id,
                Heroes.RUBICK.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "KingR",
                "Rinat Abdullin",
                "4",
                CATEGORY_IMAGE_DIR + "kingr_face",
                Heroes.RUBICK.id,
                Heroes.DISRUPTOR.id,
                Heroes.VENGEFUL.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Immersion",
                "Alexander Hmelevskoy",
                "4",
                CATEGORY_IMAGE_DIR + "immersion_face",
                Heroes.EARTH_SPIRIT.id,
                Heroes.NYX.id,
                Heroes.TUSK.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Chuvash",
                "Evgeniy Makarov",
                "4",
                CATEGORY_IMAGE_DIR + "chu_face",
                Heroes.EARTH_SPIRIT.id,
                Heroes.ELDER_TITAN.id,
                Heroes.RUBICK.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "CemaTheSlayer",
                "Semion Krivulya",
                "4",
                CATEGORY_IMAGE_DIR + "cema_face",
                Heroes.DISRUPTOR.id,
                Heroes.RUBICK.id,
                Heroes.DAZZLE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Bignum",
                "Danil Shehovtsov",
                "4",
                CATEGORY_IMAGE_DIR + "bignum_face",
                Heroes.NYX.id,
                Heroes.RUBICK.id,
                Heroes.TUSK.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "belarus4020",
                "Astral",
                "Genadiy Motuz",
                "4",
                CATEGORY_IMAGE_DIR + "astral_face",
                Heroes.DISRUPTOR.id,
                Heroes.EARTH_SPIRIT.id,
                Heroes.SHADOW_DEMON.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "ALOHADANCE",
                "Ilya Korobkin",
                "4",
                CATEGORY_IMAGE_DIR + "alohadance_face",
                Heroes.RUBICK.id,
                Heroes.IO.id,
                Heroes.TUSK.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "AfterLife",
                "Vasily Shishkin",
                "3",
                CATEGORY_IMAGE_DIR + "afterlife_face",
                Heroes.DARKSEER.id,
                Heroes.LC.id,
                Heroes.BATHRIDER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "belarus4020",
                "Stalcat",
                "Ilya Dorman",
                "3",
                CATEGORY_IMAGE_DIR + "stalcat_face",
                Heroes.DARKSEER.id,
                Heroes.LC.id,
                Heroes.BATHRIDER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "kazahstan4020",
                "Blizzy",
                "Evgeniy Ree",
                "3",
                CATEGORY_IMAGE_DIR + "blizzu_face",
                Heroes.BATHRIDER.id,
                Heroes.CENTAUR.id,
                Heroes.MAGNUS.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "633",
                "Stanislav Glushan",
                "3",
                CATEGORY_IMAGE_DIR + "bzz_face",
                Heroes.GYROCOPTER.id,
                Heroes.MIRANA.id,
                Heroes.PUCK.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "belarus4020",
                "chshrct",
                "Yevgeny Kostroma",
                "3",
                CATEGORY_IMAGE_DIR + "chrst_face",
                Heroes.BATHRIDER.id,
                Heroes.NATURES_PROPHET.id,
                Heroes.DARKSEER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "DkPhobos",
                "Alexander Kucheria",
                "3",
                CATEGORY_IMAGE_DIR + "dkphobos_face",
                Heroes.PUCK.id,
                Heroes.DARKSEER.id,
                Heroes.BREWMASTER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "DM",
                "Dmitry Dorokhin",
                "3",
                CATEGORY_IMAGE_DIR + "dm_face",
                Heroes.SAND_KING.id,
                Heroes.CENTAUR.id,
                Heroes.AXE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Funn1k",
                "Gleb Lipatnikov",
                "3",
                CATEGORY_IMAGE_DIR + "funnik_face",
                Heroes.BATHRIDER.id,
                Heroes.DARKSEER.id,
                Heroes.CLINKZ.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "GeneRaL",
                "Victor Nigrini",
                "3",
                CATEGORY_IMAGE_DIR + "general_face",
                Heroes.BATHRIDER.id,
                Heroes.BEASTMASTER.id,
                Heroes.CLOCKWERK.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Ghostik",
                "Andrey Kadyk",
                "3",
                CATEGORY_IMAGE_DIR + "ghostik_face",
                Heroes.BROODMOTHER.id,
                Heroes.LC.id,
                Heroes.BATHRIDER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "belarus4020",
                "LastHero",
                "Oleg Demidovich",
                "3",
                CATEGORY_IMAGE_DIR + "lasthero_face",
                Heroes.CENTAUR.id,
                Heroes.BEASTMASTER.id,
                Heroes.AXE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Mag",
                "Andrey Chipenko",
                "3",
                CATEGORY_IMAGE_DIR + "mag_face",
                Heroes.CLOCKWERK.id,
                Heroes.BROODMOTHER.id,
                Heroes.CENTAUR.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "9pasha",
                "Pavel Khvastunov",
                "3",
                CATEGORY_IMAGE_DIR + "pasha_face",
                Heroes.DARKSEER.id,
                Heroes.MAGNUS.id,
                Heroes.CENTAUR.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Resolut1on",
                "Roman Fominok",
                "3",
                CATEGORY_IMAGE_DIR + "resolution_face",
                Heroes.BEASTMASTER.id,
                Heroes.PUCK.id,
                Heroes.CENTAUR.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Shachlo",
                "Maxim Abramovskikh",
                "3",
                CATEGORY_IMAGE_DIR + "shachlo_face",
                Heroes.BATHRIDER.id,
                Heroes.AXE.id,
                Heroes.LONE_DRUID.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "XaKoH",
                "Evgeniy Kochetov",
                "3",
                CATEGORY_IMAGE_DIR + "xakoh_face",
                Heroes.CLOCKWERK.id,
                Heroes.BATHRIDER.id,
                Heroes.SLARDAR.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Afoninje",
                "Andrey Afonin",
                "2",
                CATEGORY_IMAGE_DIR + "afoninje_face",
                Heroes.SHADOW_FIEND.id,
                Heroes.PUCK.id,
                Heroes.DEATH_PROPHET.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Dendi",
                "Danil Ishutin",
                "2",
                CATEGORY_IMAGE_DIR + "dendi_face",
                Heroes.PUDGE.id,
                Heroes.MAGNUS.id,
                Heroes.INVOKER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "belarus4020",
                "Ergon",
                "Egor Kozlov",
                "2",
                CATEGORY_IMAGE_DIR + "ergon_face",
                Heroes.LONE_DRUID.id,
                Heroes.OD.id,
                Heroes.INVOKER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "fn",
                "Rostislav Lozovoi",
                "2",
                CATEGORY_IMAGE_DIR + "fn_face",
                Heroes.SHADOW_FIEND.id,
                Heroes.STORM_SPIRIT.id,
                Heroes.JUGGERNAUT.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "G",
                "Rostislav Lozovoi",
                "2",
                CATEGORY_IMAGE_DIR + "god_face",
                Heroes.QOP.id,
                Heroes.SHADOW_FIEND.id,
                Heroes.OD.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "gpk",
                "Danil Skutin",
                "2",
                CATEGORY_IMAGE_DIR + "gpk_face",
                Heroes.EMBER.id,
                Heroes.SHADOW_FIEND.id,
                Heroes.TEMPLAR_ASSASIN.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Iceberg",
                "Bogdan Vasylenko",
                "2",
                CATEGORY_IMAGE_DIR + "iceberg_face",
                Heroes.INVOKER.id,
                Heroes.ZEUS.id,
                Heroes.TINY.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "MagicaL",
                "Bogdan Vasylenko",
                "2",
                CATEGORY_IMAGE_DIR + "magical_face",
                Heroes.TEMPLAR_ASSASIN.id,
                Heroes.RAZOR.id,
                Heroes.TINKER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "mellojul",
                "Maxim Pnyov",
                "2",
                CATEGORY_IMAGE_DIR + "mellojul_face",
                Heroes.MONKEY_KING.id,
                Heroes.EMBER.id,
                Heroes.LINA.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Nix",
                "Alexander Levin",
                "2",
                CATEGORY_IMAGE_DIR + "nix_face",
                Heroes.ANTIMAGE.id,
                Heroes.JUGGERNAUT.id,
                Heroes.GYROCOPTER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "No[o]ne",
                "Vladimir Minenko",
                "2",
                CATEGORY_IMAGE_DIR + "noone_face",
                Heroes.DK.id,
                Heroes.INVOKER.id,
                Heroes.STORM_SPIRIT.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Pikachu",
                "Yaroslav Vasylenko",
                "2",
                CATEGORY_IMAGE_DIR + "pickachu_face",
                Heroes.INVOKER.id,
                Heroes.OD.id,
                Heroes.DEATH_PROPHET.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "belarus4020",
                "Sunlight",
                "Kirill Kachinsky",
                "2",
                CATEGORY_IMAGE_DIR + "sunlight_face",
                Heroes.STORM_SPIRIT.id,
                Heroes.QOP.id,
                Heroes.ZEUS.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "UnderShock",
                "Dmitry Bolshakov",
                "2",
                CATEGORY_IMAGE_DIR + "undershock_face",
                Heroes.PUCK.id,
                Heroes.STORM_SPIRIT.id,
                Heroes.LINA.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "xannii",
                "Arslan Shadjanov",
                "2",
                CATEGORY_IMAGE_DIR + "xannii_face",
                Heroes.ALCHEMIC.id,
                Heroes.EMBER.id,
                Heroes.TEMPLAR_ASSASIN.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "belarus4020",
                "young G",
                "Nikita Bochko",
                "2",
                CATEGORY_IMAGE_DIR + "young_g_face",
                Heroes.SHADOW_FIEND.id,
                Heroes.INVOKER.id,
                Heroes.QOP.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Chappie",
                "Vladimir Kuzmenko",
                "1",
                CATEGORY_IMAGE_DIR + "chappie_face",
                Heroes.PA.id,
                Heroes.MEEPO.id,
                Heroes.JUGGERNAUT.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Cooman",
                "Zaur Shakhmurzaev",
                "1",
                CATEGORY_IMAGE_DIR + "cooman_face",
                Heroes.ANTIMAGE.id,
                Heroes.TROLL_WARLORD.id,
                Heroes.JUGGERNAUT.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "Crystallize",
                "Vladyslav Krystanek",
                "1",
                CATEGORY_IMAGE_DIR + "cristallyze_face",
                Heroes.LIFESTEALER.id,
                Heroes.FACELESS_VOID.id,
                Heroes.SVEN.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Daxak",
                "Nikita Kuzmin",
                "1",
                CATEGORY_IMAGE_DIR + "daxak_face",
                Heroes.WIVER.id,
                Heroes.URSA.id,
                Heroes.MEDUSA.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "kirgistan4020",
                "dream",
                "Kiyalbek Tayirov",
                "1",
                CATEGORY_IMAGE_DIR + "dream_face",
                Heroes.JUGGERNAUT.id,
                Heroes.TERROBLADE.id,
                Heroes.MORPHLING.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "DyrachYO",
                "Anton Shkredov",
                "1",
                CATEGORY_IMAGE_DIR + "durach_yo_face",
                Heroes.LIFESTEALER.id,
                Heroes.SLARK.id,
                Heroes.FACELESS_VOID.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "epileptick1d",
                "Egor Grigorenko",
                "1",
                CATEGORY_IMAGE_DIR + "epilept1ckid_face",
                Heroes.PHANTOM_LANCER.id,
                Heroes.GYROCOPTER.id,
                Heroes.EMBER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Illidan",
                "Ilya Pivcaev",
                "1",
                CATEGORY_IMAGE_DIR + "illidan_face",
                Heroes.CHAOS.id,
                Heroes.SLARK.id,
                Heroes.MORPHLING.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "iLTW",
                "Igor Filatov",
                "1",
                CATEGORY_IMAGE_DIR + "iltw_face",
                Heroes.GYROCOPTER.id,
                Heroes.JUGGERNAUT.id,
                Heroes.TERROBLADE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "kazahstan4020",
                "Naive-",
                "Aybek Tokaev",
                "1",
                CATEGORY_IMAGE_DIR + "naive_face",
                Heroes.JUGGERNAUT.id,
                Heroes.TERROBLADE.id,
                Heroes.PA.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "belarus4020",
                "Palantimos",
                "Nikita Grinkevich",
                "1",
                CATEGORY_IMAGE_DIR + "palantinos_face",
                Heroes.LIFESTEALER.id,
                Heroes.WK.id,
                Heroes.PHANTOM_LANCER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "RAMZES666",
                "Roman Kushnarev",
                "1",
                CATEGORY_IMAGE_DIR + "ramzes_face",
                Heroes.TIMBERSAW.id,
                Heroes.OMNIKNIGHT.id,
                Heroes.BEASTMASTER.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Sedoy",
                "Vadim Musorin",
                "1",
                CATEGORY_IMAGE_DIR + "sedoy_face",
                Heroes.JUGGERNAUT.id,
                Heroes.WIVER.id,
                Heroes.SVEN.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "V-Tune",
                "Alik Vorobey",
                "1",
                CATEGORY_IMAGE_DIR + "v_tune_face",
                Heroes.WK.id,
                Heroes.SLARK.id,
                Heroes.ANTIMAGE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "ukraine4020",
                "XBOCT",
                "Oleksandr Dashkevych",
                "1",
                CATEGORY_IMAGE_DIR + "xbost_face",
                Heroes.LIFESTEALER.id,
                Heroes.GYROCOPTER.id,
                Heroes.ANTIMAGE.id
            ),
            Player(
                CATEGORY_IMAGE_DIR + "russia4020",
                "Zitraks",
                "Alexey Ischenko",
                "1",
                CATEGORY_IMAGE_DIR + "zitraks_face",
                Heroes.SPECTRE.id,
                Heroes.TERROBLADE.id,
                Heroes.SVEN.id
            )
        )
    }
}
