package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController


class DescriptionListProductsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description_list_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.nazwa).text=nazwaProduktu
        view.findViewById<TextView>(R.id.strefa).text=strefaProduktu
        view.findViewById<TextView>(R.id.id).text=idProduktu
        var modyfikuj=view.findViewById<TextView>(R.id.modyfikuj)
        modyfikuj.setOnClickListener{
            findNavController().navigate(R.id.action_descriptionListProductsFragment_to_modifyProductListFragment)
        }


    }
}