package com.example.diagnostic_lib.interfaces

import com.example.diagnostic_lib.bean.NetResultInfo
import java.lang.Exception

/**
 * 诊断回调监听器.
 *
 * @author rikka
 * @date 2020/10/31
 */
interface DiagnosticListener {

    /**
     * 流程正常结束时调用
     *
     * @param result
     */
    fun onCompleted(result: NetResultInfo)

    /**
     * 学习RxJava, 在流程出现异常时,
     * 不会抛出已经检测得到的结果, 而是抛出异常
     *
     * @param e
     * @param msg
     */
    fun onError(e: Exception, msg: String)

    /**
     * 实时返回检测的数据, 以String表示
     *
     * @param s
     */
    fun onProceed(s: String)
}