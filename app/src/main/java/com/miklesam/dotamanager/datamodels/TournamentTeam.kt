package com.miklesam.dotamanager.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "closed_qualifid_table")
data class TournamentTeam(var TeamName: String,var logo:String ,var win: Int, var lose: Int){
    @PrimaryKey(autoGenerate = true)
    var id_key:Int=0
}