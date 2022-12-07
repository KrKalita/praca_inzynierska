package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DescriptionListProductsFragment : Fragment() {

    private lateinit var myRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description_list_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.nazwa).text=nazwaProduktu
        view.findViewById<TextView>(R.id.strefa).text=strefaProduktu
        view.findViewById<TextView>(R.id.id).text=idProduktu
        view.findViewById<TextView>(R.id.waga).text= wagaProduktu
        view.findViewById<TextView>(R.id.stan).text= stanMagazynowy
        view.findViewById<TextView>(R.id.gabaryt).text= gabarytProduktu

        var myRef: DatabaseReference
        val firebase= FirebaseDatabase.getInstance()
        myRef=firebase.getReference("lista_produktow")
        var id=view.findViewById<TextView>(R.id.id).text.toString()

        var modyfikuj=view.findViewById<TextView>(R.id.modyfikuj)
        var skladowanie=view.findViewById<TextView>(R.id.skladowanie)
        var kompletowanie=view.findViewById<TextView>(R.id.kompletowanie)
        var wydawanie=view.findViewById<TextView>(R.id.wydawanie)
        modyfikuj.setOnClickListener{
            findNavController().navigate(R.id.action_descriptionListProductsFragment_to_modifyProductListFragment)
        }

        if (strefaProduktu == "przyjęcia towaru") {
            skladowanie.setOnClickListener {
                val firebaseInput=DatabaseRowListProducts(idProduktu, nazwaProduktu, stanMagazynowy,
                    wagaProduktu, gabarytProduktu,"składowania")
                myRef.child(id).setValue(firebaseInput);

            }
        }
        if (strefaProduktu == "składowania") {
            kompletowanie.setOnClickListener {

                var id=view.findViewById<TextView>(R.id.id).text.toString()
                val firebaseInput=DatabaseRowListProducts(idProduktu, nazwaProduktu, stanMagazynowy,
                    wagaProduktu, gabarytProduktu,"kompletowania i wydawania produktów")
                myRef.child(id).setValue(firebaseInput);

            }
        }
        if (strefaProduktu == "kompletowania i wydawania produktów") {
            wydawanie.setOnClickListener {
                myRef.child(id).removeValue()
                Toast.makeText(requireContext(),"Produkty wydane z magazynu", Toast.LENGTH_LONG).show()
            }
        }






    }
}