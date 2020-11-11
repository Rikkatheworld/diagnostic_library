package com.example.diagnostic_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.diagnostic_lib.NetDiagnostic
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.interfaces.DiagnosticListener
import java.lang.Exception

class ResultInfoActivity : AppCompatActivity() {

    // region field

    lateinit var hostname: String

    // endregion

    // region compaion

    companion object {
        private const val TAG = "ResultInfoActivity"
        const val KEY_HOSTNAME = "key_hostname"
    }

    // endregion

    // region override

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_info)

        hostname = intent.getStringExtra(KEY_HOSTNAME) ?: return

        startDetect()
    }

    // endregion

    // region private

    // 开始检测hostname
    private fun startDetect() {
        val diagnostic = NetDiagnostic(this, hostname, MyDiagnosticListener())
        diagnostic.startDiagnostic()
    }

    // endregion

    // region class

    class MyDiagnosticListener : DiagnosticListener {
        override fun onCompleted(result: NetResultInfo) {
        }

        override fun onError(e: Exception, msg: String) {
            Log.d(TAG, "onError: e:$e msg:$msg")
        }

    }

    // endregion
}