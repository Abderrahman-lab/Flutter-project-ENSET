package com.example.devise

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val tauxChange = 10.9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextAmount: EditText = findViewById(R.id.editTextAmount)
        val btnCompute: Button = findViewById(R.id.btnCompute)
        val textViewResult: TextView = findViewById(R.id.textViewResult)
        val listViewResult: ListView = findViewById(R.id.listViewResults)

        val historique: MutableList<String> = ArrayList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, historique)
        listViewResult.adapter = adapter

        btnCompute.setOnClickListener {
            val saisie = editTextAmount.text.toString()
            val amount = saisie.toDoubleOrNull()
            if (amount == null) {
                editTextAmount.error = "Montant invalide"
                return@setOnClickListener
            }
            val result = amount * tauxChange
            textViewResult.text = String.format("%.2f DH", result)
            historique.add(0, String.format("%.2f € => %.2f DH", amount, result))
            adapter.notifyDataSetChanged()
            editTextAmount.setText("")
        }
    }
}
