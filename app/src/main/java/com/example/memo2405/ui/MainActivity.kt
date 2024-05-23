package com.example.memo2405.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memo2405.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {

    }
}