package com.example.praca_inzynierska

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.assetpacks.v
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
var tytulProduktu=""
var typProduktu=""
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
        holder.tytul.setText(dataArray[holder.adapterPosition].tytul)
        holder.typ_produktu.setText(dataArray[holder.adapterPosition].typ_produktu)
        var cos=1
        //jak przycisne usun w liscie to usunie dany rekord
        holder.usun.setOnClickListener{
//            var uid=auth.currentUser?.uid.toString()
            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_produktow")
            var tekst=ListaRekordowDoModyfikacji[position].toString()
            var b=tekst.indexOf("tytul")+6
            var c=tekst[b].toString()
            for(i in 0..100){
                b++
                if(tekst[b]==','){
                    break;
                }else{
                    c=c+tekst[b]
                }

            }
            //tu usuwam rekord o tytule c w firebase
            myRef.child(c).removeValue()
        }
        //przycisk przenoszacy do opisu
        holder.opis.setOnClickListener {
            tytulProduktu=holder.tytul.text.toString()
            typProduktu=holder.typ_produktu.text.toString()
            view.findNavController().navigate(R.id.action_ListProductsFragment_to_descriptionListProductsFragment)
        }
    }
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tytul = view.findViewById<TextView>(R.id.tytul)
        val typ_produktu = view.findViewById<TextView>(R.id.typ_produktu)
        val usun = view.findViewById<Button>(R.id.usun)
        val opis = view.findViewById<Button>(R.id.opis)
    }
}