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


class AddListLocationsFragment : Fragment() {
    private lateinit var myRef:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_list_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dodajLokalizacje=view.findViewById<Button>(R.id.dodajLokalizacje4)
        var nazwaLokalizacji=view.findViewById<EditText>(R.id.nazwaLokalizacji2).text.toString()
        var cos=(numeracjaIdLokalizacji.toInt()+1).toString()
        view.findViewById<TextView>(R.id.idLokalizacji2).text=cos
        dodajLokalizacje.setOnClickListener {
            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_produktow")
            nazwaLokalizacji=view.findViewById<EditText>(R.id.nazwaLokalizacji2).text.toString()
if(nazwaLokalizacji!=""){
    if(numeracjaIdLokalizacji==""){
        numeracjaIdLokalizacji="0"
    }

    val firebaseInput3=DatabaseRowListProductsLocation2((numeracjaIdLokalizacji.toInt()+1).toString(),nazwaLokalizacji)
    myRef.child(idProduktu).child("listaLokalizacji").child((numeracjaIdLokalizacji.toInt()+1).toString()).setValue(firebaseInput3)
    Toast.makeText(requireContext(),"Dodano lokalizacje.", Toast.LENGTH_SHORT).show()
    findNavController().navigate(R.id.action_addListLocationsFragment_to_listLocationsFragment)

}else{
    Toast.makeText(requireContext(),"Podaj nazwę lokalizacji!", Toast.LENGTH_SHORT).show()

}
}
    }
}