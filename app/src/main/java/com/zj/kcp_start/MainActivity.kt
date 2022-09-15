package com.zj.kcp_start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zj.app.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.button).setOnClickListener {
            simpleClick()
        }
    }

    @DebugLog
    private fun simpleClick() {
        Thread.sleep(2000)
    }
}