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
            )
        )
    }
}
