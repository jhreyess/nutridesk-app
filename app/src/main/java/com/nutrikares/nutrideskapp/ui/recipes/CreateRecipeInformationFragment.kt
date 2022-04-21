package com.nutrikares.nutrideskapp.ui.recipes

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentCreateRecipeInformationBinding


class CreateRecipeInformationFragment : Fragment() {
    private var _binding: FragmentCreateRecipeInformationBinding? = null
    private val binding get() = _binding!!
    private val storage :FirebaseStorage = FirebaseStorage.getInstance()
    var database : DatabaseReference = Firebase.database.reference
    lateinit var fileUri: Uri
    lateinit var downloadUri : Uri
    var clickDone = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRecipeInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        downloadUri = Uri.parse("")
        fileUri = Uri.parse("")
        clickDone=false

        if(Datasource.getClickOnRecipe()) {
            binding.uploadimageButton.visibility = View.GONE
            binding.recipeImageImageView.visibility = View.VISIBLE
            binding.updateImageButton.visibility = View.VISIBLE

            val recipe = Datasource.getCurrentRecipe()
            downloadUri = Uri.parse(recipe.imageResourceId)
            binding.recipeCaloriesEditText.setText(recipe.info.calories.toString())
            binding.recipeCarbsEditText.setText(recipe.info.carbs.toString())
            binding.recipeFatsEditText.setText(recipe.info.fats.toString())
            binding.recipeProteinEditText.setText(recipe.info.protein.toString())
            if(!recipe.imageResourceId.equals("")){
                Glide.with(this)
                    .load("${recipe.imageResourceId}kjdjkdjd")
                    .into(binding.recipeImageImageView)
            }
        }

        binding.acceptButton.setOnClickListener {
            if(checkFields()){
                uploadRecipeData()
                //attachData()
                if(Datasource.getClickOnRecipe()){
                    if(Datasource.updateRecipe()){
                        Toast.makeText(this.context, "Receta modificada exitosamente", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this.context, "La receta no pudo ser modificada", Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(Datasource.addRecipe()){
                        Toast.makeText(this.context, "Receta agregada exitosamente", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this.context, "La receta no pudo ser agregada", Toast.LENGTH_LONG).show();
                    }
                }
                findNavController().navigate(R.id.action_createRecipeInformationFragment_to_nav_recipes)
            }else{
                Toast.makeText(this.context, "Faltan datos por llenar", Toast.LENGTH_LONG).show();
            }
        }

        binding.uploadimageButton.setOnClickListener{
            pickImage()
            clickDone=true
        }
        binding.updateImageButton.setOnClickListener {
            pickImage()
            clickDone=true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkFields():Boolean{
        return !(binding.recipeCaloriesEditText.text.isEmpty() || binding.recipeCarbsEditText.text.isEmpty() ||
                binding.recipeFatsEditText.text.isEmpty() || binding.recipeProteinEditText.text.isEmpty() ||
                ((fileUri.toString().equals("")) && (downloadUri.toString().equals(""))))
    }

<<<<<<< Updated upstream
    fun attachData(){
        Datasource.newRecipe.info.calories = Integer.parseInt(binding.recipeCaloriesEditText.text.toString())
        Datasource.newRecipe.info.carbs = Integer.parseInt(binding.recipeCarbsEditText.text.toString())
        Datasource.newRecipe.info.fats = Integer.parseInt(binding.recipeFatsEditText.text.toString())
        Datasource.newRecipe.info.protein = Integer.parseInt(binding.recipeProteinEditText.text.toString())
        Datasource.newRecipe.imageResourceId = downloadUri.toString()
        //Log.v("Data-final",Datasource.newRecipe.toString())
    }

    fun addRecipe(){
        try{
            database.child("recipes").child(Datasource.newRecipeId).setValue(Datasource.newRecipe)
            Toast.makeText(this.context, "Receta agregada exitosamente", Toast.LENGTH_LONG).show();
            findNavController().navigate(R.id.action_createRecipeInformationFragment_to_nav_recipes)
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(this.context, "La receta no pudo ser agregada", Toast.LENGTH_LONG).show();
        }
    }
=======
//    fun attachData(){
//        Datasource.newRecipe.info.calories = Integer.parseInt(binding.recipeCaloriesEditText.text.toString())
//        Datasource.newRecipe.info.carbs = Integer.parseInt(binding.recipeCarbsEditText.text.toString())
//        Datasource.newRecipe.info.fats = Integer.parseInt(binding.recipeFatsEditText.text.toString())
//        Datasource.newRecipe.info.protein = Integer.parseInt(binding.recipeProteinEditText.text.toString())
//        //Datasource.newRecipe.imageResourceId=uploadImage().toString()
//        Log.v("Data-final",Datasource.newRecipe.toString())
//    }
>>>>>>> Stashed changes

    fun updateRecipe(){
        try{
            database.child("recipes").child(Datasource.mapOfRecipes[Datasource.getCurrentRecipe().name].toString()).setValue(Datasource.newRecipe)
            Toast.makeText(this.context, "Receta modificada exitosamente", Toast.LENGTH_LONG).show();
            findNavController().navigate(R.id.action_createRecipeInformationFragment_to_nav_recipes)
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(this.context, "La receta no pudo ser modificada", Toast.LENGTH_LONG).show();
        }
    }

    fun uploadRecipeData(){
        if(!fileUri.toString().equals("") && clickDone){
            val storageRef = storage.reference
            val file = fileUri
            val recipesImageRef = storageRef.child("images/${fileUri.lastPathSegment}")
            val uploadTask = recipesImageRef.putFile(file)

            uploadTask.addOnFailureListener{
                Toast.makeText(this.context, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                //Log.v("Cloud","Imagen no puedo ser subida")
            }.addOnSuccessListener {
                Toast.makeText(this.context, "Imagen cargada", Toast.LENGTH_SHORT).show();
                //Log.v("Cloud","Imagen subida")
            }
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                recipesImageRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result
                    //Log.v("Cloud",downloadUri.toString())
                    attachData()
                    if(Datasource.getClickOnRecipe()){
                        //Es modificación
                        updateRecipe()
                    }else{
                        //Es adición
                        addRecipe()
                    }
                } else {

                }
            }
        }else{
            attachData()
            if(Datasource.getClickOnRecipe()){
                //Es modificación
                updateRecipe()
            }else{
                //Es adición
                addRecipe()
            }
        }
    }

    //Métodos para poder elegir la imagen
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                fileUri = data?.data!!
                //Log.v("ImagePicker",fileUri.toString())
                binding.uploadimageButton.visibility = View.GONE
                binding.recipeImageImageView.visibility = View.VISIBLE
                binding.updateImageButton.visibility = View.VISIBLE
                binding.recipeImageImageView.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this.context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this.context, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    fun pickImage(){
        ImagePicker.with(this)
            .galleryOnly()
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }
}