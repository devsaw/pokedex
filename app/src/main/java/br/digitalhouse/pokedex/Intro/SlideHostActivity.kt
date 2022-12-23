package br.digitalhouse.pokedex.Intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import br.digitalhouse.pokedex.DashBoard.DashBoardHostActivity
import br.digitalhouse.pokedex.Intro.adapter.SlideAdapter
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.ActivitySlideBinding

class SlideHostActivity : AppCompatActivity() {
    private val binding: ActivitySlideBinding by lazy { ActivitySlideBinding.inflate(layoutInflater) }
    private lateinit var introSlideAdapter: SlideAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnBoardingItems()
        setUpIndicators()
        setCurrentIndicator(0)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        binding.btnEnter.setOnClickListener{
            startActivity(Intent(this, DashBoardHostActivity::class.java))
        }

    }

    private fun setOnBoardingItems() {
        introSlideAdapter = SlideAdapter(
            listOf(
                IntroSlideDataClass(image = R.drawable.logo_fundo_branco, description = "A alugue.com é uma plataforma pensada em oferecer à você a melhor experiência de hospedagem, com cuidado e dedicação em cada detalhe. Vem viajar com a gente!"),
                IntroSlideDataClass(image = R.drawable.logo_fundo_branco, description = "Bem vindo ao alugue.com!"),
            )
        )
        val slideViewPager = findViewById<ViewPager2>(R.id.fragment_onboarding_slide_viewpager)
        slideViewPager.adapter = introSlideAdapter
        slideViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (slideViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        binding.imageNext.setOnClickListener{
            if (slideViewPager.currentItem +1 <introSlideAdapter.itemCount){
                slideViewPager.currentItem +=1
            }else{
                startActivity(Intent(this, SignInActivity::class.java))
            }
        }
    }

    private fun setUpIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSlideAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                binding.fragmentOnboardingSlideIndicator.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val indicatorsContainer = binding.fragmentOnboardingSlideIndicator
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}