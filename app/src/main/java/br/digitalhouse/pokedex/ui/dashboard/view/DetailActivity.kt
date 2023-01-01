package br.digitalhouse.pokedex.ui.dashboard.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getData()
        validData()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }
    }

    private fun getData() {
        val extra = intent.extras!!
        val name = extra.getString("name")
        val num = extra.getString("num")
        val image = extra.getString("image")
        val height = extra.getString("height")
        val weight = extra.getString("weight")
        val type = extra.getString("type")
        val weaknesses = extra.getString("weaknesses")
        val prevEvo = extra.getString("prevEvo")
        val nextEvo = extra.getString("nextEvo")
        binding.name.text = name
        binding.num.text = num
        binding.height.text = height
        binding.weight.text = weight
        binding.prevEvo.text = prevEvo
        binding.nextEvo.text = nextEvo

        Glide
            .with(this)
            .load(image)
            .error(R.drawable.pokepng)
            .into(binding.foto)

        Glide
            .with(this)
            .load(type)
            .error(R.drawable.ic_pokeballsvg)
            .into(binding.type)

        Glide
            .with(this)
            .load(weaknesses)
            .error(R.drawable.ic_pokeballsvg)
            .into(binding.weaknesses)
    }

    private fun validData(){
        val background = binding.background
    }
}