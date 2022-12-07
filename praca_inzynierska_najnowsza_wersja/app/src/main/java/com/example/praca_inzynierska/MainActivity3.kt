package com.example.praca_inzynierska

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth

class MainActivity3 : AppCompatActivity() {
    private val fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
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