package com.example.diagnostic_lib.services

import android.util.Log
import com.example.diagnostic_lib.bean.BaseDetectInfo
import com.example.diagnostic_lib.bean.NetResultInfo
import java.lang.Exception

/**
 * 诊断库服务基类.
 *
 * @author rikka
 * @date 2020/10/31
 */
private const val TAG = "BaseDiagnosticService"
abstract class BaseDiagnosticService(
    val mNetResultInfo: NetResultInfo
) {
    // region field
    // endregion

    // region private

    // endregion

    // region public

    @Throws(Exception::class)
    fun startService() {
        Log.d(TAG, "${getTag()} is Starting...")
        val res = doDetect()
        mNetResultInfo.resultList.add(res)
    }

    // endregion

    // region abstract

    abstract fun doDetect(): BaseDetectInfo

    abstract fun getTag(): String

    // endregion
}