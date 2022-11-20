package com.example.praca_inzynierska

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
var wyciagnietyZawod=""
var wyciagnieteImie=""
var wyciagnieteNazwisko=""
var wyciagnietyEmail=""
var wyciagnietyDostep="user"

class MainPageFragment : Fragment() {
    private var fbAuth= FirebaseAuth.getInstance()
    private lateinit var myRef:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page_main, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    var lista_produktow=view.findViewById<Button>(R.id.lista_produktow)

        lista_produktow.setOnClickListener {
            findNavController().navigate(R.id.ListProductsFragment)
        }
        var lista_uzytkownikow=view.findViewById<Button>(R.id.lista_uzytkownikow)

        //to do kontrolowania czy ktos ma dostep do listy_uzytkownikow, jesli administartor to ma dostep
        var k=0
        lista_uzytkownikow.setOnClickListener {
            if(wyciagnietyDostep=="user"){
//                lista_uzytkownikow.isEnabled=false
//            lista_uzytkownikow.isEnabled=true
    if(k==0){
        Toast.makeText(requireContext(),"Kliknij jeszcze raz!",Toast.LENGTH_LONG).show()
        k++
    }else{
        Toast.makeText(requireContext(),"Ta opcja jest przeznaczona tylko dla ADMINISTRATORA",Toast.LENGTH_LONG).show()
    k--
    }

            }else{
                findNavController().navigate(R.id.action_mainPageFragment2_to_listUsersFragment)
            }

        }
        var przejscie_profile=view.findViewById<Button>(R.id.przejscie_profile)
        przejscie_profile.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment2_to_profilFragment2)
        }
        var uid=fbAuth.currentUser?.uid.toString()
        var firebase= FirebaseDatabase.getInstance()
        myRef=firebase.getReference("lista_uzytkownikow")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                var cos=""
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var uid=fbAuth.currentUser?.uid.toString()
                for(i in dataSnapshot.children){
                    val newRow3=i.child("uid").getValue()
                    if(newRow3==uid){
                        //to wykorzystuje w profilFragment
                        wyciagnietyZawod=i.child("zawod").getValue().toString()
                        wyciagnieteImie=i.child("imie").getValue().toString()
                        wyciagnieteNazwisko=i.child("nazwisko").getValue().toString()
                        wyciagnietyEmail=i.child("email").getValue().toString()
                        wyciagnietyDostep=i.child("dostep").getValue().toString()
                    }


                }


            }

        })

    }


}