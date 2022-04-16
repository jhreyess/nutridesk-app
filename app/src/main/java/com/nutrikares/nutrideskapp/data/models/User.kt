package com.nutrikares.nutrideskapp.data.models

class User{

    var name: String = ""
    var role: String = ""

}

data class UnLoggedUser(private var email: String, private var password: String ){
    fun getEmail() = email
    fun getPassword() = password
}