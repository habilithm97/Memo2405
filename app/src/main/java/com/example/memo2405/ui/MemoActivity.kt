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
            setResult(INSERT, intent)
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