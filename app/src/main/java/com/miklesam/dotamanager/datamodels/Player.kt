package com.miklesam.dotamanager.datamodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players_table")
data class Player(
    @ColumnInfo(name = "nickname")
    var nickname:String,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "position")
    var position:String,
    @ColumnInfo(name = "cost")
    var cost:String
)
{
    @PrimaryKey(autoGenerate = true)
    var id_key:Int=0
}