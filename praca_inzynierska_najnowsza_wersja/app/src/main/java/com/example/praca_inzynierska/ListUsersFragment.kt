package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_products_list.*

//zmienne globalne
var CoWybrales2="prezes"
var ListaRekordowDoModyfikacji2=ArrayList<DatabaseRowListUsers>()
var CoWybralesdostepSpinner="user"
class ListUsersFragment : BaseFragment() {


    //inicjalizacja komponentow z xmla i z firebase i inne
    private lateinit var myRef: DatabaseReference
    private val auth = FirebaseAuth.getInstance()
    private lateinit var dodaj2: Button
    private lateinit var PokazCalaListe: Button
    private lateinit var WyborZRozwijanejListy: Button
    private lateinit var Szukaj: Button
    private lateinit var wpiszTytul: EditText
    private lateinit var poleSzukania: EditText
    private  lateinit var listOfItems2:ArrayList<DatabaseRowListUsers>
    private lateinit var spinner: Spinner
    private lateinit var dostepSpinner: Spinner
    private lateinit var dodajUzytkownikow: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //inicjalizacja komponentow z xmla
        spinner=view.findViewById(R.id.spinner)
        dostepSpinner=view.findViewById(R.id.dostepSpinner)
        PokazCalaListe=view.findViewById(R.id.PokazCalaListe)
        WyborZRozwijanejListy=view.findViewById(R.id.WyborZRozwijanejListy)
        Szukaj=view.findViewById(R.id.szukaj)
        poleSzukania=view.findViewById(R.id.poleSzukania)
        dodajUzytkownikow=view.findViewById(R.id.dodajUzytkownikow)
        dodajUzytkownikow.setOnClickListener {
//            auth.signOut()
            startApp2()
        }

//opcje w rozwijanej liscie spinner
        //?????????????????????
        val options= arrayOf("administrator systemu","kierownik marketingu i logistyki","pracownik biurowy","pracownik magazynowy")
//        val options= arrayOf("administrator","kierownik","pracownikB","pracownikM")
        spinner.adapter= ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,options)

        spinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                CoWybrales2=options.get(p2)//opcja ktora wybralem
                dodanieDoAdapteraWszystkichElementow()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        //opcje w rozwijanej liscie dostepSpinner
        val options2= arrayOf("user","administrator")

        dostepSpinner.adapter= ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,options2)

        dostepSpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                CoWybralesdostepSpinner=options2.get(p2)//opcja ktora wybralem
                dodanieDoAdapteraWszystkichElementow()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


//unikalny klucz do danego użytkownika(jest po to, żeby każdy użytkownik miał osobno swoją bazę danych
        var uid=auth.currentUser?.uid.toString()
        val firebase= FirebaseDatabase.getInstance()
        myRef=firebase.getReference("lista_uzytkownikow")
//wyglad recyclerview
        recyclerview2.layoutManager= GridLayoutManager(context, 1)


//aktualizacja
        dodanieDoAdapteraWszystkichElementow()

        PokazCalaListe.setOnClickListener {
            dodanieDoAdapteraWszystkichElementow()
        }
//        zalezy od wybranej opcji w liscie rozwijanej, jak przycisniemy ten przycisk to sortuje po wybranej opcji liste
        WyborZRozwijanejListy.setOnClickListener {
            var wybor=""
            var index=0
            var tab= mutableListOf<Int>()
            for(i in ListaRekordowDoModyfikacji2){
                var calyStringRekordu=i.toString()
                var wybor=""
                var pozycja=0
                var tabbb= arrayListOf(0,1,2,3,4)//trzeba jeszcze zmienic-zawod-5 elementow
                for (j1 in 0..100){
                    for (j2 in tabbb){
                        wybor=wybor+calyStringRekordu[j2]
                    }
                    if(wybor=="zawod"){//kod jest bardzo elastyczny do danej sytuacji i wystarczy podać miejsce w, którym chcesz wycignac wartosc
                        wybor=""
                        pozycja=j1
                        break
                    }else{
                        wybor=""
                    }
                    tabbb.remove(tabbb[0])
                    tabbb.add(tabbb[3]+1)//tu tez trzeba zmieniac pod ilosc elementow
                }

                for (j3 in pozycja+6..200){//tu tez trzeba zmieniac pod ilosc elementow
                    if(calyStringRekordu[j3]==','||calyStringRekordu[j3]==')'){
                        break
                    }else {
                        wybor=wybor+calyStringRekordu[j3]
                    }

                }
                if(wybor==CoWybrales2){
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

                var cos=ListaRekordowDoModyfikacji2[tab[kkk]]
                ListaRekordowDoModyfikacji2.remove(cos)
                kkk--
            }
            //aktualizowanie, żeby zmienić rekordy w adapterze
            setupAdapter(ListaRekordowDoModyfikacji2)
        }
//        jak wpisze w edittext obok szukaj i klikne szukaj to szuka rekordy z tytulem rownym temu edittext
        szukaj.setOnClickListener {
            wielkosc=ListaRekordowDoModyfikacji2.size
            if(wielkosc2!=wielkosc){
                //komunikat
                dodanieDoAdapteraWszystkichElementow()
                Toast.makeText(context, "Nacisnij jeszcze raz w przycisk szukaj!", Toast.LENGTH_SHORT).show()
            }else{


                val poleSzukaniaStri=poleSzukania.text.toString()
                var l=poleSzukaniaStri.count()-1


                var index=0
                var tab= mutableListOf<Int>()

                for(i in ListaRekordowDoModyfikacji2){

                    var a=i.toString()
                    var b=i.toString().indexOf("email")+6//indeks na ktorym sie zaczyna email(na potrzeby wyciagniecia email ze stringa)
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

                    var cos=ListaRekordowDoModyfikacji2[tab[kkk]]
                    ListaRekordowDoModyfikacji2.remove(cos)
                    kkk--
                }
                //aktualizowanie, żeby zmienić rekordy w adapterze
                setupAdapter(ListaRekordowDoModyfikacji2)
            }}
    }
    private  fun dodanieDoAdapteraWszystkichElementow(){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfItems2=ArrayList()
                for(i in dataSnapshot.children){
                    val newRow2=i.getValue(DatabaseRowListUsers::class.java)
                    listOfItems2.add(newRow2!!)
                }
                ListaRekordowDoModyfikacji2=listOfItems2//lista wykorzytywana do modyfikacji

                wielkosc2=listOfItems2.size

                setupAdapter(listOfItems2)
            }

        })
    }
    //aktualizowanie adaptera
    private  fun setupAdapter(arrayData:ArrayList<DatabaseRowListUsers>){

//to trzeba dodac zeby blad nie wyskakiwal kiedy jest null
        if(recyclerview2==null){

        }else{
            recyclerview2.adapter= AdapterListUsers(arrayData)
        }

    }
}