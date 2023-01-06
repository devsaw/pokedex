package br.digitalhouse.pokedex.ui.dashboard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
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
}