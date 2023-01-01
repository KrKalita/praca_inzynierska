package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.database_row_list_users.*


class DescriptionListCostFragment : Fragment() {

    private lateinit var myRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description_list_cost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.id_cost).text= idCost
        view.findViewById<TextView>(R.id.nazwa_cost).text= nazwaCost
        view.findViewById<TextView>(R.id.typCost).text= typCost
        view.findViewById<TextView>(R.id.value_cost).text= valuecCost.toString()
        var usun_cost=view.findViewById<Button>(R.id.usun_cost)
        var wroc_cost=view.findViewById<Button>(R.id.wroc_cost)


        var modyfikuj=view.findViewById<TextView>(R.id.modyfikuj_cost)
        modyfikuj.setOnClickListener{
            findNavController().navigate(R.id.action_descriptionListCostFragment_to_modifyCostListFragment)
        }

        usun_cost.setOnClickListener {

            var firebase=FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_kosztow")
            myRef.child(idCost).removeValue()

            Toast.makeText(requireContext(),"Modyfikacja zakończona!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_descriptionListCostFragment_to_listCostFragment)
        }
        wroc_cost.setOnClickListener(){
            findNavController().navigate(R.id.action_descriptionListCostFragment_to_listCostFragment)
        }







    }
}