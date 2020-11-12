package com.example.diagnostic_lib.services.dns

import com.example.diagnostic_lib.bean.BaseDetectInfo
import java.lang.invoke.MethodHandles

/**
 * 承载DNS的检测信息
 *
 * @author rikka
 * @date 2020/11/11
 */
class DnsDetectInfo : BaseDetectInfo() {
    // region field
    var hostName: String = ""

    /**
     * Local Dns服务器Ip
     */
    var dnsServices: MutableList<String> = mutableListOf()

    /**
     * 使用默认、每个Local DNS服务器去检索域名的结果
     *
     * @property category
     */
    var parseDomainResult = mutableListOf<CategoryDetect>()

    // endregion

    override fun printStep(): String = stage
    override fun toString(): String {
        return "DnsDetectInfo(hostName='$hostName', dnsServices=$dnsServices, parseDomainResult=$parseDomainResult)"
    }

    // endregion

    // region inner class

    data class CategoryDetect(
        /**
         * 使用的策略
         * xxx.xx: 代表使用 该DNS服务器去解析
         * Default: 代表使用系统的默认策略去解析
         */
        val category: String,
        val domain: String,
        val elapsedTime: Long,
        val parseList: MutableList<String>

    )

    // endregion

}