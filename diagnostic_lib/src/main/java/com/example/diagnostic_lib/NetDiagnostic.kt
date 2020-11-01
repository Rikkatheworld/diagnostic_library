package com.example.diagnostic_lib

import android.content.Context
import com.example.diagnostic_lib.interfaces.DiagnosticListener
import com.example.diagnostic_lib.services.BaseDiagnosticService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

/**
 * 诊断库中心.
 *
 * @author rikka
 * @date 2020/10/27
 */
class NetDiagnostic {
    // region companion

    companion object {
        const val TAG = "NetDiagnostic"
    }

    // endregion

    // region field

    var mContext: Context? = null

    var mDomain: String = ""

    var mDiagnosticListener: DiagnosticListener? = null

    private var mServiceList = mutableListOf<BaseDiagnosticService>()

    private lateinit var mJob: CoroutineScope
    // endregion

    // region public

    /**
     * 开始诊断, 使用 [mJob] 去跑整个流程
     */
    fun startDiagnostic() {
        if (checkNecessaryInfoNoNull()) {
            return
        }

        val mJob = CoroutineScope(Dispatchers.Unconfined)
    }

    private fun checkNecessaryInfoNoNull(): Boolean {
        return true
    }


    /**
     * 停止诊断, 使用 [mJob] 进行关闭
     */
    fun stopDiagnostic() {
        mJob.cancel()
    }

    // endregion

    // region private

    // endregion
}