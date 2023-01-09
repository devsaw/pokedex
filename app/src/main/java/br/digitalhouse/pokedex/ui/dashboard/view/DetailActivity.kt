package br.digitalhouse.pokedex.ui.dashboard.view

import android.animation.ValueAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.data.dto.PokemonsDataClass
import br.digitalhouse.pokedex.data.utils.ConfigFirebase
import br.digitalhouse.pokedex.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private lateinit var task : PokemonsDataClass
    private var type : String = ""
    private var image : String = ""
    private var weaknesses : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getData()
        setOnClickListener()
        task = PokemonsDataClass()
    }

    override fun onResume() {
        super.onResume()
        animation()
    }

    private fun setOnClickListener() {
        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnFav.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            binding.fav.setImageResource(R.drawable.ic_heartred)
            pushDataToFireBase()
        }
    }

    private fun getData() {
        val extra = intent.extras!!
        val name = extra.getString("name")
        val num = extra.getString("num")
        image = extra.getString("image")!!
        val height = extra.getString("height")
        val weight = extra.getString("weight")
        type = extra.getString("type")!!
        weaknesses = extra.getString("weaknesses")!!
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
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_fire)
        }else if(type == "Water"){
            background.setBackgroundColor(Color.parseColor("#30B6FE"))
            binding.type.setImageResource(R.drawable.water)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_water)
        }else if(type == "Grass"){
            background.setBackgroundColor(Color.parseColor("#64DD17"))
            binding.type.setImageResource(R.drawable.grass)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_grass)
        }else if(type == "Electric"){
            background.setBackgroundColor(Color.parseColor("#FFDE08"))
            binding.type.setImageResource(R.drawable.electric)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_electric)
        }else if(type == "Flying"){
            background.setBackgroundColor(Color.parseColor("#F7FF08"))
            binding.type.setImageResource(R.drawable.flying)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_flying)
        }else if(type == "Ice"){
            background.setBackgroundColor(Color.parseColor("#59D8C5"))
            binding.type.setImageResource(R.drawable.ice)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_ice)
        }else if(type == "Psychic"){
            background.setBackgroundColor(Color.parseColor("#ED5484"))
            binding.type.setImageResource(R.drawable.psyc)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_psychic)
        }else if(type == "Rock"){
            background.setBackgroundColor(Color.parseColor("#ECD3AE"))
            binding.type.setImageResource(R.drawable.rock)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_rock)
        }else if(type == "Normal"){
            background.setBackgroundColor(Color.parseColor("#555555"))
            binding.type.setImageResource(R.drawable.normal)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_normal)
        }else if(type == "Poison" || type == "Bug"){
            background.setBackgroundColor(Color.parseColor("#BBFF00"))
            binding.type.setImageResource(R.drawable.poison)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_poison)
        }else if(type == "Fighting"){
            background.setBackgroundColor(Color.parseColor("#913B3B"))
            binding.type.setImageResource(R.drawable.fighting)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_fighting)
        }else if(type == "Ground"){
            background.setBackgroundColor(Color.parseColor("#FFA200"))
            binding.type.setImageResource(R.drawable.ground)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_ground)
        }else if(type == "Ghost"){
            background.setBackgroundColor(Color.parseColor("#E39FFD"))
            binding.type.setImageResource(R.drawable.ghost)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_ghost)
        }else if(type == "Dragon"){
            background.setBackgroundColor(Color.parseColor("#5294B4"))
            binding.type.setImageResource(R.drawable.dragon)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav_dragon)
        }else{
            background.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
            binding.type.setImageResource(R.drawable.ic_pokeballsvg)
            binding.btnFav.setBackgroundResource(R.drawable.shape_button_fav)
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

        if (prevEvo!!.isNullOrEmpty()){
            binding.layPrev.visibility = View.GONE
        } else{
            binding.prevEvo.text = prevEvo
        }

        if (nextEvo!!.isNullOrEmpty()){
            binding.layNext.visibility = View.GONE
        }else{
            binding.nextEvo.text = nextEvo
        }
    }

    private fun pushDataToFireBase(){
        task.nameP = binding.name.text.toString()
        task.numP = binding.num.text.toString()
        task.imageP = image
        task.elementP = type
        task.heightP = binding.height.text.toString()
        task.weightP = binding.weight.text.toString()
        task.weaknessP = weaknesses
        task.prevP = binding.prevEvo.text.toString()
        task.nextP = binding.nextEvo.text.toString()

        ConfigFirebase()
            .getDatabase()
            .child("favoritos")
            .child(ConfigFirebase().getIdUser() ?: "")
            .child(task.idP!!)
            .setValue(task)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Adicionado aos favoritos!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Erro ao adicionar!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun animation(){
        val animator= ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 3000
        animator.interpolator = BounceInterpolator()
        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            binding.background.alpha = animatedValue
            binding.foto.alpha = animatedValue
            binding.foto.scaleX = animatedValue
            binding.foto.scaleY = animatedValue
        }
        animator.start()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.auth_main_enter, R.anim.auth_main_exit)
    }
}