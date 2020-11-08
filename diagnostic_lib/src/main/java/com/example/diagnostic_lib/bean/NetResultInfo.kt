package com.example.diagnostic_lib.bean

import com.example.diagnostic_lib.NetStatus

/**
 * 诊断结果数据.
 *
 * @author rikka
 * @date 2020/11/1
 */
class NetResultInfo {
    var netInfo: NetStatus? = null
    var domain: String = ""
    var resultList = mutableListOf<BaseDetectInfo>()

}