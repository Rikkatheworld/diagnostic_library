package com.example.diagnostic_lib.bean

/**
 * 检测结果基类数据类.
 *
 * @author rikka
 * @date 2020/11/7
 */
abstract class BaseDetectInfo {
    /**
     * 表示测试的阶段
     */
    var stage: String = ""

    /**
     * 开始时间
     */
    var startTime: Long = 0

    /**
     * 总消耗时长
     */
    var totalDetectTime: Long = 0

    /**
     * 打印结果, 供页面查看
     *
     * @return
     */
    abstract fun printStep(): String
}