package com.nutrikares.nutrideskapp.models

class User{

    private lateinit var name: String
    private lateinit var role: String

    fun getName() = name
    fun getRole() = role
}

data class UnLoggedUser(private var email: String, private var password: String ){
    fun getEmail() = email
    fun getPassword() = password
}