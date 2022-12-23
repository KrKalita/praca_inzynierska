package com.example.praca_inzynierska

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.navigation.*
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.GridLayoutManager as GridLayoutManager1

var CzyWlaczycProfil=false

class MainActivity2 : AppCompatActivity() {
//    //inicjalizacja komponentow z xmla i z firebase i inne
    private val fbAuth = FirebaseAuth.getInstance()
    private lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navController = navHostFragment.findNavController()
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profil ->{
                navController.navigate(R.id.action_global_profilFragment2)
            }
            R.id.strona_glowna -> {
                navController.navigate(R.id.action_global_mainPageFragment2)
            }
            R.id.logOut -> {
                fbAuth.signOut()
                finish()
            }
        }
        return false
    }



}