package com.example.smitchtestapp.networkRepository

import android.net.nsd.NsdServiceInfo


interface NetworkResolvedListener {

    fun onNearByDeviceFound(serviceInfo: NsdServiceInfo)

}