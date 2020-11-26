package com.example.diagnostic_lib.services.net

import com.example.diagnostic_lib.bean.BaseDetectInfo

/**
 * 手机网络检测结果.
 *
 * @author rikkaxie
 * @date 2020/11/26
 */
class NetDetectInfo : BaseDetectInfo() {

    var isNetworkAvailable = false
    var isNetworkOnline = false
    var proxyIp = ""
    var proxyPort = ""
    var localIp = ""
    var getResult: HttpGetInfo? = null

    class HttpGetInfo {
        var host = "*"
        var outIp = "*"
        var outIpCountry = "*"
    }

    override fun printStep(): String = ""

    override fun toString(): String {
        return "NetDetectInfo(isNetworkAvailable=$isNetworkAvailable, isNetworkOnline=$isNetworkOnline, proxyIp='$proxyIp', proxyPort='$proxyPort', localIp='$localIp')"
    }


}