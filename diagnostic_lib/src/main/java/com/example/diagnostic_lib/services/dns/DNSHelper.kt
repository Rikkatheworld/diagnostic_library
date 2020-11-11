package com.example.diagnostic_lib.services.dns

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import java.io.InputStream
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.net.InetAddress
import java.util.*

/**
 * DNS信息获取
 *
 * @author rikka
 * @date 2020/11/8
 */
@SuppressLint("MissingPermission")
class DNSHelper {

    // region companion

    companion object {

        /**
         * 拿到Local Dns服务器的Ip地址
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun getLocalDnsServicesIp(context: Context): MutableList<String> {
            var dnsServiceList = getDnsServicesFromConnectivityManager(context)
            if (dnsServiceList.isEmpty()) {
                dnsServiceList = getDnsServicesFromSystemProperties()
                if (dnsServiceList.isEmpty()) {
                    dnsServiceList = getDnsServicesFromCmd()
                }
            }
            return dnsServiceList
        }

        /**
         * 从ConnectivityManager中获取DNS ip
         *
         * @param context
         * @return
         */
        @JvmStatic
        private fun getDnsServicesFromConnectivityManager(context: Context): MutableList<String> {
            val dnsServicesList = LinkedList<String>()
            if (Build.VERSION.SDK_INT >= 21) {
                val connectivityManager: ConnectivityManager? =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                connectivityManager?.let {
                    val activeNetworkInfo = it.activeNetworkInfo
                    it.allNetworks.forEach { netWork ->
                        val networkInfo = it.getNetworkInfo(netWork)
                        if (networkInfo?.type == activeNetworkInfo?.type) {
                            val lp = it.getLinkProperties(netWork)
                            lp?.dnsServers?.forEach { address ->
                                dnsServicesList.add(address.hostAddress)
                            }
                        }
                    }
                }
            }
            return dnsServicesList
        }

        /**
         * 从SystemProperties获取Dns IP
         *
         * @return
         */
        private fun getDnsServicesFromSystemProperties(): MutableList<String> {
            val dnsServers = mutableListOf<String>()
            val classSystemProperties = Class.forName("android.os.SystemProperties")
            val method = classSystemProperties.getMethod("get", String::class.java)
            val dnsString: MutableList<String> = arrayListOf(
                "net.dns1",
                "net.dns2",
                "net.dns3",
                "net.dns4"
            )
            dnsString.forEach {
                val value = method.invoke(null, it) as String?
                if (value != null && value != "" && dnsServers.contains(it)) {
                    dnsServers.add(it)
                }
            }
            return dnsServers
        }

        /**
         * 根据命令行 "getprop" 获取Dns服务器Ip
         *
         * @return
         */
        private fun getDnsServicesFromCmd(): MutableList<String> {
            val dnsServices = LinkedList<String>()
            try {
                val process = Runtime.getRuntime().exec("getprop")
                val inputStream = process.inputStream
                val lnr = LineNumberReader(InputStreamReader(inputStream))
                var line: String?
                while (lnr.readLine().also { line = it } != null) {
                    val split = line?.indexOf("]:[")
                    if (split == null || split == -1) continue
                    val property = line?.substring(1, split)
                    var value = line?.substring(split + 4, line?.length!! - 1)
                    if (property != null && (property.endsWith(".dns")
                                || property.endsWith(".dns1")
                                || property.endsWith(".dns2")
                                || property.endsWith(".dns3")
                                || property.endsWith(".dns4"))
                    ) {
                        val ip = InetAddress.getByName(value) ?: continue
                        value = ip.hostAddress ?: continue
                        if (value.isEmpty()) continue
                        dnsServices.add(value)
                    }
                }
            } catch (e: Exception) {
                throw(e)
            }
            return dnsServices
        }

    }
    // endregion

}