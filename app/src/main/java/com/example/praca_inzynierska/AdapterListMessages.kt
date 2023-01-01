package com.example.praca_inzynierska

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class AdapterListMessages(private val view: View, private val dataArray: ArrayList<DatabaseRowListProducts>): RecyclerView.Adapter<AdapterListMessages.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListMessages.MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.database_row_list_messages,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {

        return dataArray.size
    }

    override fun onBindViewHolder(holder: AdapterListMessages.MyViewHolder, position: Int) {

        holder.nazwaKomunikatu.setText(dataArray[holder.adapterPosition].nazwa)
        holder.stanKomunikatu.setText(dataArray[holder.adapterPosition].stan)
        holder.idKomunikatu.setText(dataArray[holder.adapterPosition].id)
//zmiana koloru linearlayout
        var a=dataArray[holder.adapterPosition].stan.toInt()
        if(a<6){
            holder.LinearLayoutKolorTla.setBackgroundColor(Color.RED)
        }else{
            holder.LinearLayoutKolorTla.setBackgroundColor(Color.YELLOW)
        }

    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nazwaKomunikatu = view.findViewById<TextView>(R.id.nazwaKomunikatu)
        val idKomunikatu = view.findViewById<TextView>(R.id.idKomunikatu)
        val stanKomunikatu = view.findViewById<TextView>(R.id.stanKomunikatu)
        val LinearLayoutKolorTla = view.findViewById<LinearLayout>(R.id.LinearLayoutKolorTla)
    }
}