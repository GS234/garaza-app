package com.doma.garaza

import android.content.Context
import org.chromium.net.CronetEngine
import java.util.concurrent.Executor
import java.util.concurrent.Executors


object CronetSingleton {
    const val TOGGLE_URL = BuildConfig.BASE_URL
    const val TOGGLE_PARAMS = BuildConfig.REQUEST_PARAMS
    const val TOGGLE_REQUEST_STRING = TOGGLE_URL + TOGGLE_PARAMS



    @Volatile
    private var engine: CronetEngine? = null

    fun get(context: Context): CronetEngine {
        println(TOGGLE_REQUEST_STRING)
        return engine ?: synchronized(this) {
            engine ?: CronetEngine.Builder(context.applicationContext)
                .build()
                .also { engine = it }
        }
    }
}

object NetworkExecutor {
    val executor: Executor = Executors.newSingleThreadExecutor()
}