package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.example.praca_inzynierska.CoWybralesCost
import com.example.praca_inzynierska.DatabaseRowListCost
import com.example.praca_inzynierska.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_add_list_cost.view.*


var CoWybralesCostAdd = ""
class AddListCostFragment : Fragment() {

private lateinit var myRef:DatabaseReference
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_list_cost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dodajCost=view.findViewById<Button>(R.id.DodajKoszt)
        spinner=view.findViewById(R.id.typCost)

        val options= arrayOf("Bieżące","Przyjęcia towaru","Składowania","Kompletowania i wydania produktów","inne")

        spinner.adapter= ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,options)

        spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                CoWybralesCostAdd =options.get(p2)//opcja ktora wybralem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        dodajCost.setOnClickListener{
            var nazwa=view.findViewById<EditText>(R.id.nazwa_cost).text.toString()
            var id=view.findViewById<EditText>(R.id.id_cost).text.toString()
            var value=Integer.parseInt(view.findViewById<EditText>(R.id.value_cost).text.toString())
            var typ= CoWybralesCostAdd



            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_kosztow")
            if(id!=""){
                val firebaseInput= DatabaseRowListCost(id,nazwa,value,typ)
                myRef.child(id).setValue(firebaseInput)
            }
            findNavController().navigate(R.id.action_addListCostFragment_to_listCostFragment)
            Toast.makeText(requireContext(),"Dodano koszty!",Toast.LENGTH_LONG).show()
        }

    }
}