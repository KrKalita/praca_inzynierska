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
import kotlinx.android.synthetic.main.fragment_products_list.*

//zmienne globalne
var CoWybrales=""
var ListaRekordowDoModyfikacji=ArrayList<DatabaseRowListProducts>()
var wielkosc=0
var wielkosc2=0

class beginFragment : Fragment() {

    //inicjalizacja komponentow z xmla i z firebase i inne
    private lateinit var myRef: DatabaseReference
    private val auth = FirebaseAuth.getInstance()
    private lateinit var dodaj2: Button
    private lateinit var PokazCalaListe: Button
    private lateinit var WyborZRozwijanejListy: Button
    private lateinit var Szukaj: Button
    private lateinit var wpiszTytul: EditText
    private lateinit var poleSzukania: EditText
    private  lateinit var listOfItems:ArrayList<DatabaseRowListProducts>
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //inicjalizacja komponentow z xmla
        dodaj2=view.findViewById(R.id.dodaj2)
        wpiszTytul=view.findViewById(R.id.wpiszTytul)
        spinner=view.findViewById(R.id.spinner)
        PokazCalaListe=view.findViewById(R.id.PokazCalaListe)
        WyborZRozwijanejListy=view.findViewById(R.id.WyborZRozwijanejListy)
        Szukaj=view.findViewById(R.id.szukaj)
        poleSzukania=view.findViewById(R.id.poleSzukania)

        //opcje w rozwijanej liscie
        val options= arrayOf("meble","zabawki","jedzenie")

        spinner.adapter= ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,options)

        spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                CoWybrales=options.get(p2)//opcja ktora wybralem
                dodanieDoAdapteraWszystkichElementow(view)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        //wchodze do pliku w realtime database lista_produktow i tam cos robie
        val firebase= FirebaseDatabase.getInstance()
        myRef=firebase.getReference("lista_produktow")
//wyglad recyclerview
        recyclerview2.layoutManager= GridLayoutManager(context, 1)
//jak przycisne dodaj to dodaje do fire base jeden rekord z wybraną opcją z listy rozwijanej i tytułem
        dodaj2.setOnClickListener {
            val tytul=wpiszTytul.text.toString()
            if(tytul!=""){
                val typ_produktu=CoWybrales
                val firebaseInput=DatabaseRowListProducts(tytul,typ_produktu)
                myRef.child(tytul).setValue(firebaseInput)
            }
        }
//aktualizacja

        dodanieDoAdapteraWszystkichElementow(view)

        PokazCalaListe.setOnClickListener {
            dodanieDoAdapteraWszystkichElementow(view)
        }
//        //zalezy od wybranej opcji w liscie rozwijanej, jak przycisniemy ten przycisk to sortuje po wybranej opcji liste
        WyborZRozwijanejListy.setOnClickListener {
            var wybor=""
            var index=0
            var tab= mutableListOf<Int>()
            for(i in ListaRekordowDoModyfikacji){
//kod do odczytania typ_produktu
                var calyStringRekordu=i.toString()
                var wybor=""
                var pozycja=0
                var tabbb= arrayListOf(0,1,2,3,4,5,6,7,8,9,10,11)
                for (j1 in 0..100){
                    for (j2 in tabbb){
                        wybor=wybor+calyStringRekordu[j2]
                    }
                    if(wybor=="typ_produktu"){//kod jest bardzo elastyczny do danej sytuacji i wystarczy podać miejsce w, którym chcesz wycignac wartosc
                        wybor=""
                        pozycja=j1
                        break
                    }else{
                        wybor=""
                    }
                    tabbb.remove(tabbb[0])
                    tabbb.add(tabbb[10]+1)
                }

                for (j3 in pozycja+13..100){
                    if(calyStringRekordu[j3]==','||calyStringRekordu[j3]==')'){
                        break
                    }else {
                        wybor=wybor+calyStringRekordu[j3]
                    }

                }
                if(wybor==CoWybrales){
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

                var cos=ListaRekordowDoModyfikacji[tab[kkk]]
                ListaRekordowDoModyfikacji.remove(cos)
                kkk--
            }
            //aktualizowanie, żeby zmienić rekordy w adapterze
            setupAdapter(view,ListaRekordowDoModyfikacji)//view po to żeby można było wykorzystac w adapterze findnavcontroller
        }
        //jak wpisze w edittext obok szukaj i klikne szukaj to szuka rekordy z tytulem rownym temu edittext
        szukaj.setOnClickListener {
            wielkosc=ListaRekordowDoModyfikacji.size
            if(wielkosc2!=wielkosc){
                //komunikat
                dodanieDoAdapteraWszystkichElementow(view)
                Toast.makeText(context, "Nacisnij jeszcze raz w przycisk szukaj!", Toast.LENGTH_SHORT).show()
            }else{


                val poleSzukaniaStri=poleSzukania.text.toString()
                var l=poleSzukaniaStri.count()-1


                var index=0
                var tab= mutableListOf<Int>()

                for(i in ListaRekordowDoModyfikacji){

                    var a=i.toString()
                    var b=i.toString().indexOf("tytul")+6//indeks na ktorym sie zaczyna tytul(na potrzeby wyciagniecia tytulu ze stringa)
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

                    var cos=ListaRekordowDoModyfikacji[tab[kkk]]
                    ListaRekordowDoModyfikacji.remove(cos)
                    kkk--
                }
                //aktualizowanie, żeby zmienić rekordy w adapterze
                setupAdapter(view,ListaRekordowDoModyfikacji)
            }}

    }
    private  fun dodanieDoAdapteraWszystkichElementow(view: View){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfItems=ArrayList()
                for(i in dataSnapshot.children){
                    val newRow=i.getValue(DatabaseRowListProducts::class.java)
                    listOfItems.add(newRow!!)
                }
                ListaRekordowDoModyfikacji =listOfItems//lista wykorzytywana do modyfikacji

                wielkosc2 =listOfItems.size

                setupAdapter(view,listOfItems)
            }

        })
    }
    //aktualizowanie adaptera
    private  fun setupAdapter(view: View,arrayData:ArrayList<DatabaseRowListProducts>){
        recyclerview2.adapter= AdapterListProducts(view,arrayData)
    }

}