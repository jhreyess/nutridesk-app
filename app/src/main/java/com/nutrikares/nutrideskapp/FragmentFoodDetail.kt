package com.nutrikares.nutrideskapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.Food
import com.nutrikares.nutrideskapp.databinding.FragmentFoodDetailBinding
import com.nutrikares.nutrideskapp.utils.Calendar
import java.io.File


class FoodDetail : Fragment() {

    companion object {
        const val RECIPE_TYPE = "recipeType"
    }

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeType: String

    private var recipe: Food? = Food()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeType = it.getString(RECIPE_TYPE).toString()
        }
        val selectedDate = Calendar.translate(Datasource.getCurrentDay(), "en")
        recipe = Datasource.getUserDiets().days[selectedDate]?.foods?.get(recipeType)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtn.setOnClickListener { findNavController().navigateUp() }

        var imagePath = ""
        binding.recipeTiming.text = ""
        recipe?.let {
            binding.recipeName.text = it.name
            imagePath = it.imageResourceId
        }

        val cacheFile = File(requireActivity().cacheDir, imagePath)
        if(cacheFile.exists()){
            binding.imageView.setImageURI(cacheFile.toUri())
        }else{
            Firebase.storage.reference.child("images").child(imagePath).getFile(cacheFile)
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val uri = cacheFile.toUri()
                        binding.imageView.setImageURI(uri)
                    }
                }
        }

        recipe?.let {
            binding.recipeTiming.text = resources.getString(R.string.minutes, it.time)
            binding.recipeDescContent.text = it.description
            binding.recipeIngredientsRecycler.adapter = ListAdapter(it.ingredients)
            binding.recipeIngredientsRecycler.setHasFixedSize(true)
            binding.recipeStepsRecycler.adapter = ListAdapter(it.steps)
            binding.recipeStepsRecycler.setHasFixedSize(true)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ListAdapter(
    val data: List<String>
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val textView: TextView = view!!.findViewById(R.id.adapterTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_items, parent, false)

        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = data[position]
        holder.textView.text = text
    }

    override fun getItemCount() = data.size
}