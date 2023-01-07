package br.digitalhouse.pokedex.ui.intro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import br.digitalhouse.pokedex.ui.intro.adapter.SlideAdapter
import br.digitalhouse.pokedex.ui.intro.model.SlideDataClass
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.ui.signin.view.SignInHostActivity
import br.digitalhouse.pokedex.databinding.ActivitySlideHostBinding

class SlideHostActivity : AppCompatActivity() {
    private val binding: ActivitySlideHostBinding by lazy { ActivitySlideHostBinding.inflate(layoutInflater) }
    private lateinit var introSlideAdapter: SlideAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnBoardingItems()
        setUpIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnBoardingItems() {
        introSlideAdapter = SlideAdapter(
            listOf(
                SlideDataClass(image = R.drawable.pokepng, description = "150 pokemons"),
                SlideDataClass(image = R.drawable.pokepng, description = "Capture o seu!"),
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
                startActivity(Intent(this, SignInHostActivity::class.java))
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

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.auth_main_enter, R.anim.auth_main_exit)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}