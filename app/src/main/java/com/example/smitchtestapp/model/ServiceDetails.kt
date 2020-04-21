package com.example.smitchtestapp.model

import java.net.InetAddress

data class ServiceDetails(
    var serviceName:String,
    var inetAddress: InetAddress,
    val serviceType:String,
    val port:Int
)