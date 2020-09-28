package com.miklesam.dotamanager.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams_table")
data class Team(
    @ColumnInfo(name = "teamName")
    var teamName: String,
    @ColumnInfo(name = "teamLogo")
    var teamLogo: String,
    @ColumnInfo(name = "teamDescription")
    var teamDescription: String?,
    @ColumnInfo(name = "playerPosition1")
    var playerPosition1: String?,
    @ColumnInfo(name = "playerPosition2")
    var playerPosition2: String?,
    @ColumnInfo(name = "playerPosition3")
    var playerPosition3: String?,
    @ColumnInfo(name = "playerPosition4")
    var playerPosition4: String?,
    @ColumnInfo(name = "playerPosition5")
    var playerPosition5: String?,
    @ColumnInfo(name = "teamTrophy")
    var teamTrophy: String
) {
    @PrimaryKey(autoGenerate = true)
    var id_key: Int = 0
}