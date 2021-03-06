package com.nutrikares.nutrideskapp.presenters

import com.google.firebase.auth.FirebaseAuth
import com.nutrikares.nutrideskapp.data.models.UnLoggedUser
import com.nutrikares.nutrideskapp.views.SignInUserView
import com.nutrikares.nutrideskapp.views.View

interface SignInPresenter<T: View> : Presenter<T> {
    fun logIn(email: String, password: String)
}

class SignInUser(
    override var view: SignInUserView?,
    private val auth: FirebaseAuth) : SignInPresenter<SignInUserView> {

    override fun logIn(email: String, password: String) {

        val user = UnLoggedUser(email, password)

        when(LoginValidator.validateInput(user)){
            InputValidate.EMPTY_FIELDS -> view?.showEmptyFieldsError()
            InputValidate.EMPTY_EMAIL -> view?.showEmptyEmailError()
            InputValidate.EMPTY_PASSWORD -> view?.showEmptyPasswordError()
            InputValidate.VALID_INPUT -> {
                view?.showLoadingScreen()
                LoginValidator.logUser(user, auth){ result ->
                    when(result){
                        LoginStatus.SUCCESS -> view?.launchApp()
                        LoginStatus.WRONG_CREDENTIALS -> view?.showWrongCredentialsError()
                    }
                }
            }
        }
    }

}

object LoginValidator {

    fun validateInput(user: UnLoggedUser) : InputValidate{
        with(user){
            if(getEmail().isEmpty() && getPassword().isEmpty()) return InputValidate.EMPTY_FIELDS
            if(getEmail().isEmpty()) return InputValidate.EMPTY_EMAIL
            if(getPassword().isEmpty()) return InputValidate.EMPTY_PASSWORD
        }
        return InputValidate.VALID_INPUT
    }

    fun logUser(user: UnLoggedUser, auth: FirebaseAuth, loginSuccess: (result: LoginStatus) -> Unit){
        auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
            .addOnCompleteListener { task ->
                if(task.isSuccessful) loginSuccess(LoginStatus.SUCCESS)
                else loginSuccess(LoginStatus.WRONG_CREDENTIALS)
            }
    }
}

enum class InputValidate{
    EMPTY_FIELDS,
    EMPTY_EMAIL,
    EMPTY_PASSWORD,
    VALID_INPUT
}

enum class LoginStatus {
    WRONG_CREDENTIALS,
    SUCCESS
}
