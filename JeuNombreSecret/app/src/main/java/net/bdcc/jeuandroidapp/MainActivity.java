package net.bdcc.jeuandroidapp;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonOK;
    private EditText editTextNumber;
    private ListView listViewHisto;
    private TextView textViewIndication;
    private ProgressBar progressBarScore;
    private TextView textViewScore;
    private TextView textViewScoreCumul;

    private int secret;
    private int nombreEssais = 1;
    private final int nombreMaxEssais = 10;
    private int scoreCumule = 0;

    private final List<String> historique = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);
        listViewHisto = findViewById(R.id.listViewHisto);
        textViewIndication = findViewById(R.id.textViewIndication);
        textViewScore = findViewById(R.id.textViewScore);
        progressBarScore = findViewById(R.id.progressBarScore);
        buttonOK = findViewById(R.id.buttonOK);
        textViewScoreCumul = findViewById(R.id.textViewScoreCumul);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historique);
        listViewHisto.setAdapter(adapter);

        initialisation();

        buttonOK.setOnClickListener(evt -> {
            String str = editTextNumber.getText().toString();
            int number;
            try {
                number = Integer.parseInt(str);
            } catch (NumberFormatException ex) {
                editTextNumber.setError(getString(R.string.str_nouvel_essai));
                return;
            }

            historique.add(0, getString(R.string.essai_numero) + " " + nombreEssais + " => " + number);
            adapter.notifyDataSetChanged();

            textViewScore.setText(String.valueOf(nombreEssais));
            progressBarScore.setProgress(nombreEssais);
            editTextNumber.setText("");

            if (number == secret) {
                textViewIndication.setText(getString(R.string.bravo));
                scoreCumule += 5;
                textViewScoreCumul.setText(String.valueOf(scoreCumule));
                finDePartie();
                return;
            }

            if (nombreEssais >= nombreMaxEssais) {
                textViewIndication.setText(getString(R.string.str_nouvel_essai));
                finDePartie();
                return;
            }

            if (number > secret) {
                textViewIndication.setText(getString(R.string.nombre_plus_grand));
            } else {
                textViewIndication.setText(getString(R.string.nombre_plus_petit));
            }
            ++nombreEssais;
        });
    }

    private void finDePartie() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.str_nouvel_essai));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.btn_ok),
                (dialog, which) -> initialisation());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.btn_finish),
                (dialog, which) -> finish());
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void initialisation() {
        this.nombreEssais = 1;
        this.secret = 1 + (int) (Math.random() * 100);
        this.editTextNumber.requestFocus();
        this.progressBarScore.setProgress(nombreEssais);
        this.textViewIndication.setText("");
        this.editTextNumber.setText("");
        this.textViewScore.setText(String.valueOf(nombreEssais));
        this.textViewScoreCumul.setText(String.valueOf(scoreCumule));
        historique.clear();
        adapter.notifyDataSetChanged();
    }
}
