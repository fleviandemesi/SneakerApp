package com.example.sneakerapp.helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkHelper {
    companion object{
        fun checkFoeInternet(context: Context):Boolean{
//            register activity with the connectivity manager service
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager
            //this will work for android m and above
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                val network = connectivityManager.activeNetwork ?:
                return false
                //check for active network capability
                val activeNetwork = connectivityManager.getNetworkCapabilities(network)?:
                return false
                return when{
                    //indicate this network uses a cellular transport
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    //cellular network connection
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->true
                    else-> false

                }


            }else{
                // this is android below M
                val networkInfo = connectivityManager.activeNetworkInfo ?:
                return false
                return networkInfo.isConnected
            }
        }
    }
}