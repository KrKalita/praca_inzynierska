package com.example.praca_inzynierska

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_list_messages.*
import kotlinx.android.synthetic.main.fragment_products_list.*

var ListaRekordowDoModyfikacji4=ArrayList<DatabaseRowListProducts>()

class ListMessagesFragment : Fragment() {
    private  lateinit var listOfItemsMessages:ArrayList<DatabaseRowListProducts>
    private  lateinit var listOfItemsMessagesModyfikacja:ArrayList<DatabaseRowListProducts>
    private lateinit var myRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var recyclerviewMessages=view.findViewById<RecyclerView>(R.id.recyclerviewMessages)
        var szukaj=view.findViewById<Button>(R.id.szukaj2)
        var poleSzukania=view.findViewById<EditText>(R.id.poleSzukania2)
        var PokazCalaListe=view.findViewById<Button>(R.id.PokazCalaListe2)
        //wchodze do pliku w realtime database lista_produktow i tam cos robie
        val firebase= FirebaseDatabase.getInstance()
        myRef=firebase.getReference("lista_produktow")
//wyglad recyclerview
        recyclerviewMessages.layoutManager= GridLayoutManager(context, 1)
        dodanieDoAdapteraWszystkichElementow(view)

        PokazCalaListe.setOnClickListener {
            dodanieDoAdapteraWszystkichElementow(view)
        }


        //jak wpisze w edittext obok szukaj i klikne szukaj to szuka rekordy z tytulem rownym temu edittext
        szukaj.setOnClickListener {
            wielkosc=ListaRekordowDoModyfikacji4.size
            if(wielkosc2!=wielkosc){
                //komunikat
                dodanieDoAdapteraWszystkichElementow(view)
                Toast.makeText(context, "Nacisnij jeszcze raz w przycisk szukaj!", Toast.LENGTH_SHORT).show()
            }else{


                val poleSzukaniaStri=poleSzukania.text.toString()
                var l=poleSzukaniaStri.count()-1


                var index=0
                var tab= mutableListOf<Int>()

                for(i in ListaRekordowDoModyfikacji4){

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

                    var cos=ListaRekordowDoModyfikacji4[tab[kkk]]
                    ListaRekordowDoModyfikacji4.remove(cos)
                    kkk--
                }
                //aktualizowanie, żeby zmienić rekordy w adapterze
                setupAdapter(view,ListaRekordowDoModyfikacji4)
            }}

    }

    private  fun dodanieDoAdapteraWszystkichElementow(view: View){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfItemsMessages=ArrayList()
                listOfItemsMessagesModyfikacja=ArrayList()
                for(i in dataSnapshot.children){
                    val newRow=i.getValue(DatabaseRowListProducts::class.java)
                    val newRow22=i.getValue(DatabaseRowListProducts::class.java)
                    listOfItemsMessages.add(newRow!!)
                }
                var cosss=listOfItemsMessages.size
                var cosss2=cosss-1
                //sortowanie zeby były wypisane produkty o niskim stanie i strefie składowania
                for(i in 0..cosss2){
                    if(listOfItemsMessages[i].strefa=="składowania"){
                        if(listOfItemsMessages[i].stan.toInt()<11){
                            var element=listOfItemsMessages[i]
                            listOfItemsMessagesModyfikacja.add(element)
                        }
                    }
                }




                ListaRekordowDoModyfikacji4 =listOfItemsMessagesModyfikacja//lista wykorzytywana do modyfikacji
                wielkosc2 =listOfItemsMessagesModyfikacja.size

                setupAdapter(view,listOfItemsMessagesModyfikacja)
            }

        })
    }
    //aktualizowanie adaptera
    private  fun setupAdapter(view: View,arrayData:ArrayList<DatabaseRowListProducts>){
        //to trzeba dodac zeby blad nie wyskakiwal kiedy jest null
        if(recyclerviewMessages==null){

        }else{
            recyclerviewMessages.adapter= AdapterListMessages(view,arrayData)
        }

    }
}