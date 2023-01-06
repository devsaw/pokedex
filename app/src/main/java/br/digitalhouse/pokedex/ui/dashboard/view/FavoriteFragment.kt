package br.digitalhouse.pokedex.ui.dashboard.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.FragmentFavoriteBinding
import br.digitalhouse.pokedex.ui.dashboard.adapter.FavoriteAdapter
import br.digitalhouse.pokedex.ui.dashboard.adapter.SearchAdapter

class FavoriteFragment : Fragment(R.layout.fragment_favorite){
    private val binding: FragmentFavoriteBinding by lazy { FragmentFavoriteBinding.inflate(layoutInflater) }
    lateinit var rvFavAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter()
    }

    private fun adapter() {
        rvFavAdapter = FavoriteAdapter(requireContext(), onItemClicked = { name, num, image, height, weight, type, weaknesses, prevevo, nextevo ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("num", num)
            intent.putExtra("image", image)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            intent.putExtra("type", type)
            intent.putExtra("weaknesses", weaknesses)
            intent.putExtra("prevevo", prevevo)
            intent.putExtra("nextevo", nextevo)
            startActivity(intent)
        })
        requireView().findViewById<RecyclerView>(R.id.rvListFavorite).adapter = rvFavAdapter
    }


}