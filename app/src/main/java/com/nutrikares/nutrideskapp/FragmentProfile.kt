package com.nutrikares.nutrideskapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.databinding.FragmentProfileBinding

class FragmentProfile : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Bindings
        binding.profileName.text = Datasource.getUserInfo().name
        binding.signOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(activity, SignIn::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        binding.saveBtn.setOnClickListener {
            val user = Firebase.auth.currentUser!!
            val currentPassword = binding.currentPassword.editText!!.text.toString()
            val newPassword = binding.newPassword.editText!!.text.toString()
            val confirmPassword = binding.confirmPassword.editText!!.text.toString()
            if(newPassword.trim() == confirmPassword.trim()){
                val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
                user.reauthenticate(credential).addOnCompleteListener {
                    if(it.isSuccessful){
                        user.updatePassword(newPassword).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(activity, "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).show()
                                val intent = Intent(activity, SignIn::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }else{
                                Toast.makeText(activity, "Algo salió mal! Cambios no aplicados", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(activity, "Contraseña actual inválida", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(activity, "Contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}