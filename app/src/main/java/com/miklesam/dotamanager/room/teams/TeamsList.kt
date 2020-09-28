package com.miklesam.dotamanager.room.teams

import com.miklesam.dotamanager.datamodels.Team

class TeamsList {
    companion object {
        const val CATEGORY_IMAGE_DIR = "android.resource://com.miklesam.dotamanager/drawable/"
        val FantasticFive =
            getFantasticFiveStructure()
        val FRIENDS =
            getFriendsStructure()
        val IcCup =
            getIcCupStructure()
        val OldButGold =
            getOldButGoldStructure()
        val PowerRangers =
            getPowerRangersStructure()
        val RoxKis =
            getRoxKisStructure()
        val SFZ =
            getSFZStructure()
        val VegaSquadron =
            getVegaSquadronStructure()

        private fun getFantasticFiveStructure(): Team {
            return Team(
                "Fantastic Five",
                CATEGORY_IMAGE_DIR + "fantastic_five",
                "Fantastic Five Description",
                null,
                null,
                null,
                null,
                null,
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }

        private fun getFriendsStructure(): Team {
            return Team(
                "F.R.I.E.N.D.S",
                CATEGORY_IMAGE_DIR + "friends_logo",
                "F.R.I.E.N.D.S Description",
                null,
                null,
                null,
                null,
                null,
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }

        private fun getIcCupStructure(): Team {
            return Team(
                "iCCup",
                CATEGORY_IMAGE_DIR + "ic_cup_logo",
                "iCCupDescription",
                null,
                null,
                null,
                null,
                null,
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }

        private fun getOldButGoldStructure(): Team {
            return Team(
                "Old but Gold",
                CATEGORY_IMAGE_DIR + "old_but_gold_logo",
                "Old but Gold Description",
                null,
                null,
                null,
                null,
                null,
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }

        private fun getPowerRangersStructure(): Team {
            return Team(
                "Power Rangers",
                CATEGORY_IMAGE_DIR + "power_rangers_logo",
                "Power Rangers Description",
                null,
                null,
                null,
                null,
                null,
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }

        private fun getRoxKisStructure(): Team {
            return Team(
                "RoX",
                CATEGORY_IMAGE_DIR + "rox_kis_logo",
                "RoX Description",
                null,
                null,
                null,
                null,
                null,
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }

        private fun getSFZStructure(): Team {
            return Team(
                "ScaryFaceZ",
                CATEGORY_IMAGE_DIR + "scary_facez_logo",
                "ScaryFaceZ Description",
                null,
                null,
                null,
                null,
                null,
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }

        private fun getVegaSquadronStructure(): Team {
            return Team(
                "Vega Squadron",
                CATEGORY_IMAGE_DIR + "vega_logo",
                "Vega Squadron Description",
                null,
                null,
                null,
                null,
                null,
                CATEGORY_IMAGE_DIR + "titrof"
            )
        }


    }
}