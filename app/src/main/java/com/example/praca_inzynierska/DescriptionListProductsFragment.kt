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
import com.google.firebase.database.*

var nieWykonuj=0
var id3=""
var nazwa3=""
var strefa3=""
var stan3=""
var gabaryt3=""
var waga3=""
var wagaOpakowania3=""
var wymogi3=""
var dodanyStan=0
class DescriptionListProductsFragment : Fragment() {

    private lateinit var myRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        view.findViewById<TextView>(R.id.wagaOpakowania).text= wagaOpakowania
        view.findViewById<TextView>(R.id.wymogi).text= wymogi

        var myRef: DatabaseReference
        val firebase= FirebaseDatabase.getInstance()
        myRef=firebase.getReference("lista_produktow")
        var id=view.findViewById<TextView>(R.id.id).text.toString()

        var modyfikuj=view.findViewById<TextView>(R.id.modyfikuj)
        var skladowanie=view.findViewById<TextView>(R.id.skladowanie)
        var kompletowanie=view.findViewById<TextView>(R.id.kompletowanie)
        var wydawanie=view.findViewById<TextView>(R.id.wydawanie)

        var przyciskListaLokalizacji=view.findViewById<TextView>(R.id.przyciskListaLokalizacji)
        if (strefaProduktu == "składowania") {
            przyciskListaLokalizacji.setOnClickListener {
                numeracjaIdLokalizacji="0"
                findNavController().navigate(R.id.action_descriptionListProductsFragment_to_listLocationsFragment)
            }
        }
        modyfikuj.setOnClickListener{
            findNavController().navigate(R.id.action_descriptionListProductsFragment_to_modifyProductListFragment)
        }

        if (strefaProduktu == "przyjęcia towaru") {
            skladowanie.setOnClickListener {
                nieWykonuj=0

                val firebase= FirebaseDatabase.getInstance()
                myRef=firebase.getReference("lista_produktow")
                var powtorzenie=0
var wykonajJedenRaz=0
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {

                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(wykonajJedenRaz==0){
                        var stan2=""
                        for(i in dataSnapshot.children){
                            val newRow=i.getValue(DatabaseRowListProducts::class.java)
                            var id2=i.child("id").getValue().toString()
                            var nazwa2=i.child("nazwa").getValue().toString()
                            var strefa2=i.child("strefa").getValue().toString()
                            stan2=i.child("stan").getValue().toString()

                            var gabaryt2=i.child("gabaryt").getValue().toString()
                            var waga2=i.child("waga").getValue().toString()
                            var wagaOpakowania2=i.child("wagaOpakowania").getValue().toString()
                            var wymogi2=i.child("wymogi").getValue().toString()

                            if(nazwa2==nazwaProduktu&&strefa2=="składowania"){
                                if(idProduktu!=id2){
                                    if(stan2==""&&stanMagazynowy==""){

                                    }else{

                                            dodanyStan=stan2.toInt()+stanMagazynowy.toInt()
                                            id3=id2
                                            nazwa3=nazwa2
                                            strefa3=strefa2
                                            stan3=stan2
                                            gabaryt3=gabaryt2
                                            waga3=waga2
                                            wagaOpakowania3=wagaOpakowania2
                                            wymogi3=wymogi2


                                    }

                                    powtorzenie++
                                }

                            }



                            wykonajJedenRaz=1


                            if(powtorzenie==1){
                                myRef.child(id).removeValue()
                                myRef.child(id3).child("stan").setValue(dodanyStan.toString());
                            }

                            if(powtorzenie==0){
                                val firebaseInput=DatabaseRowListProducts(idProduktu, nazwaProduktu, stanMagazynowy,
                                    wagaProduktu, gabarytProduktu,"składowania", wagaOpakowania, wymogi)
                                myRef.child(id).setValue(firebaseInput);
                                strefaProduktu="składowania"
                            }
                        }}

                    }
                })

                Toast.makeText(requireContext(),"Produkty przeniesiono do strefy składowania lub dodało stan magazynowy do danego produktu", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_descriptionListProductsFragment_to_ListProductsFragment)
            }
        }
        if (strefaProduktu == "składowania") {
            kompletowanie.setOnClickListener {
                findNavController().navigate(R.id.action_descriptionListProductsFragment_to_stateWarehouseFragment)

            }
        }
        if (strefaProduktu == "kompletowania i wydawania produktów") {
            wydawanie.setOnClickListener {
                myRef.child(id).removeValue()
                Toast.makeText(requireContext(),"Produkty wydane z magazynu", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_descriptionListProductsFragment_to_ListProductsFragment)
            }
        }






    }
}