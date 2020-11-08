package com.example.diagnostic_lib.services

import com.example.diagnostic_lib.bean.BaseDetectInfo
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.services.base.BaseDiagnosticService

/**
 * Ping ip检测.
 *
 * @author rikka
 * @date 2020/10/31
 */
class PingDiagnosticService(mNetResultInfo: NetResultInfo) : BaseDiagnosticService(mNetResultInfo) {

    // region companion

    companion object {
        const val TAG = "PingDiagnosticService"
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
     * 承载Ping的检测信息
     *
     */
    class PingDetectInfo: BaseDetectInfo() {

    }

    // endregion

}