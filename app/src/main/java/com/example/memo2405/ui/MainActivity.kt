package com.example.memo2405.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memo2405.adapter.MemoAdapter
import com.example.memo2405.databinding.ActivityMainBinding
import com.example.memo2405.room.Memo
import com.example.memo2405.ui.MemoActivity.Companion.INSERT
import com.example.memo2405.viewmodel.MemoVM

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val memoAdapter by lazy { MemoAdapter() }
    private val viewModel: MemoVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        binding.apply {
            fab.setOnClickListener {
                val intent = Intent(this@MainActivity, MemoActivity::class.java)
                resultLauncher.launch(intent)
            }
            rv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity).apply {
                    reverseLayout = true
                    stackFromEnd = true
                }
                setHasFixedSize(true) // 고정된 사이즈의 RecyclerView -> 불필요한 리소스 줄이기
                adapter = memoAdapter
            }
        }

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            val intent = result.data
            if (result.resultCode == INSERT) { // 추가 모드
                if (intent != null) {
                    val title = intent.getStringExtra(MemoActivity.TITLE).toString()
                    val content = intent.getStringExtra(MemoActivity.CONTENT).toString()
                    val memo = Memo(title, content)
                    viewModel.addMemo(memo)
                }
            }
        }
        // 리스트를 관찰하여 변경 시 어댑터에 전달함
        viewModel.getAll.observe(this@MainActivity, Observer {
            memoAdapter.submitList(it)
        })
    }
}