package com.nutrikares.nutrideskapp.views

interface View //Marker interface

interface SignInUserView : View {
    fun launchApp()
    fun showLoadingScreen()
    fun showEmptyEmailError()
    fun showEmptyPasswordError()
    fun showEmptyFieldsError()
    fun showWrongCredentialsError()
}

