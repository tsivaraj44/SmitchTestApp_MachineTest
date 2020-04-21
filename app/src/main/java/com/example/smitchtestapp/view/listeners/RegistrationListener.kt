package com.example.smitchtestapp.view.listeners

import android.net.nsd.NsdManager.RegistrationListener
import android.net.nsd.NsdServiceInfo

class RegistrationListener(registration: MyNetworkRegistration?) :
    RegistrationListener {
    private var registration: MyNetworkRegistration? = null

    interface MyNetworkRegistration {
        fun onDeviceRegistration(message: String?)
    }

    override fun onRegistrationFailed(
        serviceInfo: NsdServiceInfo,
        errorCode: Int
    ) {
        registration!!.onDeviceRegistration("Registration failed...")
    }

    override fun onUnregistrationFailed(
        serviceInfo: NsdServiceInfo,
        errorCode: Int
    ) {
        registration!!.onDeviceRegistration("Un registration failed...")
    }

    override fun onServiceRegistered(serviceInfo: NsdServiceInfo) {
        registration!!.onDeviceRegistration("Service registered...")
    }

    override fun onServiceUnregistered(serviceInfo: NsdServiceInfo) {
        registration!!.onDeviceRegistration("Service unregistered...")
    }

    init {
        this.registration = registration
    }
}