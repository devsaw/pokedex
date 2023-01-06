package br.digitalhouse.pokedex.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.ui.dashboard.model.Notify

class NotifyAdapter(private val mNotificacao: List<Notify>):
    RecyclerView.Adapter<NotifyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cardnotify_layout, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = mNotificacao[position].title
        holder.descript.text = mNotificacao[position].description
        holder.date.text = mNotificacao[position].date
    }

    override fun getItemCount(): Int {
        return mNotificacao.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.titleNotify)
        val descript = itemView.findViewById<TextView>(R.id.textNotify)
        val date = itemView.findViewById<TextView>(R.id.dateNotify)
    }

}