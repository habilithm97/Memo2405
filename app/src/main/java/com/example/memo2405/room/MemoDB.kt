package com.example.memo2405.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Memo::class], version = 1, exportSchema = false)
abstract class MemoDB : RoomDatabase() {
    abstract fun dao(): MemoDao

    companion object { // 싱글톤
        @Volatile // 싱글톤 보장(해당 데이터가 메인 메모리에만 존재하도록 명시)
        private var instance: MemoDB? = null
        private const val DB_NAME = "memo_db"

        fun getInstance(context: Context) : MemoDB? {
            if(instance == null) {
                synchronized(MemoDB::class) { // 동기화(여러 스레드에서 동시에 인스턴스를 생성하지 않도록)
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MemoDB::class.java,
                        DB_NAME
                    ).build()
                }
            }
            return instance
        }
    }
}