package com.example.diagnostic_lib.services.socket

import com.example.diagnostic_lib.bean.BaseDetectInfo
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.services.BaseDiagnosticService

/**
 * Socket rtt检测.
 *
 * @author rikka
 * @date 2020/11/1
 */
class SocketDiagnosticService(mNetResultInfo: NetResultInfo) : BaseDiagnosticService(mNetResultInfo) {

    // region companion

    companion object {
        const val TAG = "SocketDiagnosticService"
    }

    // endregion

    // region override

    override fun doDetect(): BaseDetectInfo {
        return SocketDetectInfo()
    }

    override fun getTag(): String = TAG

    // endregion

}