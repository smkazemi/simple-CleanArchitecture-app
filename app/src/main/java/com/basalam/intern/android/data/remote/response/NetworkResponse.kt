package com.basalam.intern.android.data.remote.response


data class NetworkResponse<out T>(
    val status: NetworkStatus,
    val data: T?,
    val errorMessage: String? = null
) {

    companion object {

        fun <T> success(data: T) = NetworkResponse(NetworkStatus.SUCCESS, data)
        fun error(message: String) = NetworkResponse(NetworkStatus.ERROR, null, message)
        fun networkError() = NetworkResponse(NetworkStatus.NETWORK_ERROR, null)

    }

}
