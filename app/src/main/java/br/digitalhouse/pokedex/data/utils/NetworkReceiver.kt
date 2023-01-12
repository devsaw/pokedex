package br.digitalhouse.pokedex.data.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import br.digitalhouse.pokedex.ui.dashboard.view.DashBoardHostActivity
import br.digitalhouse.pokedex.ui.dashboard.view.NetWorkReceiverActivity

class NetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var wifiState =
            intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
        when (wifiState) {
            WifiManager.WIFI_STATE_ENABLED -> {

            }
            WifiManager.WIFI_STATE_DISABLED -> {
                var intent = Intent(context, NetWorkReceiverActivity::class.java)
                context?.startActivity(intent)
            }
        }
    }
}