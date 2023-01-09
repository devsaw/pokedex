package br.digitalhouse.pokedex.ui.dashboard.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.digitalhouse.pokedex.R
import br.digitalhouse.pokedex.data.dto.PokemonsDataClass
import br.digitalhouse.pokedex.data.utils.ConfigFirebase
import br.digitalhouse.pokedex.databinding.FragmentFavoriteBinding
import br.digitalhouse.pokedex.ui.dashboard.adapter.FavoriteAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FavoriteFragment : Fragment(R.layout.fragment_favorite){
    private val binding: FragmentFavoriteBinding by lazy { FragmentFavoriteBinding.inflate(layoutInflater) }
    lateinit var rvFavAdapter: FavoriteAdapter
    private val taskList = mutableListOf<PokemonsDataClass>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromFireBase()
        deletDataFromFireBaseWithSwipe()
    }

    private fun getDataFromFireBase() {
        //função para exibir dados do firebase
        ConfigFirebase()
            .getDatabase()
            .child("favoritos")
            .child(ConfigFirebase().getIdUser() ?: "")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        taskList.clear()
                        for (snap in snapshot.children){
                            val task = snap.getValue(PokemonsDataClass::class.java) as PokemonsDataClass
                            taskList.add(task)
                        }
                        taskList.reverse()
                        adapter()
                    } else{
                        binding.textViewList.visibility = View.VISIBLE
                    }
                    binding.progressBar.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("ERRO", "erro ao recuperar tarefas do firebase")
                }

            })
    }

    private fun deletDataFromFireBaseWithSwipe() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedCourse: PokemonsDataClass = taskList.get(viewHolder.adapterPosition)

                //função para deletar do firebase
                ConfigFirebase()
                    .getDatabase()
                    .child("favoritos")
                    .child(ConfigFirebase().getIdUser() ?: "")
                    .child(deletedCourse.idP!!)
                    .removeValue()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            Toast.makeText(requireContext(), "Você libertou ${deletedCourse.nameP}", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(), "Erro ao libertar ${deletedCourse.nameP}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Erro ao libertar ${deletedCourse.nameP}", Toast.LENGTH_SHORT).show()
                    }

                taskList.removeAt(viewHolder.adapterPosition)
                rvFavAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                rvFavAdapter.notifyDataSetChanged()
            }
        }).attachToRecyclerView(binding.rvListFavorite)
    }

    private fun adapter() {
        rvFavAdapter = FavoriteAdapter(requireContext(), taskList, onItemClicked = { name, num, image, height, weight, type, weaknesses, prevevo, nextevo ->
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
        binding.rvListFavorite.adapter = rvFavAdapter
    }

    override fun onPause() {
        super.onPause()
        requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

}