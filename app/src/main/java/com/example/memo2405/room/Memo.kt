package com.example.memo2405.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(val title: String, val content: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}