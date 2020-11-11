package com.example.diagnostic_lib.services.dns

import android.content.Context
import android.util.Log
import com.example.diagnostic_lib.bean.BaseDetectInfo
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.services.base.BaseDiagnosticService
import com.example.diagnostic_lib.utils.TimeUtils
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

    private lateinit var mContext:Context
    // endregion

    // region override

    override fun doDetect(): BaseDetectInfo {
        mHost = mNetResultInfo.domain
        if (mHost == "") {
            throw Exception("host name is empty!")
        }
        mContext = mNetResultInfo.context


        val dnsDetectInfo = DnsDetectInfo()
        dnsDetectInfo.stage = "DNS"
        dnsDetectInfo.startTime = TimeUtils.getCurrentTime()
        dnsDetectInfo.hostName = mHost
        val dnsServices = DNSHelper.getLocalDnsServicesIp(mNetResultInfo.context)

        Log.d(TAG, "dns: $dnsServices")
        return dnsDetectInfo
    }

    override fun getTag(): String = TAG

    // endregion

}