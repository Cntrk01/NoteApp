package com.movieapp.noteapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("note_table")
@Parcelize
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    val title:String?,
    val note:String?,
    val date:String?
        ) : Parcelable