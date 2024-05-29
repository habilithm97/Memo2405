package com.example.memo2405.repo

import com.example.memo2405.room.Memo
import com.example.memo2405.room.MemoDao
import kotlinx.coroutines.flow.Flow

/**
 * dao를 사용하여 DB 작업을 캡슐화한 Repository 클래스
 -> @Volatile과 synchronized를 사용한 싱글톤 패턴을 사용했기 때문에
 인스턴스가 안전하게 하나만 생성되도록 할 수 있음
**/
class MemoRepo(private val dao: MemoDao) {
    suspend fun addMemo(memo: Memo) {
        dao.addMemo(memo)
    }

    suspend fun updateMemo(memo: Memo) {
        dao.updateMemo(memo)
    }

    suspend fun deleteMemo(memo: Memo) {
        dao.deleteMemo(memo)
    }

    val getAll: Flow<List<Memo>> = dao.getAll()
}