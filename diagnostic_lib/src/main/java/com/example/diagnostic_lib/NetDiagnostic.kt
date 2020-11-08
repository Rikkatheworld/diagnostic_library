package com.example.diagnostic_lib

import android.content.Context
import android.os.Looper
import android.util.Log
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.interfaces.DiagnosticListener
import kotlinx.coroutines.*
import java.lang.Exception

/**
 * 诊断库中心.
 *
 * 开一个协程, Run 以DNS检测 -> ip Ping通 -> Socket RTT检测 -> TraceRoute检测的顺序
 * 进行诊断, 如果中间某一阶段失败, 则中断整个流程, 并抛出异常
 *
 * @author rikka
 * @date 2020/10/27
 */
class NetDiagnostic(
    private var mContext: Context,
    mDomain: String = "",
    private var mDiagnosticListener: DiagnosticListener
) {

    // region companion

    companion object {
        const val TAG = "NetDiagnostic"
    }

    // endregion

    // region field

    private val mJob: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private var mNetResultInfo: NetResultInfo =
        NetResultInfo()
    // endregion

    // region init

    init {
        mNetResultInfo.domain = mDomain
    }

    // endregion

    // region public

    /**
     * 开始诊断, 使用 [mJob] 去跑整个流程
     * 为了简化, 必须在主线程中执行, 否则抛出异常
     */
    fun startDiagnostic() {
        if (!checkInMainThread()) {
            mDiagnosticListener.onError(Exception(), "diagnostic must started on MainThread")
            return
        }
        if (mJob.isActive) {
            Log.d(TAG, "diagnostic job is running!")
            return
        }
        mJob.launch() {
            DiagnosticRunner(mNetResultInfo, mDiagnosticListener).run()
        }
    }

    /**
     * 停止诊断, 使用 [mJob] 进行关闭
     */
    fun stopDiagnostic() {
        if (mJob.isActive) {
            mJob.cancel()
        } else {
            return
        }
    }

    // endregion


    // region private

    private fun checkInMainThread(): Boolean {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            return true
        }
        return false
    }

    // endregion
}