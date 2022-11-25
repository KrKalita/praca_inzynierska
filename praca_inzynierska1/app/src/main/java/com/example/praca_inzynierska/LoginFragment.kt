package com.example.praca_inzynierska

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
var CzyDostep=""

class LoginFragment : BaseFragment() {
    private val fbAuth = FirebaseAuth.getInstance()
    private val LOG_DEUBG = "LOG_DEBUG"
    private lateinit var myRef:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //jak kline w zarejestruj to przejdz do fragmentu zaloguj
        view.findViewById<Button>(R.id.zarejestruj1).apply {
            setOnClickListener {

                var firebase= FirebaseDatabase.getInstance()
                myRef=firebase.getReference("dostepDoRejestracji")
                var kkk=1
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        var cos=""
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var uid=fbAuth.currentUser?.uid.toString()
                        for(i in dataSnapshot.children){

                            if(kkk==1){
                                val newRow10=i.getValue()
                                if(newRow10=="1"){
                                    CzyDostep="1"
                                    Toast.makeText(requireContext(),"Rejestracja włączona!!",
                                        Toast.LENGTH_LONG).show()
                                    view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
                                }else{
                                    CzyDostep="0"
                                    Toast.makeText(requireContext(),"Rejestracja wyłączona!!",
                                        Toast.LENGTH_LONG).show()
                                }
                            }
                            kkk=0

                        }


                    }

                })
                kkk=1





            }
        }
        view.findViewById<Button>(R.id.zaloguj1).apply {
            setOnClickListener {
                val email = view.findViewById<TextInputEditText>(R.id.email1).text?.trim().toString()
                val pass =  view.findViewById<TextInputEditText>(R.id.haslo1).text?.trim().toString()
                if(email!=""&&pass!=""){//zabezpieczenie
                    //logowanie się przez podanie emaila i hasła
                    fbAuth.signInWithEmailAndPassword(email, pass)
                        .addOnSuccessListener { authRes ->


                            if(authRes.user != null) startApp()
                        }
                        .addOnFailureListener{ exc ->
                            Snackbar.make(requireView(), "Nie ma takiego emaila", Snackbar.LENGTH_SHORT)
                                .show()
                            //komunikat o błędzie
                            Log.d(LOG_DEUBG, exc.message.toString())
                        }
                }

            }
        }
    }

}