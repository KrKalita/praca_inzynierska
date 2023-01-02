package com.example.praca_inzynierska

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
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
var CoWybralesCost=""
var wielkoscCost=0
var wielkosc2Cost=0

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
    private lateinit var DateBetween1:Button
    private lateinit var DateBetween2:TextView
    private lateinit var DateBetween3:Button
    private lateinit var DateExact:Button
    private lateinit var ConfirmDate:Button
    private lateinit var CancelDate:Button
    private lateinit var CalculateCostExact:Button
    private lateinit var CalculateCostBetween:Button


    private lateinit var calendar: Calendar
    var datasuma=""

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


        var suma=0

        poleSzukania=view.findViewById(R.id.poleSzukaniaCost)
        DateBetween1=view.findViewById(R.id.DateCostBetween1)
        DateBetween2=view.findViewById(R.id.DateCostBetween2)
        DateBetween3=view.findViewById(R.id.DateCostBetween3)
        DateExact=view.findViewById(R.id.DateCostExact)
        ConfirmDate=view.findViewById(R.id.ConfirmCalculate)
        CancelDate=view.findViewById(R.id.CancelCalculate)
        CalculateCostBetween=view.findViewById(R.id.sumujCostBetween)
        CalculateCostExact=view.findViewById(R.id.sumujCostDate)


        DateBetween1.visibility=View.GONE
        DateBetween2.visibility=View.GONE
        DateBetween3.visibility=View.GONE
        DateExact.visibility=View.GONE
        CalculateCostBetween.visibility=View.GONE//chwila
        ConfirmDate.visibility=View.GONE
        CancelDate.visibility=View.GONE

        CalculateCostExact.setOnClickListener(){
            DateBetween1.visibility=View.GONE
            DateBetween2.visibility=View.GONE
            DateBetween3.visibility=View.GONE
            DateExact.visibility=View.VISIBLE
            CalculateCostExact.visibility=View.GONE
            CalculateCostBetween.visibility=View.GONE
            ConfirmDate.visibility=View.VISIBLE
            CancelDate.visibility=View.VISIBLE
        }
        CalculateCostBetween.setOnClickListener(){
            DateBetween1.visibility=View.VISIBLE
            DateBetween2.visibility=View.VISIBLE
            DateBetween3.visibility=View.VISIBLE
            DateExact.visibility=View.GONE
            CalculateCostExact.visibility=View.GONE
            CalculateCostBetween.visibility=View.GONE
            ConfirmDate.visibility=View.VISIBLE
            CancelDate.visibility=View.VISIBLE
        }
        CancelDate.setOnClickListener(){
            DateBetween1.visibility=View.GONE
            DateBetween2.visibility=View.GONE
            DateBetween3.visibility=View.GONE
            DateExact.visibility=View.GONE
            CalculateCostExact.visibility=View.VISIBLE
            CalculateCostBetween.visibility=View.GONE //chwila
            ConfirmDate.visibility=View.GONE
            CancelDate.visibility=View.GONE

        }
        ConfirmDate.setOnClickListener(){
            DateBetween1.visibility=View.GONE
            DateBetween2.visibility=View.GONE
            DateBetween3.visibility=View.GONE
            DateExact.visibility=View.GONE
            CalculateCostExact.visibility=View.VISIBLE
            CalculateCostBetween.visibility=View.GONE //chwila
            ConfirmDate.visibility=View.GONE
            CancelDate.visibility=View.GONE

            for (i in listOfItems){
                if(i.data.split('.')[1]==datasuma.split('.')[1])
                {
                    suma+=i.value
                }
            }
            var sumakosztow=""
            when(Integer.parseInt(datasuma.split('.')[1])) {
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
            Toast.makeText(requireContext(),"W $sumakosztow koszty wynoszą $suma!",Toast.LENGTH_SHORT).show()
        }


        //opcje w rozwijanej liscie
        val options= arrayOf("Bieżące","Przyjęcia towaru","Składowania","Kompletowania i wydania produktów","inne")

        spinner.adapter= ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,options)

        spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                CoWybralesCost=options.get(p2)//opcja ktora wybralem
                dodanieDoAdapteraWszystkichElementowCost(view)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        class SelectDateFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
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
            var data1=""
            var data2=""


            fun populateSetDate(y: Int, m: Int, d: Int) {
                if(DateExact.visibility==View.VISIBLE){
                    datasuma="$d.$m.$y"
                    when(m) {
                        1 -> DateExact.text="Styczeń $y"
                        2 -> DateExact.text="Luty $y"
                        3 -> DateExact.text="Marzec $y"
                        4 -> DateExact.text="Kwiecień $y"
                        5 -> DateExact.text="Maj $y"
                        6 -> DateExact.text="Czerwiec $y"
                        7 -> DateExact.text="Lipiec $y"
                        8 -> DateExact.text="Sierpień $y"
                        9 -> DateExact.text="Wrzesień $y"
                        10 -> DateExact.text="Październik $y"
                        11 -> DateExact.text="Listopad $y"
                        12 -> DateExact.text="Grudzień $y"
                    }
                }
                if((DateBetween1.text=="Wybierz date"||DateBetween1.text==data1)&&DateBetween1.visibility==View.VISIBLE){
                    data1="$d.$m.$y"
                    DateBetween1.text=data1
                }
                else{
                    data1="Wybierz date"
                    DateBetween1.text=data1
                }
                if((DateBetween3.text=="Wybierz date"||DateBetween3.text==data2)&&DateBetween3.visibility==View.VISIBLE){
                    data2="$d.$m.$y"
                    DateBetween3.text=data2
                }
                else{
                    DateBetween3.text="Wybierz date"
                }
            }
        }
        DateExact.setOnClickListener(){
            val newFragment: DialogFragment = SelectDateFragment()
            newFragment.show(requireFragmentManager(), "DatePicker")

        }
        DateBetween1.setOnClickListener(){
            val newFragment: DialogFragment = SelectDateFragment()
            newFragment.show(requireFragmentManager(), "DatePicker")

        }
        DateBetween3.setOnClickListener(){
            val newFragment: DialogFragment = SelectDateFragment()
            newFragment.show(requireFragmentManager(), "DatePicker")

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
            var wybor=""
            var index=0
            var tab= mutableListOf<Int>()
            for(i in ListaRekordowDoModyfikacjiCost){
//kod do odczytania typ_produktu

                var calyStringRekordu=i.toString()
                var wybor=""
                var pozycja=0
                var tabbb= arrayListOf(0,1,2,3,4,5)
                for (j1 in 0..100){
                    for (j2 in tabbb){
                        wybor=wybor+calyStringRekordu[j2]
                    }
                    if(wybor=="typ"){//kod jest bardzo elastyczny do danej sytuacji i wystarczy podać miejsce w, którym chcesz wycignac wartosc
                        wybor=""
                        pozycja=j1
                        break
                    }else{
                        wybor=""
                    }
                    tabbb.remove(tabbb[0])
                    tabbb.add(tabbb[4]+1)
                }

                for (j3 in pozycja+7..100){
                    if(calyStringRekordu[j3]==','||calyStringRekordu[j3]==')'){
                        break
                    }else {
                        wybor=wybor+calyStringRekordu[j3]
                    }

                }
                if(wybor== CoWybralesCost){
                    index++
                }else{
                    tab.add(index)
                    index++
                }
                wybor=""
            }
            //tab przechowuje wszystkie numery rekordow, ktore beda usuniete w adapterze na potrzeby podgladu
            var kkk=tab.size-1
            for(i in tab){

                var cos= ListaRekordowDoModyfikacjiCost[tab[kkk]]
                ListaRekordowDoModyfikacjiCost.remove(cos)
                kkk--
            }
            //aktualizowanie, żeby zmienić rekordy w adapterze
            setupAdapter(view,ListaRekordowDoModyfikacjiCost)//view po to żeby można było wykorzystac w adapterze findnavcontroller
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
                ListaRekordowDoModyfikacjiCost =listOfItems//lista wykorzytywana do modyfikacji

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