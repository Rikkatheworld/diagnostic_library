package com.example.diagnostic_lib.services.traceroute

import com.example.diagnostic_lib.bean.BaseDetectInfo
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.services.BaseDiagnosticService

/**
 * TraceRoute 检测.
 *
 * @author rikka
 * @date 2020/11/1
 */
class TraceRouteDiagnosticService(mNetResultInfo: NetResultInfo) : BaseDiagnosticService(mNetResultInfo) {
    // region companion

    companion object{
        const val TAG = "TraceRouteDiagnosticService"
    }

    // endregion

    // region override

    override fun doDetect(): BaseDetectInfo {
        return TraceRouteDetectInfo()
    }

    override fun getTag(): String =
        TAG

    // endregion


}