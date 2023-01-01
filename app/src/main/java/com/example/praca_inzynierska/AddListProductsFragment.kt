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
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_add_list_products.view.*


class AddListProductsFragment : Fragment() {

private lateinit var myRef:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_list_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var przyjecieTowaru=view.findViewById<Button>(R.id.przyjecieTowaru)
        view.findViewById<TextView>(R.id.id).text=(numeracjaId.toInt()+1).toString()
        przyjecieTowaru.setOnClickListener{
            var nazwa=view.findViewById<EditText>(R.id.nazwa).text.toString()

            var id=(numeracjaId.toInt()+1).toString()
            var stan=view.findViewById<EditText>(R.id.stan).text.toString()
            var waga=view.findViewById<EditText>(R.id.waga).text.toString()
            var gabaryt=view.findViewById<EditText>(R.id.gabaryt).text.toString()
            var wagaOp=view.findViewById<EditText>(R.id.wagaOpakowania).text.toString()
            var wymog=view.findViewById<EditText>(R.id.wymogi).text.toString()
if(nazwa!=""&&stan!=""){


    val firebase= FirebaseDatabase.getInstance()
    myRef=firebase.getReference("lista_produktow")
    if(id!=""){
        val firebaseInput=DatabaseRowListProducts(id,nazwa,stan,waga,gabaryt,"przyjęcia towaru",wagaOp,wymog)
        myRef.child(id).setValue(firebaseInput)
    }
    Toast.makeText(requireContext(),"Przyjęto dostawe do magazynu!",Toast.LENGTH_SHORT).show()
    numeracjaId=(numeracjaId.toInt()+1).toString()
    view.findViewById<TextView>(R.id.id).text=(numeracjaId.toInt()+1).toString()


}else{
    Toast.makeText(requireContext(),"Musisz podać nazwę i stan!",Toast.LENGTH_SHORT).show()
}


        }
    }
}