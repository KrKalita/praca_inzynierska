package com.example.praca_inzynierska

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


var CoWybralesCostAdd = ""
class AddListCostFragment : Fragment() {

private lateinit var myRef:DatabaseReference
    private lateinit var spinner: Spinner
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var dateButton: Button
    private lateinit var calendar: Calendar
    var data=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_list_cost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var dodajCost=view.findViewById<Button>(R.id.DodajKoszt)
        spinner=view.findViewById(R.id.typCost)
        dateButton= view.findViewById(R.id.DateCost)
        dateButton.setText(getDate())

        val options= arrayOf("Bieżące","Przyjęcia towaru","Składowania","Kompletowania i wydania produktów","inne")

        spinner.adapter= ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,options)

        spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                CoWybralesCostAdd =options.get(p2)//opcja ktora wybralem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        class SelectDateFragment : DialogFragment(), OnDateSetListener {
            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                val calendar = Calendar.getInstance()
                val yy = calendar[Calendar.YEAR]
                val mm = calendar[Calendar.MONTH]
                val dd = calendar[Calendar.DAY_OF_MONTH]
                return DatePickerDialog(requireActivity(), this, yy, mm, dd)
            }

            override fun onDateSet(view: DatePicker, yy: Int, mm: Int, dd: Int) {
                populateSetDate(yy, mm+1 , dd)
            }

            fun populateSetDate(y: Int, m: Int, d: Int) {
                data="$d.$m.$y"
                dateButton.text = data
            }
        }
        dateButton.setOnClickListener(){
            val newFragment: DialogFragment = SelectDateFragment()
            newFragment.show(requireFragmentManager(), "DatePicker")

        }

        dodajCost.setOnClickListener{
            var nazwa=view.findViewById<EditText>(R.id.nazwa_cost).text.toString()
            var id=view.findViewById<EditText>(R.id.id_cost).text.toString()
            var value=Integer.parseInt(view.findViewById<EditText>(R.id.value_cost).text.toString())
            var typ= CoWybralesCostAdd


            val firebase= FirebaseDatabase.getInstance()
            myRef=firebase.getReference("lista_kosztow")
            if(id!=""){
                val firebaseInput= DatabaseRowListCost(id,nazwa,data,value,typ)
                myRef.child(id).setValue(firebaseInput)
            }
            findNavController().navigate(R.id.action_addListCostFragment_to_listCostFragment)
            Toast.makeText(requireContext(),"Dodano koszty!",Toast.LENGTH_LONG).show()
        }

    }

    private fun getDate():String {
        calendar = Calendar.getInstance()
        var y =calendar.get(Calendar.YEAR)
        var m =calendar.get(Calendar.MONTH)
        var d =calendar.get(Calendar.DAY_OF_MONTH)
        m+=1
        return("$d.$m.$y")
    }
}