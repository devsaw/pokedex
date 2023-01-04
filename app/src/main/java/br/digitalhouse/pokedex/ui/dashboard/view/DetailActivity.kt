package br.digitalhouse.pokedex.ui.dashboard.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getData()
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
        val prevEvo = extra.getString("prevevo")
        val nextEvo = extra.getString("nextevo")

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

        val background = binding.background

        if (type == "Fire"){
            background.setBackgroundColor(Color.parseColor("#FF5C34"))
            binding.type.setImageResource(R.drawable.fire)
        }else if(type == "Water"){
            background.setBackgroundColor(Color.parseColor("#30B6FE"))
            binding.type.setImageResource(R.drawable.water)
        }else if(type == "Grass"){
            background.setBackgroundColor(Color.parseColor("#64DD17"))
            binding.type.setImageResource(R.drawable.grass)
        }else if(type == "Electric"){
            background.setBackgroundColor(Color.parseColor("#FFDE08"))
            binding.type.setImageResource(R.drawable.electric)
        }else if(type == "Flying"){
            background.setBackgroundColor(Color.parseColor("#F7FF08"))
            binding.type.setImageResource(R.drawable.flying)
        }else if(type == "Ice"){
            background.setBackgroundColor(Color.parseColor("#59D8C5"))
            binding.type.setImageResource(R.drawable.ice)
        }else if(type == "Psychic"){
            background.setBackgroundColor(Color.parseColor("#ED5484"))
            binding.type.setImageResource(R.drawable.psyc)
        }else if(type == "Rock"){
            background.setBackgroundColor(Color.parseColor("#ECD3AE"))
            binding.type.setImageResource(R.drawable.rock)
        }else if(type == "Normal"){
            background.setBackgroundColor(Color.parseColor("#555555"))
            binding.type.setImageResource(R.drawable.normal)
        }else if(type == "Poison" || type == "Bug"){
            background.setBackgroundColor(Color.parseColor("#BBFF00"))
            binding.type.setImageResource(R.drawable.poison)
        }else if(type == "Fighting"){
            background.setBackgroundColor(Color.parseColor("#913B3B"))
            binding.type.setImageResource(R.drawable.fighting)
        }else if(type == "Ground"){
            background.setBackgroundColor(Color.parseColor("#FFA200"))
            binding.type.setImageResource(R.drawable.ground)
        }else if(type == "Ghost"){
            background.setBackgroundColor(Color.parseColor("#E39FFD"))
            binding.type.setImageResource(R.drawable.ghost)
        }else if(type == "Dragon"){
            background.setBackgroundColor(Color.parseColor("#5294B4"))
            binding.type.setImageResource(R.drawable.dragon)
        }else{
            background.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
            binding.type.setImageResource(R.drawable.ic_pokeballsvg)
        }

        if (weaknesses == "Fire"){
            binding.weaknesses.setImageResource(R.drawable.fire)
        }else if(weaknesses == "Water"){
            binding.weaknesses.setImageResource(R.drawable.water)
        }else if(weaknesses == "Grass"){
            binding.weaknesses.setImageResource(R.drawable.grass)
        }else if(weaknesses == "Electric"){
            binding.weaknesses.setImageResource(R.drawable.electric)
        }else if(weaknesses == "Flying"){
            binding.weaknesses.setImageResource(R.drawable.flying)
        }else if(weaknesses == "Ice"){
            binding.weaknesses.setImageResource(R.drawable.ice)
        }else if(weaknesses == "Psychic"){
            binding.weaknesses.setImageResource(R.drawable.psyc)
        }else if(weaknesses == "Rock"){
            binding.weaknesses.setImageResource(R.drawable.rock)
        }else if(weaknesses == "Normal"){
            binding.weaknesses.setImageResource(R.drawable.normal)
        }else if(weaknesses == "Poison" || weaknesses == "Bug"){
            binding.weaknesses.setImageResource(R.drawable.poison)
        }else if(weaknesses == "Fighting"){
            binding.weaknesses.setImageResource(R.drawable.fighting)
        }else if(weaknesses == "Ground"){
            binding.weaknesses.setImageResource(R.drawable.ground)
        }else if(weaknesses == "Ghost"){
            binding.weaknesses.setImageResource(R.drawable.ghost)
        }else if(weaknesses == "Dragon"){
            binding.weaknesses.setImageResource(R.drawable.dragon)
        }else{
            binding.weaknesses.setImageResource(R.drawable.ic_pokeballsvg)
        }

        if (prevEvo!!.isEmpty()){
            binding.layPrev.visibility = View.GONE
        } else{
            binding.prevEvo.text = prevEvo
        }

        if (nextEvo!!.isEmpty()){
            binding.layNext.visibility = View.GONE
        }else{
            binding.nextEvo.text = nextEvo
        }
    }
}