package com.example.smitchtestapp.networkRepository

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log

class NetworkRepository(networkResolvedListener: NetworkResolvedListener?) {

    val TAG = "Result Data"

    private var networkResolvedListener:NetworkResolvedListener?=null

    init {
        this.networkResolvedListener=networkResolvedListener
    }

    fun startSearchForDevice(mNsdManager: NsdManager?,serviceInfo: NsdServiceInfo?){
        Log.d(TAG, "Service Started...")
//        mNsdManager?.resolveService(serviceInfo, this)

        val listener = object : NsdManager.ResolveListener{
            override fun onResolveFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
                println("$TAG onResolveFailed: $serviceInfo")
            }

            override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
                println("$TAG onServiceResolved success: $serviceInfo")

                networkResolvedListener?.onNearByDeviceFound(serviceInfo)
            }

        }

        mNsdManager?.resolveService(serviceInfo, listener)
    }


}