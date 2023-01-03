package com.example.praca_inzynierska

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isGone
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_cost_list.*
import java.util.*
import kotlin.collections.ArrayList

//zmienne globalne
var ListaRekordowDoModyfikacjiCost=ArrayList<DatabaseRowListCost>()
var ListaSzukanychRekordowCost=ArrayList<DatabaseRowListCost>()
var CoWybralesCost="Bieżące"
var lastidcost=1

class ListCostFragment : Fragment() {

    //inicjalizacja komponentow z xmla i z firebase i inne
    private lateinit var myRef: DatabaseReference
    private val auth = FirebaseAuth.getInstance()
    private lateinit var dodaj2: Button
    private lateinit var PokazCalaListe: Button
    private lateinit var WyborZRozwijanejListy: Button
    private lateinit var Szukaj: Button
    private lateinit var poleSzukania: EditText
    private  lateinit var listOfItems:ArrayList<DatabaseRowListCost>
    private lateinit var spinner: Spinner
    private lateinit var DateYearSpinner:Spinner
    private lateinit var DateMonthSpinner:Spinner
    private lateinit var ConfirmDate:Button
    private lateinit var CancelDate:Button
    private lateinit var CalculateCostExact:Button
    private lateinit var LayoutDate:LinearLayout


    private lateinit var calendar: Calendar
    var datasumamiesiac=0
    var datasumarok=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cost_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //inicjalizacja komponentow z xmla
        dodaj2=view.findViewById(R.id.dodajCost)
        spinner=view.findViewById(R.id.spinnerCost)
        PokazCalaListe=view.findViewById(R.id.PokazCalaListeCost)
        WyborZRozwijanejListy=view.findViewById(R.id.SzukajTypuCost)
        Szukaj=view.findViewById(R.id.szukajCost)
        LayoutDate=view.findViewById(R.id.LayoutDatePick)


        DateYearSpinner=view.findViewById(R.id.DateYear)
        DateMonthSpinner=view.findViewById(R.id.DateMonth)

        val optionsmonth= arrayOf("Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień")
        val optionsyear= arrayOf("2022","2023","2024","2025","2026","2027","2028","2029","2030")

        DateYear.adapter= ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,optionsyear)

        var wybranyrok=""
        var wybranymiesiac=0
        DateYearSpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                wybranyrok=optionsyear.get(p2)//opcja ktora wybralem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        DateMonthSpinner.adapter= ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,optionsmonth)

        DateMonthSpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                wybranymiesiac=p2//opcja ktora wybralem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        var suma=0

        poleSzukania=view.findViewById(R.id.poleSzukaniaCost)
        ConfirmDate=view.findViewById(R.id.ConfirmCalculate)
        CancelDate=view.findViewById(R.id.CancelCalculate)
        CalculateCostExact=view.findViewById(R.id.sumujCostDate)
        ConfirmDate.visibility=View.GONE
        CancelDate.visibility=View.GONE
        LayoutDate.visibility=View.GONE

        CalculateCostExact.setOnClickListener(){
            CalculateCostExact.visibility=View.GONE
            ConfirmDate.visibility=View.VISIBLE
            CancelDate.visibility=View.VISIBLE
            LayoutDate.visibility=View.VISIBLE
        }
        CancelDate.setOnClickListener(){
            CalculateCostExact.visibility=View.VISIBLE
            CancelDate.visibility=View.GONE
            ConfirmDate.visibility=View.GONE
            LayoutDate.visibility=View.GONE
        }
        ConfirmDate.setOnClickListener(){
            CalculateCostExact.visibility=View.VISIBLE
            ConfirmDate.visibility=View.GONE
            CancelDate.visibility=View.GONE
            LayoutDate.visibility=View.GONE
            datasumamiesiac=wybranymiesiac
            datasumarok=wybranyrok
            for (i in listOfItems){
                if(i.data.split('.')[1]==(wybranymiesiac+1).toString()&&i.data.split('.')[2]==wybranyrok)
                {
                    suma+=i.value
                }
            }
            var sumakosztow=""
            when(wybranymiesiac+1) {
                1 -> sumakosztow="Styczniu"
                2 -> sumakosztow="Lutym"
                3 -> sumakosztow="Marcu"
                4 -> sumakosztow="Kwietniu"
                5 -> sumakosztow="Maju"
                6 -> sumakosztow="Czerwcu"
                7 -> sumakosztow="Lipcu"
                8 -> sumakosztow="Sierpniu"
                9 -> sumakosztow="Wrześniu"
                10 -> sumakosztow="Październiku"
                11 -> sumakosztow="Listopadzie"
                12 -> sumakosztow="Grudniu"
            }
            Toast.makeText(requireContext(),"W $sumakosztow $wybranyrok koszty wynoszą $suma!",Toast.LENGTH_SHORT).show()
            suma=0
        }


        //opcje w rozwijanej liscie
        val options= arrayOf("Bieżące","Przyjęcia towaru","Składowania","Kompletowania i wydania produktów","inne")

        spinner.adapter= ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,options)

        spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                CoWybralesCost=options.get(p2)//opcja ktora wybralem
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        //wchodze do pliku w realtime database lista_produktow i tam cos robie
        val firebase= FirebaseDatabase.getInstance()
        myRef=firebase.getReference("lista_kosztow")
