package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ModifyUserListFragment : Fragment() {

    private var fbAuth= FirebaseAuth.getInstance()
    private lateinit var myRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_modify_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var imie=view.findViewById<EditText>(R.id.imie)
        var nazwisko=view.findViewById<EditText>(R.id.nazwisko)
        var email=view.findViewById<TextView>(R.id.email)
        var zawod=view.findViewById<TextView>(R.id.zawod)
        var dostep=view.findViewById<TextView>(R.id.dostep)
        var modyfikuj=view.findViewById<Button>(R.id.modyfikuj)
        var cancel_user=view.findViewById<Button>(R.id.cancel_user)


        imie.setText(wyciagnieteImie)
        nazwisko.setText(wyciagnieteNazwisko)
        email.text=wyciagnietyEmail
        zawod.text=wyciagnietyZawod
        dostep.text=wyciagnietyDostep
        var a=""
        var b=""
        var uid=""
        //jesli klikne to zmieni dwa edittext i tez zmieni w realtime database w firebase
        modyfikuj.setOnClickListener {
            a=imie.text.toString()
            b=nazwisko.text.toString()

            uid=fbAuth.currentUser?.uid.toString()
            var firebase=FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_uzytkownikow")
            myRef.child(uid).child("imie").setValue(a)//zmiana wartosci dla parametru imie w bazie z takim uid
            myRef.child(uid).child("nazwisko").setValue(b)
            wyciagnieteImie=a
            wyciagnieteNazwisko=b
            Toast.makeText(requireContext(),"Modyfikacja zakończona!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_modifyUserListFragment_to_profilFragment2)
        }
        cancel_user.setOnClickListener(){
            findNavController().navigate(R.id.action_modifyUserListFragment_to_profilFragment2)
        }
    }

}