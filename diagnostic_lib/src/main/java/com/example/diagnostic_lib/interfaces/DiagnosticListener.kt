package com.example.diagnostic_lib.interfaces

import com.example.diagnostic_lib.NetInfo
import com.example.diagnostic_lib.NetStatus
import java.lang.Exception

/**
 * 诊断回调监听器.
 *
 * @author rikka
 * @date 2020/10/31
 */
interface DiagnosticListener {
    fun onCompleted(result: NetInfo)
    fun onError(e: Exception, msg: String)
}