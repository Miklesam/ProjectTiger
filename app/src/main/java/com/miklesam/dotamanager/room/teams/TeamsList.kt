package com.miklesam.dotamanager.room.teams

import com.miklesam.dotamanager.datamodels.Team

class TeamsList {
    companion object {
        const val CATEGORY_IMAGE_DIR = "android.resource://com.miklesam.dotamanager/drawable/"
        val Navi =
            getNaviStructure()
        val Gambit =
            getGambitStructure()
        val VP =
            getVPStructure()
        val Secret=
            getSecretStructure()
        val ViciGaming=
            getViciStructure()
        val EG=
            getEGStructure()
        val Alliance=
            getAllianceStructure()
        val TNC=
            getTNCStructure()
        val Liquid=
            getLiquidStructure()
        val Nigma=
            getNigmaStructure()



        private fun getNaviStructure(): Team {
            return Team(
                "NaVi",
                CATEGORY_IMAGE_DIR + "navi_logo",
                "Navi Description",
                "Crystallize",
                "MagicaL",
                "9pasha",
                "CemaTheSlayer",
                "illias",
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }
        private fun getGambitStructure(): Team {
            return Team(
                "Gambit",
                CATEGORY_IMAGE_DIR + "gambit_esports",
                "Gambit Description",
                "Dream",
                "gpk",
                "Shachlo",
                "XSvamp1Re",
                "fng",
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }
        private fun getVPStructure(): Team {
            return Team(
                "Virtus Pro",
                CATEGORY_IMAGE_DIR + "vp",
                "VP Description",
                "Cooman",
                "No[o]ne",
                "Resolut1on",
                "Zayac",
                "Solo",
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }
        private fun getSecretStructure(): Team {
            return Team(
                "Team Secret",
                CATEGORY_IMAGE_DIR + "team_secret",
                "Secret Description",
                "Matumbaman",
                "Nisha",
                "Zai",
                "Yapzor",
                "Puppey",
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }
        private fun getViciStructure(): Team {
            return Team(
                "Vici Gaming",
                CATEGORY_IMAGE_DIR + "vici_gaming",
                "ViciGaming Description",
                "Paparazi",
                "Ori",
                "Yang",
                "Pyw",
                "Dy",
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }
        private fun getEGStructure(): Team {
            return Team(
                "Evil Geniuses",
                CATEGORY_IMAGE_DIR + "evil_giniuses",
                "EG Description",
                "Arteezy",
                "Abed",
                "Ramzes",
                "Cr1t",
                "Fly",
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }
        private fun getAllianceStructure(): Team {
            return Team(
                "Alliance",
                CATEGORY_IMAGE_DIR + "alliance",
                "Alliance Description",
                "Nikobaby",
                "Limp",
                "33",
                "Handsken",
                "Fata",
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }
        private fun getTNCStructure(): Team {
            return Team(
                "TNC",
                CATEGORY_IMAGE_DIR + "tnc_gaming",
                "TNC Description",
                "Gabbi",
                "Armel",
                "kpii",
                "Tims",
                "March",
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }
        private fun getLiquidStructure(): Team {
            return Team(
                "Liquid",
                CATEGORY_IMAGE_DIR + "team_liquid",
                "TNC Description",
                "miCKe",
                "qojqva",
                "Boxi",
                "Taiga",
                "iNSaNiA",
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }
        private fun getNigmaStructure(): Team {
            return Team(
                "Nigma",
                CATEGORY_IMAGE_DIR + "nigma_logo",
                "Nigma Description",
                "Miracle-",
                "w33",
                "MinD_ContRoL",
                "GH",
                "KuroKy",
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }


    }
}