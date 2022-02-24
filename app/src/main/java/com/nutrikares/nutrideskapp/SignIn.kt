package com.nutrikares.nutrideskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.nutrikares.nutrideskapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

    }

    fun login(view: View){ loginUser() }

    private fun loginUser(){
        val email = binding.userEmail.editText?.text.toString()
        val password = binding.userPassword.editText?.text.toString()
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                    task -> if(task.isSuccessful){ launchApp() }
                }
        }
    }

    private fun launchApp(){
        startActivity(Intent(this, MainActivity::class.java))
    }
}