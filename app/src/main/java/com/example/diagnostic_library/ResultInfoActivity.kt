package com.example.diagnostic_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ResultInfoActivity : AppCompatActivity() {

    lateinit var hostname: String

    companion object {
        const val KEY_HOSTNAME = "key_hostname"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_info)

        hostname = intent.getStringExtra(KEY_HOSTNAME) ?: return

        startDetect()
    }

    // 开始检测hostname
    private fun startDetect() {

    }
}