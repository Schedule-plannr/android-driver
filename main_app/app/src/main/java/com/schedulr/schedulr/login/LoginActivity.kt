package com.schedulr.schedulr.login

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.schedulr.schedulr.R
import com.schedulr.schedulr.base.BaseActivity

class LoginActivity: BaseActivity() {
    private lateinit var credentialsInput: LinearLayout
    private lateinit var buttonInput: LinearLayout
    private lateinit var emailInput: TextInputLayout
    private lateinit var passwordInput: TextInputLayout
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    private var isSigningIn = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        setupUI()
        setSignInListeners()
    }



    private fun setupUI() {
        credentialsInput = findViewById(R.id.credentialsLayout)
        buttonInput = findViewById(R.id.landing_buttons)
        emailInput = findViewById(R.id.email_sign_in)
        passwordInput = findViewById(R.id.password_sign_in)
        signInButton = findViewById(R.id.sign_in)
        signUpButton = findViewById(R.id.sign_up)
    }




    private fun setSignInListeners() {

        signInButton.setOnClickListener({
            val email = emailInput.editText!!.text.toString()
            val password = passwordInput.editText!!.text.toString()
            when( isSigningIn ) {
                true -> when( checkForValidInput( email, password ) ) {
                    true -> {
                        attemptSignIn( email, password )
                    }
                    false -> {
                    }
                }
                false -> {
                    credentialsInput.visibility = View.VISIBLE
                    signUpButton.visibility = View.GONE
                    isSigningIn = true
                }
            }
        })
    }



    private fun checkForValidInput( email: String, password: String ): Boolean {
        emailInput.error = null
        passwordInput.error = null

        when {
            isEmailInvalid( email ) -> {
                emailInput.error = getString(R.string.invalid_email)
                return false
            }
            TextUtils.isEmpty( password ) -> {
                passwordInput.error = getString(R.string.invalid_password)
                return false
            }
        }
        return true
    }



    private fun attemptSignIn( email: String, password: String ) {
        firebaseAuth.signInWithEmailAndPassword( email, password ).addOnCompleteListener({
            when( it.isSuccessful ) {
                true -> goToDashBoard( this )
                false -> makeLongSnack( findViewById(R.id.coordinator), "Sign In Failed" )
            }
        })
    }



    private fun isEmailInvalid( email: String ): Boolean {
        return TextUtils.isEmpty( email ) || email.length < 3 || !email.contains( "@" ) || !email.contains( "." )
    }
}