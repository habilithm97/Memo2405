package com.example.memo2405.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.memo2405.repo.MemoRepo
import com.example.memo2405.room.Memo
import com.example.memo2405.room.MemoDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Repository 클래스를 통해 DB와 상호작용하고, UI에 필요한 데이터를 제공하는
 ViewModel 클래스
 */
class MemoVM(application: Application) : AndroidViewModel(application) {
    private val repository: MemoRepo
    val getAll: LiveData<List<Memo>>

    init {
        /**
         * application context 사용
         -메모리 릭 방지(애플리케이션의 수명과 일치하기 때문에)
         -DB가 애플리케이션의 전역 context에서 안전하게 접근
         */
        val dao = MemoDB.getInstance(application)!!.dao()
        repository = MemoRepo(dao)
        // UI에서 효율적이고 안전하게 데이터를 관찰/처리할 수 있도록 LiveData로 변환
        getAll = repository.getAll.asLiveData()
    }

    fun addMemo(note: Memo) {
        /**
         * viewModelScope : ViewModel의 생명주기에 맞춰 자동으로 취소되는 코루틴 스코프
         -> ViewModel이 파괴되면 코루틴이 자동으로 취소되어 메모리 릭을 방지함
         */
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMemo(note)
        }
    }

    fun updateMemo(memo: Memo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMemo(memo)
        }
    }

    fun deleteMemo(memo: Memo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMemo(memo)
        }
    }
}