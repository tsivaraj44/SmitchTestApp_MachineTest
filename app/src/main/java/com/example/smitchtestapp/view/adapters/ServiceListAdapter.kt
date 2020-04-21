package com.example.smitchtestapp.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smitchtestapp.R
import com.example.smitchtestapp.model.ServiceDetails
import kotlinx.android.synthetic.main.item_services.view.*
import java.net.InetAddress
import java.util.*
import kotlin.collections.ArrayList

class ServiceListAdapter(): RecyclerView.Adapter<ServiceListAdapter.ServiceViewHolder>() {

    var serviceList:ArrayList<ServiceDetails> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        return ServiceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_services,parent, false))
    }

    override fun getItemCount() = serviceList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.txtServiceName.text = "Service Name: "+serviceList[position].serviceName
        holder.txtServiceType.text = "Service Type: "+ serviceList[position].serviceType
        holder.txtServiceIp.text = "Ip address: "+serviceList[position].inetAddress.hostAddress
        holder.txtServicePort.text = "Port: "+serviceList[position].port.toString()
    }

    class ServiceViewHolder(view:View): RecyclerView.ViewHolder(view){
        val txtServiceName = view.txt_service_name
        val txtServiceType = view.txt_service_type
        val txtServiceIp = view.txt_service_ip_address
        val txtServicePort = view.txt_service_port
    }

    fun updateList(data: ServiceDetails?) {
        serviceList.add(data!!)
        removeDuplicateServices()
        notifyDataSetChanged()
    }

    private fun removeDuplicateServices() {
        val treeList: TreeSet<ServiceDetails> =
            TreeSet<ServiceDetails>(Comparator { items1, items2 ->
                items1.inetAddress.toString().compareTo(items2.inetAddress.toString())
            })
        treeList.addAll(serviceList)
        serviceList.clear()
        serviceList.addAll(treeList)
    }

    fun clearServiceList(){
        serviceList.clear()
        notifyDataSetChanged()
    }

    fun removeConnectionLostService(data:InetAddress?){
        val length = serviceList.size
        try {
            for(index in 0..length){
                if(serviceList[index].inetAddress.toString() == data.toString()) {
                    serviceList.removeAt(index)
                    break
                }
            }
        } catch (e:Exception){
            e.printStackTrace()
        }

    }

}