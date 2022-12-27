package br.digitalhouse.pokedex.menu.view

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.ClipboardManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.digitalhouse.pokedex.databinding.ActivityPixBinding

class PixActivity : AppCompatActivity() {
    private val binding: ActivityPixBinding by lazy { ActivityPixBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnCopy.setOnClickListener{
            copyKey()
            Toast.makeText(this, "Chave copiada!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyKey() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.text = "cba9ba47-6e34-4503-b357-fee53bd343da"
        } else {
            val clipboard =
                getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("", "cba9ba47-6e34-4503-b357-fee53bd343da")
            clipboard.setPrimaryClip(clip)
        }
    }
}