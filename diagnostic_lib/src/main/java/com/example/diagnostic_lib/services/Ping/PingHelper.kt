package com.example.diagnostic_lib.services.Ping

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Ping 的 Helper类.
 *
 * @author rikkaxie
 * @date 2020/11/26
 */
private const val TAG = "PingHelper"

object PingHelper {

    /**
     * Ping 指定Host
     *
     * @param host
     * @return
     */
    fun pingHostAndGetStatus(host: String): Int {
        var result: String? = null
        try {
            val p = Runtime.getRuntime().exec("ping -c 3 -w 100 $host") // ping网址3次
            // 读取ping的内容，可以不加
            val input: InputStream = p.inputStream
            val reader = BufferedReader(InputStreamReader(input))
            val stringBuffer = StringBuffer()
            var content: String?
            while (reader.readLine().also { content = it } != null) {
                stringBuffer.append(content)
            }
            // ping的状态
            Log.d(TAG, "stringBuffer: $stringBuffer")
            return p.waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return 1
    }
}