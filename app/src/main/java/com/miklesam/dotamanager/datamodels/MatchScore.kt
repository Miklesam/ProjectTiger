package com.miklesam.dotamanager.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "closed_playoff_table")
data class MatchScore(
    @ColumnInfo(name = "topTeamName")
    var topTeamName: String,
    @ColumnInfo(name = "bottomTeamName")
    var bottomTeamName: String,
    @ColumnInfo(name = "topTeamLogo")
    var topTeamLogo: String,
    @ColumnInfo(name = "bottomTeamLogo")
    var bottomTeamLogo: String,
    @ColumnInfo(name = "topTeam")
    var topTeam: Int,
    @ColumnInfo(name = "bottomTeam")
    var bottomTeam: Int,
    @ColumnInfo(name = "playoffStage")
    var playoffStage: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id_key: Int = 0
}