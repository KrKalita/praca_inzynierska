package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_add_list_products.view.*


class AddListProductsFragment : Fragment() {

private lateinit var myRef:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_list_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//    var nazwa=view.findViewById<EditText>(R.id.nazwa).text.toString()
//        var id=view.findViewById<EditText>(R.id.id).text.toString()
//        var przyjecieTowaru=view.findViewById<Button>(R.id.przyjecieTowaru)
        var przyjecieTowaru=view.findViewById<Button>(R.id.przyjecieTowaru)

        przyjecieTowaru.setOnClickListener{
            var nazwa=view.findViewById<EditText>(R.id.nazwa).text.toString()
            var id=view.findViewById<EditText>(R.id.id).text.toString()
            var stan=view.findViewById<EditText>(R.id.stan).text.toString()
            var waga=view.findViewById<EditText>(R.id.waga).text.toString()
            var gabaryt=view.findViewById<EditText>(R.id.gabaryt).text.toString()


            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_produktow")
            if(id!=""){
                val firebaseInput=DatabaseRowListProducts(id,nazwa,stan,waga,gabaryt,"przyjęcia towaru")
                myRef.child(id).setValue(firebaseInput)
            }
            Toast.makeText(requireContext(),"Przyjęto dostawe do magazynu!",Toast.LENGTH_LONG).show()
        }
    }
}