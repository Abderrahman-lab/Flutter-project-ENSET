package com.example.imc

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextPoids: EditText = findViewById(R.id.editTextPoids)
        val editTextTaille: EditText = findViewById(R.id.editTextTaille)
        val btnCalculer: Button = findViewById(R.id.btnCalculer)
        val textViewImc: TextView = findViewById(R.id.textViewImc)
        val textViewCategorie: TextView = findViewById(R.id.textViewCategorie)
        val imageViewCategorie: ImageView = findViewById(R.id.imageViewCategorie)

        btnCalculer.setOnClickListener {
            val poids = editTextPoids.text.toString().toDoubleOrNull()
            val tailleCm = editTextTaille.text.toString().toDoubleOrNull()

            if (poids == null || tailleCm == null || poids <= 0 || tailleCm <= 0) {
                textViewImc.text = ""
                textViewCategorie.text = ""
                imageViewCategorie.setImageDrawable(null)
                if (poids == null) editTextPoids.error = "Poids invalide"
                if (tailleCm == null) editTextTaille.error = "Taille invalide"
                return@setOnClickListener
            }

            val tailleM = tailleCm / 100.0
            val imc = poids / (tailleM * tailleM)
            textViewImc.text = String.format(Locale.getDefault(), "IMC = %.1f", imc)

            val (categorieRes, drawableRes, colorRes) = when {
                imc < 18.5 -> Triple(R.string.cat_insuffisance, R.drawable.ic_cat_insuffisance, R.color.catInsuffisance)
                imc < 25.0 -> Triple(R.string.cat_normale, R.drawable.ic_cat_normale, R.color.catNormale)
                imc < 30.0 -> Triple(R.string.cat_surpoids, R.drawable.ic_cat_surpoids, R.color.catSurpoids)
                else -> Triple(R.string.cat_obesite, R.drawable.ic_cat_obesite, R.color.catObesite)
            }

            textViewCategorie.setText(categorieRes)
            textViewCategorie.setTextColor(ContextCompat.getColor(this, colorRes))
            imageViewCategorie.setImageResource(drawableRes)
        }
    }
}
