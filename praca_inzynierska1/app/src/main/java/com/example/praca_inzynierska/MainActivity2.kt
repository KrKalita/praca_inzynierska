package com.example.praca_inzynierska

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profil ->{
                //mozna dodac przechodzenie do fragmentow w menu, ale to nie wiem jak?????????????
                CzyWlaczycProfil=true
//                var obiekt=MainPageFragment()
//                var tr = supportFragmentManager.beginTransaction()
//                tr.replace(R.id.cos11, obiekt)
//                tr.commit()
//                findNavController(this, R.id.my_nav2).navigate(R.id.action_mainPageFragment2_to_profilFragment2)
//                CzyWlaczycProfil=true
//                var obiekt=MainPageFragment()
//                obiekt.onResume()
            }
            R.id.strona_glowna -> {

            }
            R.id.logOut -> {
                fbAuth.signOut()
                finish()
            }
        }
        return false
    }



}