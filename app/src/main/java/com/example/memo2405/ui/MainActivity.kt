package com.example.memo2405.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memo2405.R
import com.example.memo2405.adapter.MemoAdapter
import com.example.memo2405.databinding.ActivityMainBinding
import com.example.memo2405.room.Memo
import com.example.memo2405.ui.MemoActivity.Companion.CONTENT
import com.example.memo2405.ui.MemoActivity.Companion.ID
import com.example.memo2405.ui.MemoActivity.Companion.INSERT
import com.example.memo2405.ui.MemoActivity.Companion.TITLE
import com.example.memo2405.ui.MemoActivity.Companion.UPDATE
import com.example.memo2405.viewmodel.MemoVM
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
                    val title = intent.getStringExtra(TITLE).toString()
                    val content = intent.getStringExtra(CONTENT).toString()
                    val dateFormat = SimpleDateFormat("yyMMdd", Locale.getDefault())
                    val date = dateFormat.format(Date())
                    val memo = Memo(title, content, date)
                    viewModel.addMemo(memo)
                }
            } else if(result.resultCode == UPDATE) { // 수정 모드
                if(intent != null) {
                    val id = intent.getIntExtra(ID, -1)

                    if(id == -1) {
                        return@registerForActivityResult
                    }

                    val title = intent.getStringExtra(TITLE).toString()
                    val content = intent.getStringExtra(CONTENT).toString()
                    val dateFormat = SimpleDateFormat("yyMMdd", Locale.getDefault())
                    val date = dateFormat.format(Date())
                    val memo = Memo(title, content, date)
                    memo.id = id
                    viewModel.updateMemo(memo)
                }
            }
        }
        // 리스트를 관찰하여 변경 시 어댑터에 전달함
        viewModel.getAll.observe(this@MainActivity, Observer {
            memoAdapter.submitList(it) {
                binding.rv.scrollToPosition(it.size - 1) // 마지막 아이템 위치로 스크롤
            }
        })

        memoAdapter.apply {
            setOnItemClickListener(object : MemoAdapter.OnItemClickListener {
                override fun onItemClick(view: View, memo: Memo, position: Int) {
                    val intent = Intent(this@MainActivity, MemoActivity::class.java)
                    intent.putExtra(ID, memo.id)
                    intent.putExtra(TITLE, memo.title)
                    intent.putExtra(CONTENT, memo.content)
                    resultLauncher.launch(intent)
                }
            })
            setOnItemLongClickListener(object : MemoAdapter.OnItemLongClickListener {
                override fun onItemLongClick(view: View, memo: Memo, position: Int) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle(R.string.dialog_title)
                        .setMessage(R.string.dialog_msg)
                        .setIcon(R.drawable.delete)
                        .setPositiveButton(R.string.ok) { _, _ ->
                            viewModel.deleteMemo(memo)
                        }.setNegativeButton(R.string.cancel) { dialog, _ ->
                            dialog.dismiss()
                        }.show()
                }
            })
        }
    }
}