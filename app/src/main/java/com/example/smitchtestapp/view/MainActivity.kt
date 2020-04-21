package com.example.smitchtestapp.view

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smitchtestapp.R
import com.example.smitchtestapp.view.adapters.ServiceListAdapter
import com.example.smitchtestapp.view.listeners.DiscoveryListener
import com.example.smitchtestapp.view.listeners.RegistrationListener
import com.example.smitchtestapp.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RegistrationListener.MyNetworkRegistration,
    DiscoveryListener.NetworkDiscovery {

    val TAG = "MainActivity"

    private val SERVICE_NAME = "Myservice001"
    private val SERVICE_TYPE = "_http._tcp."
    private val PORT = 80

    private var disCoveryServiceActive = false
    private var published = false

    private var mNsdManager: NsdManager? = null

    var viewModel:MainViewModel? = null

    var discoveryListener: DiscoveryListener =
        DiscoveryListener(this)
    var registrationListener: RegistrationListener =
        RegistrationListener(this)

    var serviceListAdapter:ServiceListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mNsdManager = getSystemService(Context.NSD_SERVICE) as NsdManager?

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setAdapterFunctionality()

        btn_publish.setOnClickListener{
            startRegisterService(PORT)
        }

        btn_scan.setOnClickListener{
            startDisCoverService()
        }

        viewModel?.serviceDetails?.observe(this, Observer {

            println("Scan Details: "+it.serviceName)
//            availableServiceList.add(it)

            runOnUiThread { serviceListAdapter?.updateList(it) }

        })

    }

    override fun onPause() {
        super.onPause()
        if(mNsdManager!=null){
            stopDisCoverService()
            unRegisterService()
            runOnUiThread{ serviceListAdapter?.clearServiceList() }
        }
    }

    fun setAdapterFunctionality(){
        recyler_view.layoutManager = LinearLayoutManager(this)
        serviceListAdapter = ServiceListAdapter()
        recyler_view.adapter = serviceListAdapter
    }

    fun startRegisterService(port: Int) {
        val serviceInfo = NsdServiceInfo()
        serviceInfo.serviceName = SERVICE_NAME
        serviceInfo.serviceType = SERVICE_TYPE
        serviceInfo.port = port

        if(!published) {
            published = true
            mNsdManager?.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD,
                registrationListener
            )
        }

    }

    fun startDisCoverService() {
        if(!disCoveryServiceActive){
            mNsdManager!!.discoverServices(
                SERVICE_TYPE,
                NsdManager.PROTOCOL_DNS_SD, discoveryListener
            )
            disCoveryServiceActive = true
        }

    }

    fun unRegisterService() {
        if (published) {
            published = false
            mNsdManager!!.unregisterService(registrationListener)
        }
    }

    fun stopDisCoverService() {
        if (disCoveryServiceActive) {
            disCoveryServiceActive = false
            mNsdManager!!.stopServiceDiscovery(discoveryListener)
        }
    }

    override fun onDeviceRegistration(message: String?) {
        Log.d(TAG,"onDeviceRegistration $message")
        showToast(message)
    }

    override fun onServiceFound(serviceInfo: NsdServiceInfo?) {
        Log.d(TAG,"onServiceFound $serviceInfo")

        // Call Repository
        viewModel?.startScanServiceListener(mNsdManager, serviceInfo)

    }

    override fun showDiscoveryMessage(message: String?) {
        showToast(message)
    }

    override fun onConnectionLost(lostServiceInfo: NsdServiceInfo?) {
        showToast(lostServiceInfo?.serviceName+" lost. Ip: "+lostServiceInfo?.host.toString())
        serviceListAdapter?.removeConnectionLostService(lostServiceInfo?.host)
    }

    private fun showToast(msg:String?){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}
