package com.schedulr.schedulr.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class NetworkRequest {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var fbdb: FirebaseFirestore
    lateinit var docRef: DocumentReference
    lateinit var colRef: CollectionReference



    init {
        firebaseAuth = FirebaseAuth.getInstance()


    }
}