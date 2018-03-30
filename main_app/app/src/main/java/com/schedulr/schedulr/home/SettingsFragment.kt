package com.schedulr.schedulr.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.schedulr.schedulr.R
import com.schedulr.schedulr.base.BaseFragment
import com.schedulr.schedulr.login.LoginActivity

class SettingsFragment : BaseFragment() {

    private lateinit var signOut: Button


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate( R.layout.fragment_settings, container, false )

        setupUI( view )
        setSignOutListener()

        return view
    }



    override fun setupUI( view: View) {
        signOut = view.findViewById( R.id.sign_out )
    }



    private fun setSignOutListener() {
        signOut.setOnClickListener({
            FirebaseAuth.getInstance().signOut()
            val intent = Intent( activity, LoginActivity::class.java )
            startActivity( intent )
            activity.finish()
        })
    }
}