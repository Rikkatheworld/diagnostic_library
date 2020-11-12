package com.example.diagnostic_lib.services.dns

import android.content.Context
import com.example.diagnostic_lib.bean.BaseDetectInfo
import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.services.BaseDiagnosticService
import com.example.diagnostic_lib.utils.TimeUtils
import java.lang.Exception

/**
 * DNS 域名查询检测
 *
 * @author rikka
 * @date 2020/11/1
 */
class DnsDiagnosticService(mNetResultInfo: NetResultInfo) :
    BaseDiagnosticService(mNetResultInfo) {

    // region companion
    companion object {
        const val TAG = "DnsDiagnosticService"

        private const val CATEGORY_PREFIX = "Category : "
    }

    // endregion

    // region field

    private lateinit var mHost: String

    private lateinit var mContext: Context
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
        // 获取Local DNS服务器
        val dnsServices = DNSHelper.getLocalDnsServicesIp(mNetResultInfo.context)
        dnsDetectInfo.dnsServices = dnsServices
        // 解析 Ip
        val parseResult = parseDomain(dnsServices)
        dnsDetectInfo.parseDomainResult = parseResult
        dnsDetectInfo.totalDetectTime = TimeUtils.getElapsedTime(dnsDetectInfo.startTime)
        return dnsDetectInfo
    }

    override fun getTag(): String = TAG

    // endregion

    // region private

    /**
     * 解析域名, 一共有两种策略
     * 1. 使用SDK即系统默认的Api去解析域名
     * 2. 使用指定Local Dns去解析域名
     *
     * @param dnsServices
     * @return
     */
    private fun parseDomain(dnsServices: MutableList<String>): MutableList<DnsDetectInfo.CategoryDetect> {
        val parseResult = mutableListOf<DnsDetectInfo.CategoryDetect>()
        if (dnsServices.size == 0) {
            return parseResult
        }
        parseResult.add(defaultParseDomain())
        dnsServices.forEach {
            parseResult.add(localDnsParseDomain(it))
        }
        return parseResult
    }

    /**
     * 使用系统默认方法去解析域名
     *
     * @return
     */
    private fun defaultParseDomain(): DnsDetectInfo.CategoryDetect {
        val startTime = TimeUtils.getCurrentTime()
        val resList = DNSHelper.defaultParseDomain(mHost)
        return DnsDetectInfo.CategoryDetect(
            CATEGORY_PREFIX + "Default", mHost,
            TimeUtils.getElapsedTime(startTime),
            resList
        )
    }

    /**
     * 指定DNS解析域名
     *
     * @param dnsIp
     * @return
     */
    private fun localDnsParseDomain(dnsIp: String): DnsDetectInfo.CategoryDetect {
        val startTime = TimeUtils.getCurrentTime()
        val resList = DNSHelper.specialParseDomain(dnsIp, mHost)
        return DnsDetectInfo.CategoryDetect(
            CATEGORY_PREFIX + dnsIp, mHost,
            TimeUtils.getElapsedTime(startTime),
            resList
        )
    }

    // endregion

}