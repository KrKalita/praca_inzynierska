package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list_locations.*
import kotlinx.android.synthetic.main.fragment_products_list.*


class ListLocationsFragment : Fragment() {

    private lateinit var myRef: DatabaseReference
    private  lateinit var listOfItemsLocations:ArrayList<DatabaseRowListProductsLocation2>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_locations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dodajLokalizacje=view.findViewById<Button>(R.id.dodajLokalizacje)
        var recyclerviewLocations=view.findViewById<RecyclerView>(R.id.recyclerviewLocations)
        //aktualizacja
        val firebase= FirebaseDatabase.getInstance()
        myRef=firebase.getReference("lista_produktow")

        recyclerviewLocations.layoutManager= GridLayoutManager(context, 1)

        dodanieDoAdapteraWszystkichElementow(view)

        dodajLokalizacje.setOnClickListener {

            findNavController().navigate(R.id.action_listLocationsFragment_to_addListLocationsFragment)
        }

    }
    private  fun dodanieDoAdapteraWszystkichElementow(view: View){

        //ma wyciagac tylko lokalizacje
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfItemsLocations=ArrayList()
                for(i in dataSnapshot.children){
                     var idElementu=i.child("id").getValue().toString()
                    if(idElementu==idProduktu){
                        for(j in 1..100){
                            var newRow28=i.child("listaLokalizacji").child(j.toString()).getValue(DatabaseRowListProductsLocation2::class.java)
                            if(newRow28==null){
                                break
                            }
                            listOfItemsLocations.add(newRow28!!)
                        }

                    }
                }
                setupAdapter(view,listOfItemsLocations)
            }

        })
    }
    //aktualizowanie adaptera
    private  fun setupAdapter(view: View,arrayData:ArrayList<DatabaseRowListProductsLocation2>){
        //to trzeba dodac zeby blad nie wyskakiwal kiedy jest null
        if(recyclerviewLocations==null){

        }else{
            recyclerviewLocations.adapter= AdapterListLocations(view,arrayData)
        }

    }


}