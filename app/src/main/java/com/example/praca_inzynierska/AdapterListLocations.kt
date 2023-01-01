package com.example.praca_inzynierska

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

var nazwaLokalizacjiGlobalna=""
var idLokalizacjiGlobalna=""
var numeracjaIdLokalizacji="0"
class AdapterListLocations(private val view: View, private val dataArray: ArrayList<DatabaseRowListProductsLocation2>): RecyclerView.Adapter<AdapterListLocations.MyViewHolder2>() {
    private lateinit var myRef: DatabaseReference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val inflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.database_row_list_locations,parent,false)
        return MyViewHolder2(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {

        holder.nazwaLokalizacji.setText(dataArray[holder.adapterPosition].nazwaLokalizacji)
        holder.idLokalizacji.setText(dataArray[holder.adapterPosition].idLokalizacji)
        numeracjaIdLokalizacji=dataArray[dataArray.size-1].idLokalizacji



        holder.modyfikuj.setOnClickListener {
            nazwaLokalizacjiGlobalna=holder.nazwaLokalizacji.text.toString()
            idLokalizacjiGlobalna=holder.idLokalizacji.text.toString()
            view.findNavController().navigate(R.id.action_listLocationsFragment_to_modifyLocationsListFragment)
        }
    }
    inner class MyViewHolder2(view: View): RecyclerView.ViewHolder(view){
        val nazwaLokalizacji = view.findViewById<TextView>(R.id.nazwaLokalizacji)
        val idLokalizacji = view.findViewById<TextView>(R.id.idLokalizacji)
        val modyfikuj = view.findViewById<Button>(R.id.modyfikujLokalizacje)
    }
}