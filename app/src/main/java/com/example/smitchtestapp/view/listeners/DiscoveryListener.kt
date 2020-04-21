package com.example.smitchtestapp.view.listeners

import android.net.nsd.NsdManager.DiscoveryListener
import android.net.nsd.NsdServiceInfo

class DiscoveryListener(discovery: NetworkDiscovery?) :
    DiscoveryListener {
    private var discovery: NetworkDiscovery? = null

    interface NetworkDiscovery {
        fun onServiceFound(serviceInfo: NsdServiceInfo?)
        fun onConnectionLost(lostServiceInfo: NsdServiceInfo?)
        fun showDiscoveryMessage(message: String?)
    }

    override fun onStartDiscoveryFailed(
        serviceType: String,
        errorCode: Int
    ) {
        discovery!!.showDiscoveryMessage("Start discovery failed...")
    }

    override fun onStopDiscoveryFailed(
        serviceType: String,
        errorCode: Int
    ) {
        discovery!!.showDiscoveryMessage("Stop discovery failed...")
    }

    override fun onDiscoveryStarted(serviceType: String) {
        discovery!!.showDiscoveryMessage("Discovery started...")
    }

    override fun onDiscoveryStopped(serviceType: String) {
        discovery!!.showDiscoveryMessage("Discovery stopped...")
    }

    override fun onServiceFound(serviceInfo: NsdServiceInfo) {
        discovery!!.onServiceFound(serviceInfo)
    }

    override fun onServiceLost(serviceInfo: NsdServiceInfo) {
        discovery!!.showDiscoveryMessage("Service lost...")
        discovery!!.onConnectionLost(serviceInfo)
    }

    init {
        this.discovery = discovery
    }
}