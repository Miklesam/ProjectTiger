package com.miklesam.dotamanager.room

import com.miklesam.dotamanager.R
import com.miklesam.dotamanager.datamodels.Team

class TeamsList {
    companion object {
        const val CATEGORY_IMAGE_DIR = "android.resource://com.miklesam.dotamanager/drawable/"
        val Navi = getNaviStructure()
        val Gambit = getGambitStructure()
        val VP = getVPStructure()





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
                "VP",
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
    }
}