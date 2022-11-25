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
        var cos=1
        //jak przycisne usun w liscie to usunie dany rekord
//        holder.usun.setOnClickListener{
////            var uid=auth.currentUser?.uid.toString()
//            val firebase= FirebaseDatabase.getInstance()
//            myRef=firebase.getReference("lista_produktow")
//            var tekst=ListaRekordowDoModyfikacji[position].toString()
//            var b=tekst.indexOf("id")+3
//            var c=tekst[b].toString()
//            for(i in 0..100){
//                b++
//                if(tekst[b]==','){
//                    break;
//                }else{
//                    c=c+tekst[b]
//                }
//
//            }
//            //tu usuwam rekord o tytule c w firebase
//            myRef.child(c).removeValue()
//        }
        //przycisk przenoszacy do opisu
        holder.opis.setOnClickListener {
            nazwaProduktu=holder.nazwa.text.toString()
            strefaProduktu=holder.strefa.text.toString()
            idProduktu=holder.id.text.toString()
            view.findNavController().navigate(R.id.action_ListProductsFragment_to_descriptionListProductsFragment)
        }
    }
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nazwa = view.findViewById<TextView>(R.id.nazwa)
        val id = view.findViewById<TextView>(R.id.id)
        val strefa = view.findViewById<TextView>(R.id.strefa)
//        val usun = view.findViewById<Button>(R.id.usun)
        val opis = view.findViewById<Button>(R.id.opis)
    }
}