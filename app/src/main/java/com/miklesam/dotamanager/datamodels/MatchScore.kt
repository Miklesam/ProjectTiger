package com.miklesam.dotamanager.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "closed_playoff_table")
data class MatchScore(
    @ColumnInfo(name = "topTeam")
    var topTeam: Int,
    @ColumnInfo(name = "bottomTeam")
    var bottomTeam: Int
){
    @PrimaryKey(autoGenerate = true)
    var id_key:Int=0
}