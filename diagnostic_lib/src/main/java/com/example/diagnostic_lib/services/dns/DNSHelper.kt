package com.example.diagnostic_lib.services.dns

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import android.net.ConnectivityManager
import android.os.Build
import org.xbill.DNS.Lookup
import org.xbill.DNS.SimpleResolver
import java.io.InputStreamReader
import java.io.LineNumberReader
import java.lang.invoke.MethodHandles
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress
import java.util.*

/**
 * DNS相关信息获取与检测逻辑
 *
 * @author rikka
 * @date 2020/11/8
 */
@SuppressLint("MissingPermission")
object DNSHelper {

    // region public

    /**
     * 拿到Local Dns服务器的Ip地址
     *
     * @param context
     * @return
     */
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
     * 系统默认解析域名的方式
     * 使用[InetAddress.getAllByName]获取
     *
     * @param hostname
     * @return
     */
    fun defaultParseDomain(hostname: String): MutableList<String> {
        if (hostname.isEmpty()) {
            throw Exception("Host name is null!")
        }
        val list = mutableListOf<String>()
        try {
            val addresses = InetAddress.getAllByName(hostname)
            addresses?.forEach {
                if (it is Inet4Address) {
                    list.add(it.hostAddress)
                }
            }
        } catch (e: Exception) {
            throw(e)
        }
        return list
    }

    /**
     * 指定DNS服务器来解析域名
     *
     * @param dnsIp
     * @param hostname
     * @return
     */
    fun specialParseDomain(dnsIp: String, hostname: String): MutableList<String> {
        val list = mutableListOf<String>()
        if (dnsIp.isEmpty() || hostname.isEmpty()) {
            throw Exception("Dns server or hostname is null!")
        }

        try {
            val lookup = Lookup(hostname)
            val simpleResolver = SimpleResolver(dnsIp)
            lookup.setResolver(simpleResolver)
            lookup.run()
            if (lookup.result == Lookup.SUCCESSFUL) {
                val records = lookup.answers
                records.forEach {
                    list.add(it.rdataToString())
                }
            } else {
                throw Exception(lookup.errorString)
            }
        } catch (e: Exception) {
            throw e
        }
        return list
    }

    // endregion

    // region private

    /**
     * 从ConnectivityManager中获取DNS ip
     *
     * @param context
     * @return
     */
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

    // endregion
}