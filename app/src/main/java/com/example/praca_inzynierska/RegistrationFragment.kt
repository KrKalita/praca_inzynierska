package com.example.praca_inzynierska

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegistrationFragment : BaseFragment() {
    private val REG_DEBUG = "REG_DEBUG"
    private val fbAuth = FirebaseAuth.getInstance()
    private lateinit var myRef:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.zarejestruj2).apply {
            setOnClickListener {
                val email = view.findViewById<TextInputEditText>(R.id.email2).text?.trim().toString()
                val pass = view.findViewById<TextInputEditText>(R.id.haslo2).text?.trim().toString()
                val repeatPass = view.findViewById<TextInputEditText>(R.id.powtorzHaslo).text?.trim().toString()
                if(email!=""&&pass!=""&&repeatPass!=""){//zabezpiecznie
                    if(pass==repeatPass){//czy hasło jest równe hasle powtórz hasło, jesli tak to wykonaj
                        fbAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnSuccessListener {authRes ->
//dodanie danych dla danego uzytkownika przy rejestracji
                                var uid=fbAuth.currentUser?.uid.toString()
                                var email=fbAuth.currentUser?.email.toString()
                                val firebase= FirebaseDatabase.getInstance()
                                myRef=firebase.getReference("lista_uzytkownikow")
                                val firebaseInput=DatabaseRowListUsers("wpisz","wpisz",email,"wpisz",uid,"user")
                                myRef.child(uid).setValue(firebaseInput)

                                if(authRes.user != null){
                                    startApp()
                                }
                            }
                            .addOnFailureListener{exc ->
                                Snackbar.make(requireView(), "Zła składnia maila(musi być np: cos@gmail.com) lub różne hasła lub złe hasła(musi być 6 znaków).", Snackbar.LENGTH_SHORT)
                                    .show()
                                //komunikat o błędzie
                                Log.d(REG_DEBUG, exc.message.toString())
                            }


                    }
                }

            }
        }
    }

}