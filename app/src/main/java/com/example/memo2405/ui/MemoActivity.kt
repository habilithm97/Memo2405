package com.example.memo2405.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.memo2405.R
import com.example.memo2405.databinding.ActivityMemoBinding

class MemoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMemoBinding.inflate(layoutInflater) }
    private lateinit var toast: Toast

    companion object {
        const val ID = "id"
        const val TITLE = "title"
        const val CONTENT = "content"
        const val INSERT = 0
        const val UPDATE = 1
    }
    private var id = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        toast = Toast.makeText(this@MemoActivity, "", Toast.LENGTH_SHORT)

        binding.apply {
            btnCancel.setOnClickListener {
                finish()
            }
            btnSave.setOnClickListener {
                saveMemo()
            }
        }
        val intent = intent
        if(!intent.hasExtra(ID)) { // 추가 모드
            id = intent.getIntExtra(ID, -1)
        } else { // 수정 모드
            id = intent.getIntExtra(ID, -1)
            binding.edtTitle.setText(intent.getStringExtra(TITLE))
            binding.edtContent.setText(intent.getStringExtra(CONTENT))
        }
    }

    private fun saveMemo() {
        val title = binding.edtTitle.text.toString()
        val content = binding.edtContent.text.toString()

        if(title.trim().isEmpty()) {
            shortToast(getString(R.string.empty_title))
        } else {
            val intent = Intent()
            intent.putExtra(TITLE, title)
            intent.putExtra(CONTENT, content)

            if(id == -1) { // 추가 모드
                setResult(INSERT, intent)
            } else { // 수정 모드
                intent.putExtra(ID, id)
                setResult(UPDATE, intent)
            }
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        binding.edtTitle.requestFocus()
    }

    // 토스트 메시지 중복 방지
    private fun shortToast(msg: String) {
        toast.cancel()
        toast = Toast.makeText(this@MemoActivity, msg, Toast.LENGTH_SHORT)
        toast.show()
    }
}