package com.example.praca_inzynierska

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.praca_inzynierska.typCost
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class ModifyCostListFragment : Fragment() {

    private lateinit var myRef:DatabaseReference
    private lateinit var spinner: Spinner
    private lateinit var dateButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_modify_cost_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.id_cost).text= idCost
        view.findViewById<EditText>(R.id.nazwa_cost).setText(nazwaCost)
        dateButton=view.findViewById(R.id.date_cost_button)
        var data=""
        dateButton.text= dateCost

        spinner=view.findViewById(R.id.typCost)

        val options= arrayOf("Bieżące","Przyjęcia towaru","Składowania","Kompletowania i wydania produktów","inne")

        spinner.adapter= ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,options)

        if(typCost=="Bieżące"){
            spinner.setSelection(0)
        }else if(typCost=="Przyjęcia towaru"){
            spinner.setSelection(1)
        }else if(typCost=="Składowania"){
            spinner.setSelection(2)
        }else if(typCost=="Kompletowania i wydania produktów"){
            spinner.setSelection(3)
        }else{
            spinner.setSelection(4)
        }

        view.findViewById<EditText>(R.id.value_cost).setText(valuecCost.toString())

        spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                typCost =options.get(p2)//opcja ktora wybralem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        var modyfikuj=view.findViewById<Button>(R.id.modyfikuj_cost)
        var cancel_cost=view.findViewById<Button>(R.id.cancel_cost)
        modyfikuj.setOnClickListener {
            val nazwa =view.findViewById<EditText>(R.id.nazwa_cost).text.toString()
            val value =view.findViewById<EditText>(R.id.value_cost).text.toString()
            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_kosztow")
            if(nazwa.isNotBlank()&& value.isNotBlank()) {
                val firebaseInput = DatabaseRowListCost(
                    idCost, nazwa, dateCost,
                    Integer.parseInt(value), typCost
                )
                nazwaCost=nazwa
                valuecCost=value.toInt()
                myRef.child(idCost).setValue(firebaseInput)
                Toast.makeText(requireContext(),"Modyfikacja zakończona!", Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.action_modifyCostListFragment_to_descriptionListCostFragment)
            }
            else{

                Toast.makeText(requireContext(),"Wypełnij wszystkie pola!", Toast.LENGTH_LONG).show()

            }
        }
        cancel_cost.setOnClickListener(){
            findNavController().navigate(R.id.action_modifyCostListFragment_to_descriptionListCostFragment)
        }
        class SelectDateFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                val yy = Integer.parseInt(dateCost.split('.')[2])
                val mm = Integer.parseInt(dateCost.split('.')[1])-1
                val dd = Integer.parseInt(dateCost.split('.')[0])
                return DatePickerDialog(requireActivity(), this, yy, mm, dd)
            }

            override fun onDateSet(view: DatePicker, yy: Int, mm: Int, dd: Int) {
                populateSetDate(yy, mm+1 , dd)
            }

            fun populateSetDate(y: Int, m: Int, d: Int) {
                data="$d.$m.$y"
                dateCost=data
                dateButton.text = data
            }
        }
        dateButton.setOnClickListener(){
            val newFragment: DialogFragment = SelectDateFragment()
            newFragment.show(requireFragmentManager(), "DatePicker")

        }

    }
}