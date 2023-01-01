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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ModifyLocationsListFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_modify_locations_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    var modyfikujLokalizacje=view.findViewById<Button>(R.id.modyfikujLokalizacje10)
        var idLokalizacji=view.findViewById<TextView>(R.id.idLokalizacji3)


        view.findViewById<EditText>(R.id.nazwaLokalizacji3).setText(nazwaLokalizacjiGlobalna)
        view.findViewById<TextView>(R.id.idLokalizacji3).text=idLokalizacjiGlobalna

        modyfikujLokalizacje.setOnClickListener {
            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_produktow")
            var nazwaLokalizacji=view.findViewById<EditText>(R.id.nazwaLokalizacji3).text.toString()
            if(nazwaLokalizacji!=""){
                if(numeracjaIdLokalizacji==""){
                    numeracjaIdLokalizacji="0"
                }

                val firebaseInput3=DatabaseRowListProductsLocation2(idLokalizacjiGlobalna,nazwaLokalizacji)
                myRef.child(idProduktu).child("listaLokalizacji").child(idLokalizacjiGlobalna).setValue(firebaseInput3)
                Toast.makeText(requireContext(),"Zmodyfikowano lokalizacje.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"Podaj nazwę lokalizacji!", Toast.LENGTH_SHORT).show()
            }
            }
    }
}