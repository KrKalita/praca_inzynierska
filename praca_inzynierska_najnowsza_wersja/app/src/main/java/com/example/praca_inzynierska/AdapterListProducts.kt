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
var nazwaProduktu=""
var strefaProduktu=""
var idProduktu=""
var wagaProduktu=""
var stanMagazynowy=""
var gabarytProduktu=""
class AdapterListProducts(private val view: View,private val dataArray: ArrayList<DatabaseRowListProducts>): RecyclerView.Adapter<AdapterListProducts.MyViewHolder>() {
    private lateinit var myRef: DatabaseReference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.database_row_list_products,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.nazwa.setText(dataArray[holder.adapterPosition].nazwa)
        holder.strefa.setText(dataArray[holder.adapterPosition].strefa)
        holder.id.setText(dataArray[holder.adapterPosition].id)


        holder.opis.setOnClickListener {
            nazwaProduktu=holder.nazwa.text.toString()
            strefaProduktu=holder.strefa.text.toString()
            idProduktu=holder.id.text.toString()
            wagaProduktu="test"
            gabarytProduktu="test"
            stanMagazynowy="test"
            view.findNavController().navigate(R.id.action_ListProductsFragment_to_descriptionListProductsFragment)
        }
    }
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nazwa = view.findViewById<TextView>(R.id.nazwa)
        val id = view.findViewById<TextView>(R.id.id)
        val strefa = view.findViewById<TextView>(R.id.strefa)
        val opis = view.findViewById<Button>(R.id.opis)
    }
}