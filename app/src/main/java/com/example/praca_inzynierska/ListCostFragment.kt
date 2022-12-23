package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_cost_list.*

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
        poleSzukania=view.findViewById(R.id.poleSzukaniaCost)

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
        //jak wpisze w edittext obok szukaj i klikne szukaj to szuka rekordy z tytulem rownym temu edittext
       /* szukaj.setOnClickListener {
            wielkoscCost=ListaRekordowDoModyfikacjiCost.size
            if(wielkosc2Cost!= wielkoscCost){
                //komunikat
                dodanieDoAdapteraWszystkichElementow(view)
                Toast.makeText(context, "Nacisnij jeszcze raz w przycisk szukaj!", Toast.LENGTH_SHORT).show()
            }else{


                val poleSzukaniaStri=poleSzukania.text.toString()
                var l=poleSzukaniaStri.count()-1


                var index=0
                var tab= mutableListOf<Int>()

                for(i in ListaRekordowDoModyfikacjiCost){

                    var a=i.toString()
                    var b=i.toString().indexOf("nazwa")+6//indeks na ktorym sie zaczyna tytul(na potrzeby wyciagniecia tytulu ze stringa)
//szukam rekordow nie pasujacych do słowa klucz i je usuwam
                    var hhhh=0
                    for (i in b..b+l) {
                        if(a[i]!=poleSzukaniaStri[hhhh]){
                            tab.add(index)
                            break;
                        }
                        hhhh++
                    }
                    index++
                }
                var kkk=tab.size-1
                for(i in tab){

                    var cos= ListaRekordowDoModyfikacjiCost[tab[kkk]]
                    ListaRekordowDoModyfikacjiCost.remove(cos)
                    kkk--
                }
                //aktualizowanie, żeby zmienić rekordy w adapterze
                setupAdapter(view, ListaRekordowDoModyfikacjiCost)
            }}*/

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

}