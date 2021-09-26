package com.basalam.intern.android.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

/**
 * By the grace of Allah, Created by Sayed-MohammadReza Kazemi
 * on 4/18/2021.
 */
class NetworkUtils {

    companion object {

        fun isNetworkAvailable(context: Context): Boolean {

            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return false

            when {

                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {             // api 29 and above

                    val cp = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false

                    return cp.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

                }

                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> { // between api 21 and 29

                    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

                    return activeNetwork?.isConnectedOrConnecting == true


                }

                else -> { // before api 21

                    val n = cm.activeNetworkInfo

                    return n != null && n.isConnected
                }
            }

            return false
        }
    }
}