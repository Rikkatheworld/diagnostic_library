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
    var hostName: String = ""

    var dnsServices: MutableList<String> = mutableListOf()


    data class CategoryDetect(
        val category: String
    )

}