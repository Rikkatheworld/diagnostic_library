package com.example.diagnostic_lib

/**
 * 网络状态.
 *
 * @author rikka
 * @date 2020/10/31
 */
enum class NetStatus {
    /**
     * 网络状态良好
     */
    GOOD,

    /**
     * 弱网状态
     */
    BAD,

    /**
     * 未知
     */
    UNKNOWN,

    /**
     * 无网, 无信号
     */
    OFFLINE
}