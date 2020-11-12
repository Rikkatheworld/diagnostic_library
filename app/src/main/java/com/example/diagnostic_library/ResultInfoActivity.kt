package com.example.diagnostic_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.diagnostic_lib.NetDiagnostic
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.interfaces.DiagnosticListener
import java.lang.Exception

class ResultInfoActivity : AppCompatActivity() {

    // region field

    lateinit var mHostName: String

    var mResultTextView: TextView? = null

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

        mHostName = intent.getStringExtra(KEY_HOSTNAME) ?: return
        mResultTextView = findViewById(R.id.detect_result)
        startDetect()
    }

    // endregion

    // region private

    // 开始检测hostname
    private fun startDetect() {
        val diagnostic = NetDiagnostic(this, mHostName, object : DiagnosticListener {
            override fun onCompleted(result: NetResultInfo) {
            }

            override fun onError(e: Exception, msg: String) {
                Log.d(TAG, "onError: e:$e msg:$msg")
                runOnUiThread {
                    mResultTextView?.append(msg)
                }
            }

            override fun onProceed(s: String) {
                Log.d(TAG, "onProceed: $s ")
                runOnUiThread {
                    mResultTextView?.append(s)
                }
            }
        })
        diagnostic.startDiagnostic()
    }

    // endregion

}