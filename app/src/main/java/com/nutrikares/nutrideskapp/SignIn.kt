package com.nutrikares.nutrideskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.nutrikares.nutrideskapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nutrikares.nutrideskapp.models.User
import com.nutrikares.nutrideskapp.presenters.SignInPresenter
import com.nutrikares.nutrideskapp.presenters.SignInUser
import com.nutrikares.nutrideskapp.views.SignInUserView

class SignIn : AppCompatActivity(), SignInUserView {

    private lateinit var binding: ActivitySignInBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val presenter: SignInPresenter<SignInUserView> by lazy {
        SignInUser(this, auth, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        binding.signInBtn.setOnClickListener { login() }
    }

    private fun login(){
        val email = binding.userEmail.editText?.text.toString()
        val password = binding.userPassword.editText?.text.toString()
        presenter.logIn(email, password)
    }

    override fun launchApp(){

        val userId = auth.currentUser?.uid.toString()
        if (!TextUtils.isEmpty(userId)){
            lateinit var userInfo: User
            val queryRef = database.child("users").child(userId)
            val listener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                       userInfo = snapshot.getValue(User::class.java)!!
                        val activity = when(userInfo.getRole()){
                            "admin" -> AdminMainActivity::class.java
                            else -> MainActivity::class.java
                        }

                        startActivity(Intent(this@SignIn, activity))
                    }

                    override fun onCancelled(err: DatabaseError) {
                        Toast.makeText(this@SignIn,"Something went wrong!",Toast.LENGTH_SHORT).show()
                    }
                }

            queryRef.addListenerForSingleValueEvent(listener)
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