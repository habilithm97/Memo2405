package com.example.memo2405.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memo2405.databinding.ActivityMemoBinding

class MemoActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMemoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {

    }
}