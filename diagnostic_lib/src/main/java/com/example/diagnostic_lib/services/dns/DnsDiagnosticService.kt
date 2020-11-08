package com.example.diagnostic_lib.services.dns

import com.example.diagnostic_lib.bean.BaseDetectInfo
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.services.base.BaseDiagnosticService
import java.lang.Exception

/**
 * DNS 域名查询检测.
 *
 * @author rikka
 * @date 2020/11/1
 */
class DnsDiagnosticService(mNetResultInfo: NetResultInfo) :
    BaseDiagnosticService(mNetResultInfo) {

    // region companion
    companion object {
        const val TAG = "DnsDiagnosticService"
    }

    // endregion

    // region field

    private lateinit var mHost: String

    // endregion

    // region override

    override fun doDetect(): BaseDetectInfo {
        mHost = mNetResultInfo.domain
        if (mHost == "") {
            throw Exception("host name is empty!")
        }

        val dnsDetectInfo =
            DNSDetectInfo()
        dnsDetectInfo.stage = "DNS"
        dnsDetectInfo.startTime = System.currentTimeMillis()
        dnsDetectInfo.hostName = mHost

        return dnsDetectInfo
    }

    override fun getTag(): String =
        TAG

    // endregion

    // region inner class

    /**
     * 承载DNS的检测信息
     *
     */
    class DNSDetectInfo : BaseDetectInfo() {
        var hostName: String = ""
    }

    // endregion
}