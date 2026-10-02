package com.doma.garaza.data

import android.content.Context
import com.doma.garaza.CronetSingleton
import com.doma.garaza.MyRequestCallback
import com.doma.garaza.NetworkExecutor
import kotlinx.coroutines.suspendCancellableCoroutine

class DeviceRepository(
    private val context: Context
) {

    private val engine = CronetSingleton.get(context)
    private val executor = NetworkExecutor.executor



    suspend fun sendToggleRequest() =
        suspendCancellableCoroutine<Unit> { continuation ->
        val request = engine.newUrlRequestBuilder(
            CronetSingleton.TOGGLE_REQUEST_STRING,
            MyRequestCallback(
                continuation = continuation
            ),
            executor
        ).build()

        request.start()

        continuation.invokeOnCancellation {
            request.cancel()
        }
    }
}