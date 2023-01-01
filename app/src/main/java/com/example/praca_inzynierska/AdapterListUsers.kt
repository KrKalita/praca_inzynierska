package com.example.praca_inzynierska

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdapterListUsers ( val dataArray: ArrayList<DatabaseRowListUsers>):RecyclerView.Adapter<Holder>(){
    private lateinit var myRef: DatabaseReference
    private val auth = FirebaseAuth.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.database_row_list_users,parent,false) as View

        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.imie.setText(dataArray[holder.adapterPosition].imie)
        holder.nazwisko.setText(dataArray[holder.adapterPosition].nazwisko)
        holder.zawod.setText(dataArray[holder.adapterPosition].zawod)
        holder.email.setText(dataArray[holder.adapterPosition].email)
        holder.dostep.setText(dataArray[holder.adapterPosition].dostep)
        var uidDoUsuniecia=dataArray[holder.adapterPosition].uid
        //usuwa uzytkownika tylko z listy, a z firebase nie(mozna dodac kod, albo administartor usunie jeszcze w firebase w authetication!!!!!)
        var uid=auth.currentUser?.uid.toString()
        holder.modyfikuj.setOnClickListener {
            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_uzytkownikow")
            myRef.child(uidDoUsuniecia).child("zawod").setValue(CoWybrales2)
            myRef.child(uidDoUsuniecia).child("dostep").setValue(CoWybralesdostepSpinner)
        }
        holder.usun.setOnClickListener {
            var uid=auth.currentUser?.uid.toString()
            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_uzytkownikow")
            //tu usuwam rekord w firebase
            myRef.child(uidDoUsuniecia).removeValue()
        }
    }

    override fun getItemCount(): Int {

        return dataArray.size
    }

}

class Holder(itemView: View):RecyclerView.ViewHolder(itemView){
    val imie:TextView
    val nazwisko:TextView
    val zawod:TextView
    val email:TextView
    val usun:Button
    val modyfikuj:Button
    val dostep:TextView

    init{
        imie= itemView.findViewById<TextView>(R.id.imie)
        nazwisko=itemView.findViewById<TextView>(R.id.nazwisko)
        zawod=itemView.findViewById<TextView>(R.id.zawod)
        email=itemView.findViewById<TextView>(R.id.email)
        usun=itemView.findViewById<Button>(R.id.usun)
        modyfikuj=itemView.findViewById<Button>(R.id.modyfikuj)
        dostep= itemView.findViewById<TextView>(R.id.dostep)

    }

}