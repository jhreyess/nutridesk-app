package com.nutrikares.nutrideskapp.ui.recipes

import android.app.Activity
import android.app.ProgressDialog
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
import com.google.firebase.storage.ktx.storage
import com.nutrikares.nutrideskapp.R
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentCreateRecipeInformationBinding


class CreateRecipeInformationFragment : Fragment() {
    private var _binding: FragmentCreateRecipeInformationBinding? = null
    private val binding get() = _binding!!
    //Firebase
    private val storage :FirebaseStorage = FirebaseStorage.getInstance()
    var database : DatabaseReference = Firebase.database.reference
    //Variables for this fragment
    lateinit var fileUri: Uri
    lateinit var downloadUri : Uri
    var clickDone = false
    private lateinit var progressDialog : ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRecipeInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressDialog = ProgressDialog(activity)
        progressDialog.setTitle("Por favor espera")
        progressDialog.setMessage("Subiendo imagen")
        progressDialog.setCanceledOnTouchOutside(false)

        downloadUri = Uri.parse("")
        fileUri = Uri.parse("")
        clickDone=false

        if(Datasource.getClickOnRecipe()) {
            binding.uploadimageButton.visibility = View.GONE
            binding.recipeImageImageView.visibility = View.VISIBLE
            binding.updateImageButton.visibility = View.VISIBLE

            val recipe = Datasource.getCurrentRecipe()
            val storageRef = Firebase.storage.reference
            storageRef.child("images").child(recipe.imageResourceId).downloadUrl.addOnSuccessListener { uri->
                downloadUri = uri
                val path = downloadUri.toString()
                if(!path.equals("")){
                    Glide.with(this)
                        .load(path)
                        .into(binding.recipeImageImageView)
                }
            }
            binding.recipeCaloriesEditText.setText(recipe.info.calories.toString())
            binding.recipeCarbsEditText.setText(recipe.info.carbs.toString())
            binding.recipeFatsEditText.setText(recipe.info.fats.toString())
            binding.recipeProteinEditText.setText(recipe.info.protein.toString())
        }

        binding.acceptButton.setOnClickListener {
            if(checkFields()){
                val lastName = Datasource.getCurrentRecipe().name
                var nameRepeated=false
                var idRepeated=false
                if(Datasource.getClickOnRecipe()){
                    //Modificaci??n
                    for (recipe in Datasource.recipes){
                        if(!lastName.equals(Datasource.newRecipe.name)){
                            if (recipe.equals(Datasource.newRecipe.name)) nameRepeated = true
                        }
                    }
                    if (!nameRepeated){
                        uploadRecipeData()
                    }else{
                        Toast.makeText(activity, "El nombre ya existe", Toast.LENGTH_LONG).show();
                    }
                }else{
                    //Es adici??n
                    for (recipe in Datasource.recipes){
                        if (recipe.equals(Datasource.newRecipe.name)) nameRepeated = true
                        if (Datasource.mapOfRecipes[recipe].equals(Datasource.newRecipeId)) idRepeated = true
                    }
                    if(nameRepeated || idRepeated){
                        Toast.makeText(activity, "El nombre o id ya existen", Toast.LENGTH_LONG).show();
                    }else{
                        uploadRecipeData()
                    }
                }
            }else{
                Toast.makeText(activity, "Faltan datos por llenar", Toast.LENGTH_LONG).show();
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

    fun attachData(){
        Datasource.newRecipe.info.calories = Integer.parseInt(binding.recipeCaloriesEditText.text.toString())
        Datasource.newRecipe.info.carbs = Integer.parseInt(binding.recipeCarbsEditText.text.toString())
        Datasource.newRecipe.info.fats = Integer.parseInt(binding.recipeFatsEditText.text.toString())
        Datasource.newRecipe.info.protein = Integer.parseInt(binding.recipeProteinEditText.text.toString())
        Datasource.newRecipe.imageResourceId = if(!fileUri.toString().equals("") && clickDone) downloadUri.toString() else Datasource.getCurrentRecipe().imageResourceId
    }

    fun addRecipe(){
        try{
            database.child("recipes").child(Datasource.newRecipeId).setValue(Datasource.newRecipe)
            Toast.makeText(activity, "Receta agregada exitosamente", Toast.LENGTH_LONG).show();
            findNavController().navigate(R.id.action_createRecipeInformationFragment_to_nav_recipes)
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(activity, "La receta no pudo ser agregada", Toast.LENGTH_LONG).show();
        }
    }

    fun updateRecipe(){
        try{
            database.child("recipes").child(Datasource.mapOfRecipes[Datasource.getCurrentRecipe().name].toString()).setValue(Datasource.newRecipe)
            Toast.makeText(activity, "Receta modificada exitosamente", Toast.LENGTH_LONG).show();
            findNavController().navigate(R.id.action_createRecipeInformationFragment_to_nav_recipes)
        }catch (e : Exception){
            Log.d("Exception",e.toString())
            Toast.makeText(activity, "La receta no pudo ser modificada", Toast.LENGTH_LONG).show();
        }
    }

    fun uploadRecipeData(){
        if(!fileUri.toString().equals("") && clickDone){
            progressDialog.show()
            val storageRef = storage.reference
            val file = fileUri
            val recipesImageRef = storageRef.child("images/${fileUri.lastPathSegment}")
            val uploadTask = recipesImageRef.putFile(file)

            uploadTask.addOnFailureListener{
                progressDialog.dismiss()
                Toast.makeText(activity, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }.addOnSuccessListener {
                downloadUri = Uri.parse(fileUri.lastPathSegment)
                attachData()
                if(Datasource.getClickOnRecipe()){
                    progressDialog.dismiss()
                    //Es modificaci??n
                    updateRecipe()
                }else{
                    progressDialog.dismiss()
                    //Es adici??n
                    addRecipe()
                }
                Toast.makeText(activity, "Imagen cargada", Toast.LENGTH_SHORT).show();
            }
        }else{
            attachData()
            updateRecipe()
        }
    }

    //M??todos para poder elegir la imagen
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
                Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
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