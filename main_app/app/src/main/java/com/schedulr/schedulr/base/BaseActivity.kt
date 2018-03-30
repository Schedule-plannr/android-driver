package com.schedulr.schedulr.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.firebase.client.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.schedulr.schedulr.login.LoginActivity
import com.schedulr.schedulr.home.MainActivity

open class BaseActivity: AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firestore: FirebaseFirestore
    lateinit var docRef: DocumentReference
    lateinit var colRef: CollectionReference



    override fun onCreate( savedInstanceState : Bundle? ) {
        super.onCreate( savedInstanceState )

        Firebase.setAndroidContext( this )
    }



    fun goToDashBoard( context: Context) {
        val intent = Intent( context, MainActivity::class.java )
        startActivity( intent )
        finish()
    }



    fun goToSignIn( context: Context) {
        val intent = Intent( context, LoginActivity::class.java )
        startActivity( intent )
        finish()
    }



    fun makeLongSnack(view: View, message: String ) {
        val snack = Snackbar.make( view, message, Snackbar.LENGTH_LONG )
        snack.show()
    }
}