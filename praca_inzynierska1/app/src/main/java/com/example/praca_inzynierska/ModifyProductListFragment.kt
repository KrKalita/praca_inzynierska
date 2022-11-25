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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ModifyProductListFragment : Fragment() {

    private lateinit var myRef:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modify_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.strefa).text=strefaProduktu
        view.findViewById<TextView>(R.id.id).text=idProduktu
        view.findViewById<EditText>(R.id.nazwa).setText(nazwaProduktu)
        var modyfikuj=view.findViewById<Button>(R.id.modyfikuj)
        modyfikuj.setOnClickListener {
            var nazwa=view.findViewById<EditText>(R.id.nazwa).text.toString()
            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_produktow")
            if(idProduktu!=""){
                val firebaseInput=DatabaseRowListProducts(idProduktu,nazwa,strefaProduktu)
                myRef.child(idProduktu).setValue(firebaseInput)
            }
            nazwaProduktu=nazwa
            Toast.makeText(requireContext(),"Modyfikacja zakończona!", Toast.LENGTH_LONG).show()
        }

    }
}