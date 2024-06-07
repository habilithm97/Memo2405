package com.example.memo2405.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Memo::class], version = 2, exportSchema = false)
abstract class MemoDB : RoomDatabase() {
    abstract fun dao(): MemoDao

    companion object { // 싱글톤
        @Volatile // 싱글톤 보장(해당 데이터가 메인 메모리에만 존재하도록 명시)
        private var INSTANCE: MemoDB? = null
        private const val DB_NAME = "memo_db"
        private const val TB_NAME = "memo_tb"

        fun getInstance(context: Context) : MemoDB {
            // INSTANCE가 null이면 instance를 생성하고 반환
            return INSTANCE ?: synchronized(this) { // 동기화(멀티 스레드 환경에서 안전하게 생성)
                val instance = Room.databaseBuilder(
                    context.applicationContext, MemoDB::class.java, DB_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build()
                // 생성된 instance를 INSTANCE에 할당
                // 이후에는 INSTANCE가 null이 아니므로 이미 생성된 INSTANCE를 반환
                INSTANCE = instance // 싱글톤 유지
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table $TB_NAME add column date text not null default ''")
            }
        }
    }
}
