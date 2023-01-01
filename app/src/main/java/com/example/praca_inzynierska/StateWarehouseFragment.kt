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


class StateWarehouseFragment : Fragment() {
    private lateinit var myRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_state_warehouse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    var kompletowanie2=view.findViewById<Button>(R.id.kompletowanie2)
        view.findViewById<TextView>(R.id.stanMagazynowy).text=stanMagazynowy

        kompletowanie2.setOnClickListener {
            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_produktow")
            var iloscStanMagazynowy=view.findViewById<EditText>(R.id.iloscStanMagazynowy).text.toString()

            if(iloscStanMagazynowy!=""){
                if(iloscStanMagazynowy.toInt()<stanMagazynowy.toInt()){
                    //dodanie produktu do strefy kompletowania i wydawania produktów
                    val firebaseInput=DatabaseRowListProducts(
                        (numeracjaId.toInt()+1).toString(),
                        nazwaProduktu,
                        iloscStanMagazynowy,
                        wagaProduktu,
                        gabarytProduktu,"kompletowania i wydawania produktów",
                        wagaOpakowania,
                        wymogi
                    )
                    myRef.child((numeracjaId.toInt()+1).toString()).setValue(firebaseInput);
                    //usuwa ilosc stanu magazynowego
                    myRef.child(idProduktu).child("stan").setValue((stanMagazynowy.toInt()-iloscStanMagazynowy.toInt()).toString());

                    Toast.makeText(requireContext(),"Produkty przeniesiono do strefy kompletowania i wydawania produktów", Toast.LENGTH_SHORT).show()
                    strefaProduktu ="kompletowania i wydawania produktów"
                    findNavController().navigate(R.id.action_stateWarehouseFragment_to_ListProductsFragment)

                }else if(iloscStanMagazynowy.toInt()==stanMagazynowy.toInt()){
                    //dodanie produktu do strefy kompletowania i wydawania produktów
                    val firebaseInput=DatabaseRowListProducts(
                        (numeracjaId.toInt()+1).toString(),
                        nazwaProduktu,
                        iloscStanMagazynowy,
                        wagaProduktu,
                        gabarytProduktu,"kompletowania i wydawania produktów",
                        wagaOpakowania,
                        wymogi
                    )
                    myRef.child((numeracjaId.toInt()+1).toString()).setValue(firebaseInput);

                    //jesli stan magazynowy jest rowny 0 to usuwa dany produkt ze strefy skladowania,
                    myRef.child(idProduktu).removeValue()
                    Toast.makeText(requireContext(),"Produkty przeniesiono do strefy kompletowania i wydawania produktów", Toast.LENGTH_SHORT).show()
                    strefaProduktu ="kompletowania i wydawania produktów"
                    findNavController().navigate(R.id.action_stateWarehouseFragment_to_ListProductsFragment)
                }else{
                    Toast.makeText(requireContext(),"Nie ma tyle produktów na stanie!!!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"Podaj stan!", Toast.LENGTH_SHORT).show()
            }

        }

    }
}