package com.doma.garaza

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.doma.garaza.data.DeviceRepository
import com.doma.garaza.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration.Companion.milliseconds


class MyQTileService() : TileService() {
    private lateinit var repository: DeviceRepository
    private val tileScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var currentJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        repository = DeviceRepository(applicationContext)
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile.apply {
            state = Tile.STATE_INACTIVE
            label = "preklop"
            icon = Icon.createWithResource(this@MyQTileService, R.drawable.ic_garaza)
            updateTile()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tileScope.cancel()
    }

    override fun onClick() {
        super.onClick()
        sendRequest()
    }

    private fun updateTile(active: Boolean, newLabel: String="") {
        qsTile.apply {
            state = if (active) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            label = if (newLabel == "") "preklop" else "newLabel"
            icon = Icon.createWithResource(this@MyQTileService, R.drawable.ic_garaza)
            updateTile()
        }
    }

    private fun sendRequest(){
//        if(currentJob?.isActive == true) return
        currentJob?.cancel()
        currentJob = tileScope.launch {
            try {
                withTimeout(1_000.milliseconds) {
                    repository.sendToggleRequest()
                }

                qsTile.apply {
                    state = Tile.STATE_ACTIVE
                    updateTile()
                }
                delay(300.milliseconds)
                qsTile.apply {
                    state = Tile.STATE_INACTIVE
                    updateTile()
                }

            } catch (e: Exception) {
                qsTile.apply {
                    state = Tile.STATE_INACTIVE
                    updateTile()
                }
            }
        }
    }
}


