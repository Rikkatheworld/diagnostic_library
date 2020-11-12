package com.example.diagnostic_lib.utils

import android.os.Build
import android.os.SystemClock

/**
 * 时间工具类
 *
 * @author rikka
 * @date 2020/11/11
 */
class TimeUtils {

    companion object {

        /**
         * 用于记录间隔的时间, 所以不使用[System.currentTimeMillis]
         *
         * @return
         */
        @JvmStatic
        fun getCurrentTime(): Long {
            return SystemClock.elapsedRealtimeNanos()
        }

        @JvmStatic
        fun getElapsedTime(startTime: Long): Long {
            return ((getCurrentTime() - startTime) * 1e-6).toLong()
        }
    }
}
