package br.digitalhouse.pokedex.ui.dashboard.view

import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.data.utils.NetworkReceiver
import br.digitalhouse.pokedex.databinding.ActivityNotifyBinding
import br.digitalhouse.pokedex.ui.dashboard.adapter.NotifyAdapter
import br.digitalhouse.pokedex.ui.dashboard.model.Notify

class NotifyActivity : AppCompatActivity() {
    private val binding: ActivityNotifyBinding by lazy { ActivityNotifyBinding.inflate(layoutInflater) }
    lateinit var adapter: NotifyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
        binding.textViewNotifyEmpty.visibility = View.VISIBLE
        binding.rvListNotify.visibility = View.GONE
       // adapter()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }
    }

//    private fun adapter() {
//        val list = List<Notify>
//        adapter = NotifyAdapter(list)
//        findViewById<RecyclerView>(R.id.rvListNotify).adapter = adapter
//    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(NetworkReceiver(), intentFilter)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.auth_main_enter, R.anim.auth_main_exit)
    }
}