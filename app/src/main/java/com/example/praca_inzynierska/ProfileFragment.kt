package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ProfilFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    var imie=view.findViewById<TextView>(R.id.imie)
        var nazwisko=view.findViewById<TextView>(R.id.nazwisko)
        var email=view.findViewById<TextView>(R.id.email)
        var zawod=view.findViewById<TextView>(R.id.zawod)
        var modyfikuj=view.findViewById<Button>(R.id.modyfikuj)
        var dostep=view.findViewById<TextView>(R.id.dostep)
        modyfikuj.setOnClickListener {
            findNavController().navigate(R.id.action_profilFragment2_to_modifyUserListFragment)
        }
        imie.text=wyciagnieteImie
        nazwisko.text=wyciagnieteNazwisko
        email.text=wyciagnietyEmail
        zawod.text=wyciagnietyZawod
        dostep.text=wyciagnietyDostep

    }

}