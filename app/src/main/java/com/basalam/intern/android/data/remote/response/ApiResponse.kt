package com.basalam.intern.android.data.remote.response

/**
 * By the grace of Allah, Created by Sayed-MohammadReza Kazemi
 * on 5/18/2021.
 */
data class ApiResponse<T>(
    val status: NetworkStatus,
    val data: T? = null,
    val errorMessage: String? = null
) {

    companion object {

        fun <T> success(data: T) = ApiResponse(NetworkStatus.SUCCESS, data)
        fun <T> loading() = ApiResponse<T>(NetworkStatus.LOADING)
        fun <T> error(message: String) =
            ApiResponse<T>(status = NetworkStatus.ERROR, errorMessage = message)

        fun <T> networkError() = ApiResponse<T>(status = NetworkStatus.NETWORK_ERROR)

    }

}