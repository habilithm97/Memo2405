package com.example.memo2405.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // 동일한 항목이면 덮어씀
    suspend fun addMemo(memo: Memo)

    @Update
    suspend fun updateMemo(memo: Memo)

    @Delete
    suspend fun deleteMemo(memo: Memo)

    @Query("select * from memo order by id")
    fun getAll(): Flow<List<Memo>> // 비동기 데이터 스트림 Flow(DB의 변경 사항을 실시간으로 스트리밍)
}