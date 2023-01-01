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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ModifyProductListFragment : Fragment() {

    private lateinit var myRef:DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_modify_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.strefa).text=strefaProduktu
        view.findViewById<TextView>(R.id.id).text=idProduktu
        view.findViewById<EditText>(R.id.nazwa).setText(nazwaProduktu)
        view.findViewById<EditText>(R.id.stan).setText(stanMagazynowy)
        view.findViewById<EditText>(R.id.gabaryt).setText(gabarytProduktu)
        view.findViewById<EditText>(R.id.waga).setText(wagaProduktu)
        view.findViewById<EditText>(R.id.wagaOpakowania).setText(wagaOpakowania)
        view.findViewById<EditText>(R.id.wymogi).setText(wymogi)

        var modyfikuj=view.findViewById<Button>(R.id.modyfikuj)
        modyfikuj.setOnClickListener {

            var nazwa=view.findViewById<EditText>(R.id.nazwa).text.toString()
            var stan=view.findViewById<EditText>(R.id.stan).text.toString()
            var gabaryt=view.findViewById<EditText>(R.id.gabaryt).text.toString()
            var waga=view.findViewById<EditText>(R.id.waga).text.toString()
            var wagaOp=view.findViewById<EditText>(R.id.wagaOpakowania).text.toString()
            var wymog=view.findViewById<EditText>(R.id.wymogi).text.toString()

            if(nazwa!=""&&stan!=""){
                val firebase= FirebaseDatabase.getInstance()
                myRef=firebase.getReference("lista_produktow")
                if(idProduktu!=""){

                    myRef.child(idProduktu).child("id").setValue(idProduktu)
                    myRef.child(idProduktu).child("gabaryt").setValue(gabaryt)
                    myRef.child(idProduktu).child("nazwa").setValue(nazwa)
                    myRef.child(idProduktu).child("stan").setValue(stan)
                    myRef.child(idProduktu).child("strefa").setValue(strefaProduktu)
                    myRef.child(idProduktu).child("waga").setValue(waga)
                    myRef.child(idProduktu).child("wagaOpakowania").setValue(wagaOp)
                    myRef.child(idProduktu).child("wymogi").setValue(wymog)

                }
                nazwaProduktu=nazwa
                stanMagazynowy=stan
                gabarytProduktu=gabaryt
                wagaProduktu=waga
                wagaOpakowania=wagaOp
                wymogi=wymog
                Toast.makeText(requireContext(),"Modyfikacja zakończona!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_modifyProductListFragment_to_descriptionListProductsFragment)
            }else{
                Toast.makeText(requireContext(),"Musisz podać nazwę i stan!", Toast.LENGTH_SHORT).show()
            }


        }


    }
}