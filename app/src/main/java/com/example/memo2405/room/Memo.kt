package com.example.memo2405.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo_tb")
data class Memo(val title: String, val content: String, val date: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}