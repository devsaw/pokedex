package br.digitalhouse.pokedex.ui.dashboard.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.databinding.FragmentSearchBinding
import br.digitalhouse.pokedex.ui.dashboard.adapter.SearchAdapter
import br.digitalhouse.pokedex.ui.dashboard.viewmodel.PokemonViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding: FragmentSearchBinding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    lateinit var rvSearchView: SearchAdapter
    private val pokemonViewModel: PokemonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView()
        adapter()
    }

    private fun adapter() {
        rvSearchView = SearchAdapter(requireContext(), onItemClicked = { titleMovie, overviews, pictures ->
            val intentDetail = Intent(requireContext(), DetailActivity::class.java)
            intentDetail.putExtra("title", titleMovie)
            intentDetail.putExtra("overview", overviews)
            intentDetail.putExtra("filmes", pictures)
            startActivity(intentDetail)
        })
        requireView().findViewById<RecyclerView>(R.id.rvListSv).adapter = rvSearchView
    }

    private fun searchView() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            private var searchJob: Job? = null
            override fun onQueryTextSubmit(search: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(search: String?): Boolean {
                searchJob?.cancel()
                searchJob = if (!search.isNullOrBlank()) {
                    lifecycleScope.launch {
                        val searchPokemon = pokemonViewModel.fetchPokemon()
                        rvSearchView.update(searchPokemon)
                    }
                } else {
                    null
                }
                return true
            }
        })
    }

}