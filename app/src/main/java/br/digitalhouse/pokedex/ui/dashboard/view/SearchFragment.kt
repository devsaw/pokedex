package br.digitalhouse.pokedex.ui.dashboard.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.data.dto.ListPokemon
import br.digitalhouse.pokedex.data.dto.PokemonObject
import br.digitalhouse.pokedex.databinding.FragmentSearchBinding
import br.digitalhouse.pokedex.ui.dashboard.adapter.SearchAdapter
import br.digitalhouse.pokedex.ui.dashboard.viewmodel.PokemonViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding: FragmentSearchBinding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    lateinit var searchAdapter: SearchAdapter
    private val cList = mutableListOf<ListPokemon>()
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
        adapter()
    }

    private fun adapter() {
        searchAdapter = SearchAdapter(requireContext(), cList as ArrayList, onItemClicked = { name, num, image, height, weight, type, weaknesses, prevevo, nextevo ->
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
        binding.rvListSv.adapter = searchAdapter

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {


            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())) {
                    searchAdapter.filter.filter("")
                } else {
                    searchAdapter.filter.filter(s.toString())
                }
            }

        })
    }

    override fun onPause() {
        super.onPause()
        requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

}