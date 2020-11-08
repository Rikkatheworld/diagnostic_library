package com.example.diagnostic_lib.services

import com.example.diagnostic_lib.bean.BaseDetectInfo
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.services.base.BaseDiagnosticService

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
        return BaseDetectInfo()
    }

    override fun getTag(): String = TAG

    // endregion

    // region inner class

    /**
     * 承载Socket检测信息
     *
     */
    class SocketDetectInfo: BaseDetectInfo() {

    }
    // endregion
}