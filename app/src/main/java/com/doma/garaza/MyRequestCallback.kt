package com.doma.garaza

import android.util.Log
import kotlinx.coroutines.CancellableContinuation
import org.chromium.net.CronetException
import org.chromium.net.UrlRequest
import org.chromium.net.UrlResponseInfo
import java.nio.ByteBuffer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


private const val TAG = "MyRequestCallback"

class MyRequestCallback(
    private val continuation: CancellableContinuation<Unit>
//    private val onSuccess: () -> Unit,
//    private val onError: () -> Unit
) : UrlRequest.Callback() {
    override fun onRedirectReceived(request: UrlRequest?, info: UrlResponseInfo?, newLocationUrl: String?) {
        Log.i(TAG, "onRedirectReceived method called.")
        // You should call the request.followRedirect() method to continue
        // processing the request.
        // request?.followRedirect()
    }

    override fun onResponseStarted(request: UrlRequest?, info: UrlResponseInfo?) {
        Log.i(TAG, "onResponseStarted method called (request id: -).")
        // You should call the request.read() method before the request can be
        // further processed. The following instruction provides a ByteBuffer object
        // with a capacity of 102400 bytes for the read() method. The same buffer
        // with data is passed to the onReadCompleted() method.
        request?.read(ByteBuffer.allocateDirect(102400))
    }

    override fun onReadCompleted(request: UrlRequest?, info: UrlResponseInfo?, byteBuffer: ByteBuffer?) {
        Log.i(TAG, "onReadCompleted method called.")
        // You should keep reading the request until there's no more data.
//        byteBuffer?.clear()
//        request?.read(byteBuffer)
    }


//    override fun onSucceeded(
//                        request: UrlRequest,
//                        info: UrlResponseInfo
//                    ) {
//                        continuation.resume(Unit) { cause, _, _ -> onCancellation(cause) }
//                    }
//
//                    override fun onFailed(
//                        request: UrlRequest,
//                        info: UrlResponseInfo?,
//                        error: CronetException
//                    ) {
//                        continuation.resumeWithException(error)
//                    }

    override fun onSucceeded(request: UrlRequest?, info: UrlResponseInfo?) {
        Log.i(TAG, "[-] onSucceeded method called.")
        continuation.resume(Unit)
//        Handler(Looper.getMainLooper()).post{ // for thread safety
//            onSuccess()
//        }
    }

    override fun onFailed(request: UrlRequest?, info: UrlResponseInfo?, error: CronetException) {
        Log.i(TAG, "[-] onFailed method called.")
        continuation.resumeWithException(error)
//        Handler(Looper.getMainLooper()).post{ // for thread safety
//            onError()
//        }
    }

    override fun onCanceled(request: UrlRequest?, info: UrlResponseInfo?) {
        Log.i(TAG, "onCanceled method called.")
    }
}
