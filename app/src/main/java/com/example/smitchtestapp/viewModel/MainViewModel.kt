package com.example.smitchtestapp.viewModel

import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smitchtestapp.model.ServiceDetails
import com.example.smitchtestapp.networkRepository.NetworkRepository
import com.example.smitchtestapp.networkRepository.NetworkResolvedListener

class MainViewModel : ViewModel(), NetworkResolvedListener {

    val networkRepository: NetworkRepository by lazy {
        NetworkRepository(this)
    }

    fun startScanServiceListener(mNsdManager: NsdManager?,serviceInfo: NsdServiceInfo?){
        networkRepository.startSearchForDevice(mNsdManager,serviceInfo)
    }

    var _serviceDetails:MutableLiveData<ServiceDetails> = MutableLiveData();

    val serviceDetails:LiveData<ServiceDetails>
        get() = _serviceDetails

    override fun onNearByDeviceFound(serviceInfo: NsdServiceInfo) {

        val serviceDetails = ServiceDetails(serviceInfo.serviceName,
            serviceInfo.host,
            serviceInfo.serviceType,
            serviceInfo.port)

        _serviceDetails.postValue(serviceDetails)

    }
}