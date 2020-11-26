package com.example.diagnostic_lib.services.net

import android.util.Log
import com.example.diagnostic_lib.bean.BaseDetectInfo
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.services.BaseDiagnosticService

/**
 * 手机网络状态检测.
 *
 * @author rikkaxie
 * @date 2020/11/26
 */
class NetDiagnosticService(mNetResultInfo: NetResultInfo) : BaseDiagnosticService(mNetResultInfo) {
    companion object {
        private const val TAG = "NetDiagnosticService"

        private const val WEBSITE_BAIDU = "www.baidu.com"
        private const val WEBSITE_JD = "www.jd.com"
        private const val WEBSITE_TENCENT = "www.qq.com"
    }

    val pingCheckList = mutableListOf(
        WEBSITE_BAIDU,
        WEBSITE_JD,
        WEBSITE_TENCENT
    )

    override fun doDetect(): BaseDetectInfo {
        val detectInfo = NetDetectInfo()
        detectInfo.isNetworkAvailable = NetHelper.checkNetworkAvailable(mNetResultInfo.context)
        detectInfo.isNetworkOnline = NetHelper.checkNetworkOnline(pingCheckList)
        detectInfo.localIp = NetHelper.getClientIp()
        val wifiProxy = NetHelper.isWifiProxy(mNetResultInfo.context)
        Log.d(TAG, "wifiProxy：$wifiProxy")
        return detectInfo
    }

    override fun getTag(): String = TAG

}