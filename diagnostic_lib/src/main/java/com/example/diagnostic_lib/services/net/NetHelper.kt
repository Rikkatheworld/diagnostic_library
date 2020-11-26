package com.example.diagnostic_lib.services.net

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils
import android.util.Log
import com.example.diagnostic_lib.services.Ping.PingHelper
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*


/**
 * Net检测Helper类.
 *
 * @author rikkaxie
 * @date 2020/11/26
 */
private const val TAG = "NetHelper"

@SuppressLint("MissingPermission")
object NetHelper {

    fun checkNetworkAvailable(context: Context): Boolean {
        try {
            val connMgr =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connMgr.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * Ping外网, 看看手机可否连接外网
     *
     * @param checkList Ping网络使用的网址
     */
    fun checkNetworkOnline(checkList: MutableList<String>): Boolean {
        checkList.forEach { host ->
            val status = PingHelper.pingHostAndGetStatus(host)
            Log.d(TAG, "status: $status")
            if (status == 0) {
                return true
            }
        }
        return false
    }

    /**
     * 内网IP
     */
    fun getClientIp(): String {
        val localIp = ""
        val localEnumeration = NetworkInterface.getNetworkInterfaces()
        localEnumeration?.let {
            while (localEnumeration.hasMoreElements()) {
                val localEnumerationNew: Enumeration<*> =
                    (localEnumeration.nextElement() as NetworkInterface).inetAddresses
                while (localEnumerationNew.hasMoreElements()) {
                    val inetAddress =
                        localEnumerationNew.nextElement() as InetAddress
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address && !inetAddress.isLinkLocalAddress()) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        }
        return localIp
    }

    fun isWifiProxy(context: Context?): Boolean {
        val proxyPort: Int
        val proxyAddress: String = System.getProperty("http.proxyHost")
        val portStr = System.getProperty("http.proxyPort")
        proxyPort = (portStr ?: "-1").toInt()
        Log.d(TAG, "proxy:Address: $proxyAddress, port:$proxyPort")
        return !TextUtils.isEmpty(proxyAddress) && proxyPort != -1
    }

}
