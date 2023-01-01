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
import com.google.firebase.database.FirebaseDatabase

var idCost=""
var nazwaCost=""
var valuecCost=0
var typCost=""

class AdapterListCost(private val view: View,private val dataArray: ArrayList<DatabaseRowListCost>): RecyclerView.Adapter<AdapterListCost.MyViewHolder>() {
    private lateinit var myRef: DatabaseReference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.database_row_list_cost,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.nazwa.setText(dataArray[holder.adapterPosition].nazwa)
        holder.typ.setText(dataArray[holder.adapterPosition].typ)
        holder.id.setText(dataArray[holder.adapterPosition].id)
        holder.value.setText(dataArray[holder.adapterPosition].value.toString())

        holder.opis.setOnClickListener {
            nazwaCost=holder.nazwa.text.toString()
            typCost=holder.typ.text.toString()
            idCost=holder.id.text.toString()
            valuecCost=Integer.parseInt(holder.value.text.toString())
            view.findNavController().navigate(R.id.action_listCostFragment_to_descriptionListCostFragment)

        }
    }
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nazwa = view.findViewById<TextView>(R.id.nazwa_cost)
        val id = view.findViewById<TextView>(R.id.id_cost)
        val typ = view.findViewById<TextView>(R.id.typCost)
        val value = view.findViewById<TextView>(R.id.value_cost)
        val opis = view.findViewById<Button>(R.id.opis_cost)
    }
}