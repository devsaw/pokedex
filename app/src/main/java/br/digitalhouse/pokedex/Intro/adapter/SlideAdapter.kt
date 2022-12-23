package br.digitalhouse.pokedex.Intro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.Intro.model.SlideDataClass
import br.digitalhouse.pokedex.R

class SlideAdapter (private val items: List<SlideDataClass>) :
    RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        return SlideViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_slides, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class SlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textDescript = view.findViewById<TextView>(R.id.textDescript)
        private val image = view.findViewById<ImageView>(R.id.imageViewSlide)

        fun bindView(item: SlideDataClass) {
            image.setImageResource(item.image)
            textDescript.text = (item.description)
        }
    }
}