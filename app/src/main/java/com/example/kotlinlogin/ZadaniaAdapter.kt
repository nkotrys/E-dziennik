package com.example.kotlinlogin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class ZadaniaAdapter(context: Context, zadania: List<Zadanie>,
                     val usunZadanieListener : UsunZadanieListener) :
    ArrayAdapter<Zadanie>(context, R.layout.zadanie, zadania) {

    // Interfejs, który musi implementować klasa, która będzie
    // usuwać dla nas zadania (w naszym przypadku ZadaniaActivity)
    interface UsunZadanieListener {
        fun usunZadanie(position: Int)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Pobieramy z listy obiekt Zadanie do wyświelenia
        val zadanie = getItem(position) as Zadanie

        // Tworzymy widok wiersza wg naszego layout'u
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.zadanie, parent, false)

        // Pobieramy referencje do pola tekstowego i przycisku
        val textViewOpisZadania = rowView.findViewById<TextView>(R.id.textViewOpisZadania)
        val buttonUsunZadanie = rowView.findViewById<Button>(R.id.buttonUsunZadanie)

        // Ustawiamy tekstowy opis zadania
        textViewOpisZadania.text = zadanie.toString()

        // Ustawiamy akcję obsługi kliknięcia przycisku
        buttonUsunZadanie.setOnClickListener { v : View ->
            if (usunZadanieListener != null) {
                // Metoda usunZadanie z klasy ZadaniaActivity
                // zostanie wywołana z odpowiednią wartością
                // parametru position, co pozwoli nam zidentyfikować
                // zadanie do usunięcia
                usunZadanieListener.usunZadanie(position)
            }
        }

        return rowView
    }
}