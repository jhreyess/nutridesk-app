package com.nutrikares.nutrideskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.nutrikares.nutrideskapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.data.Datasource
import com.nutrikares.nutrideskapp.data.models.*
import com.nutrikares.nutrideskapp.presenters.SignInPresenter
import com.nutrikares.nutrideskapp.presenters.SignInUser
import com.nutrikares.nutrideskapp.views.SignInUserView

class SignIn : AppCompatActivity(), SignInUserView {

    private lateinit var binding: ActivitySignInBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val presenter: SignInPresenter<SignInUserView> by lazy {
        SignInUser(this, auth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference
        binding.signInBtn.setOnClickListener { login() }
    }

    override fun onStart() {
        super.onStart()
        val user = Firebase.auth.currentUser
        if (user != null) {
            launchApp()
        }
    }

    private fun login() {
        val email = binding.userEmail.editText?.text.toString()
        val password = binding.userPassword.editText?.text.toString()
        presenter.logIn(email, password)
    }

    override fun launchApp() {

        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            val queryRef = database.child("users").child(uid)
            queryRef.keepSynced(true)
            queryRef.get().addOnSuccessListener { data ->
                val userInfo = data.getValue(UserInfo::class.java)!!
                Datasource.setUserInfo(userInfo)
                val activity = if (userInfo.role == "admin") AdminMainActivity::class.java else MainActivity::class.java
                val intent = Intent(baseContext, activity)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }



    override fun showLoadingScreen() {
        binding.errorMessage.text = ""
        binding.loadingScreen.visibility = View.VISIBLE
    }

    override fun showEmptyEmailError() { binding.errorMessage.setText(R.string.emptyEmailError) }
    override fun showEmptyPasswordError() { binding.errorMessage.setText(R.string.emptyPasswordError) }
    override fun showEmptyFieldsError() { binding.errorMessage.setText(R.string.emptyFieldsError) }
    override fun showWrongCredentialsError() {
        binding.loadingScreen.visibility = View.INVISIBLE
        binding.errorMessage.setText(R.string.wrongCredentialsError)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}