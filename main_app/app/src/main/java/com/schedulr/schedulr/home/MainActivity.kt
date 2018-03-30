package com.schedulr.schedulr.home

import android.Manifest
import android.app.Fragment
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.schedulr.schedulr.R
import com.schedulr.schedulr.base.BaseActivity
import com.schedulr.schedulr.network.NetworkRequest

class MainActivity : BaseActivity() {

    lateinit var navigator: BottomNavigationView

    private lateinit var settings: SettingsFragment

    private lateinit var network: NetworkRequest

    companion object {
        private const val SIGN_IN_CODE = 3773
        private const val TAG = "HomeActivity"
        private const val REQUEST_INTERNET = 200
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_NETWORK_STATE ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, arrayOf( Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE ), REQUEST_INTERNET)
        }

        network = NetworkRequest()

        firebaseAuth = network.firebaseAuth

        when( null == firebaseAuth.currentUser ) {
            true -> {
                goToSignIn( this )
            }
            false -> {
                firestore = FirebaseFirestore.getInstance()
                setupUI()
            }
        }
    }



    fun setupUI() {
        navigator = this.findViewById( R.id.navigation )
        val userType = getUserType()
    }



    fun setupForCustomer() {
        navigator.inflateMenu(R.menu.customer_menu)
        navigator.setOnNavigationItemSelectedListener( navigationForCustomer )
    }



    fun setupForEmployee() {
        navigator.inflateMenu(R.menu.employee_menu)
        navigator.setOnNavigationItemSelectedListener( navigationForEmployee )
    }



    private fun changeFragment( fragment: Fragment ) {
        when( null == fragment ) {
            true -> return
            false -> {
                val fragmentManager = fragmentManager
                when {
                    null != fragmentManager -> {
                        val fragmentTransaction = fragmentManager.beginTransaction()
                        when {
                            null != fragmentTransaction -> {
                                fragmentTransaction.replace(R.id.fragmentContainer, fragment )
                                fragmentTransaction.commit()
                            }
                        }
                    }
                }
            }
        }
    }



    private fun getUserType(): String {
        var userType = "-1"
        colRef = firestore.collection( "Users" )
        docRef = colRef.document( firebaseAuth.currentUser!!.uid )

        docRef.get().addOnCompleteListener({
            when( it.isSuccessful ) {
                true -> {
                    val user = it.result
                    userType = when ( null != user && user.exists() ) {
                        true -> user.data["type"].toString()
                        false -> "-1"
                    }
                    settings = SettingsFragment()
                    when( userType ) {
                        "-1" -> goToSignIn( this )
                        "customer" -> setupForCustomer()
                        "employee" -> setupForEmployee()
                    }
                }
                false -> -1
            }
        })
        return userType
    }



    private val navigationForCustomer = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when ( item.itemId ) {
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_appointments -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                changeFragment( settings )
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }



    private val navigationForEmployee = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when ( item.itemId ) {
            R.id.navigation_calendar -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_appointments -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_portfolio -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                changeFragment( settings )
                return@OnNavigationItemSelectedListener true
            }
        }
        return@OnNavigationItemSelectedListener false
    }
}
