package com.miklesam.dotamanager.datamodels

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players_main_table")
data class Player(
    @ColumnInfo(name = "flag")
    var flag: String,
    @ColumnInfo(name = "nickname")
    var nickname: String,
    @ColumnInfo(name = "name")
    var name: String?,
    @ColumnInfo(name = "position")
    var position: String,
    @ColumnInfo(name = "player_photo")
    var photo: String,
    @ColumnInfo(name = "signature_1")
    var signature1: Int,
    @ColumnInfo(name = "signature_2")
    var signature2: Int,
    @ColumnInfo(name = "signature_3")
    var signature3: Int,
    @ColumnInfo(name = "currentTeam")
    var currentTeam: String?

) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id_key: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString()
    ) {
        id_key = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(flag)
        parcel.writeString(nickname)
        parcel.writeString(name)
        parcel.writeString(position)
        parcel.writeString(photo)
        parcel.writeInt(signature1)
        parcel.writeInt(signature2)
        parcel.writeInt(signature3)
        parcel.writeString(currentTeam)
        parcel.writeInt(id_key)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }


}
