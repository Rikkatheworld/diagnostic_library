package com.example.diagnostic_lib

import com.example.diagnostic_lib.bean.NetResultInfo
import com.example.diagnostic_lib.interfaces.DiagnosticListener
import com.example.diagnostic_lib.services.Ping.PingDiagnosticService
import com.example.diagnostic_lib.services.BaseDiagnosticService
import com.example.diagnostic_lib.services.dns.DnsDiagnosticService
import com.example.diagnostic_lib.services.net.NetDiagnosticService
import com.example.diagnostic_lib.services.socket.SocketDiagnosticService
import com.example.diagnostic_lib.services.traceroute.TraceRouteDiagnosticService
import java.lang.Exception

/**
 * 诊断库的Runner, 执行在子线程中.
 *
 * @author rikka
 * @date 2020/11/7
 */
class DiagnosticRunner(
    private val mNetResultInfo: NetResultInfo,
    private val mDiagnosticListener: DiagnosticListener
) {

    // region companion

    companion object {
        private const val TAG = "DiagnosticRunner"
    }
    // endregion

    // region field

    private val mServiceList = mutableListOf<BaseDiagnosticService>()

    // endregion

    // region public

    suspend fun run() {
        if (mServiceList.isEmpty()) {
            addElement()
        }

        try {
            for (i in 0 until mServiceList.size) {
                mDiagnosticListener.onProceed("${mServiceList[i].getTag()} start... \n")
                mServiceList[i].startService()

                val resultString = if (mNetResultInfo.resultList.size == 0) {
                    "empty"
                } else {
                    mNetResultInfo.resultList[i].toString()
                }
                mDiagnosticListener.onProceed(
                    "${mServiceList[i].getTag()} Finish, result:$resultString} \n"
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            mDiagnosticListener.onError(e, "${e.message}")
            return
        }
        mDiagnosticListener.onCompleted(mNetResultInfo)
    }

    // endregion

    // region private

    private fun addElement() {
        mServiceList.clear()
        mServiceList.add(NetDiagnosticService(mNetResultInfo))
        mServiceList.add(DnsDiagnosticService(mNetResultInfo))
        mServiceList.add(PingDiagnosticService(mNetResultInfo))
        mServiceList.add(SocketDiagnosticService(mNetResultInfo))
        mServiceList.add(TraceRouteDiagnosticService(mNetResultInfo))
    }

    // endregion
}