package com.example.diagnostic_lib.services.dns

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import java.util.*

/**
 * DNS信息获取
 *
 * @author rikka
 * @date 2020/11/8
 */
class DNSHelper {

    // region companion

    companion object {
        @JvmStatic
        fun getDnsServicesFromConnectionManager(context: Context): MutableList<String> {
            val dnsServicesList = LinkedList<String>()
            if (Build.VERSION.SDK_INT > 23) {
                val connectivityManager: ConnectivityManager? =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                connectivityManager?.let {
                    val activeNetworkInfo = it.activeNetwork
                    for (i in it.allNetworks.indices)
                }
            }

        }
    }

    // endregion
}