//wyglad recyclerview
        recyclerviewcost.layoutManager= GridLayoutManager(context, 1)
//jak przycisne dodaj to dodaje do fire base jeden rekord z wybraną opcją z listy rozwijanej i tytułem
        dodajCost.setOnClickListener {

            findNavController().navigate(R.id.action_listCostFragment_to_addListCostFragment)

        }
//aktualizacja

        dodanieDoAdapteraWszystkichElementowCost(view)

        PokazCalaListe.setOnClickListener {
            dodanieDoAdapteraWszystkichElementowCost(view)
        }
//        //zalezy od wybranej opcji w liscie rozwijanej, jak przycisniemy ten przycisk to sortuje po wybranej opcji liste
        WyborZRozwijanejListy.setOnClickListener {
            ListaRekordowDoModyfikacjiCost.clear()

            for(i in listOfItems)
            {
            if(CoWybralesCost==i.typ){
                ListaRekordowDoModyfikacjiCost.add(i)
            }
            }
            //aktualizowanie, żeby zmienić rekordy w adapterze
            setupAdapter(view,ListaRekordowDoModyfikacjiCost)//view po to żeby można było wykorzystac w adapterze findnavcontroller
        }

        Szukaj.setOnClickListener(){
            ListaSzukanychRekordowCost.clear()
            if(poleSzukania.text.isNotBlank()){


            if(ListaRekordowDoModyfikacjiCost.isNotEmpty()){
                for(i in ListaRekordowDoModyfikacjiCost){
                    if(i.nazwa.lowercase().contains(poleSzukania.text.toString().lowercase())){
                        ListaSzukanychRekordowCost.add(i)
                    }
                }
                setupAdapter(view, ListaSzukanychRekordowCost)
            }
            else{
                for (i in listOfItems){
                    if(i.nazwa.lowercase().contains(poleSzukania.text.toString().lowercase())){
                        ListaSzukanychRekordowCost.add(i)
                    }
                }
                setupAdapter(view, ListaSzukanychRekordowCost)
            }
        }
            else{
            setupAdapter(view,listOfItems)
        }
        }

    }
    private  fun dodanieDoAdapteraWszystkichElementowCost(view: View){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfItems=ArrayList<DatabaseRowListCost>()
                for(i in dataSnapshot.children){
                    var newRow=i.getValue(DatabaseRowListCost::class.java)
                    listOfItems.add(newRow!!)
                }
                ListaRekordowDoModyfikacjiCost.clear()
                wielkosc2 =listOfItems.size

                setupAdapter(view,listOfItems)
            }

        })
    }
    //aktualizowanie adaptera
    private  fun setupAdapter(view: View,arrayData:ArrayList<DatabaseRowListCost>){
        //to trzeba dodac zeby blad nie wyskakiwal kiedy jest null
        if(recyclerviewcost==null){

        }else{
            recyclerviewcost.adapter= AdapterListCost(view,arrayData)
            if(arrayData.isNotEmpty()){
            lastidcost=Integer.parseInt(arrayData.last().id)
            }
            else{
                lastidcost=0
            }
        }

    }

}