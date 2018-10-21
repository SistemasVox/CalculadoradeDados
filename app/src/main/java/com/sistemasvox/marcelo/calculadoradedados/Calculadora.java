package com.sistemasvox.marcelo.calculadoradedados;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Calculadora extends Activity implements View.OnKeyListener {

    private static final String TAG = "teste";
    private List<String> listaDados, listaVelocidade;
    private TextView txtInfo;
    private EditText txtDados, txtVecolicadade;
    private Spinner spinnerDados, spinnerVelocidade;
    private double kbs, kb;
    private long aa, dd, hh, mm, ss;
    private GregorianCalendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        criadorDeSpinner();
    }

    public void preparacaoInicial(View v) {
        zerandoVariaveisGlonais();
        txtInfo.setText("Calculando...");
        lerETratarDados();
        txtDados.setText("");
        txtVecolicadade.setText("");
        calcularTempo();
    }

    private void zerandoVariaveisGlonais() {
        aa = 0;
        dd = 0;
        hh = 0;
        mm = 0;
        ss = 0;
        kb = 0;
        kbs = 0;
        c = new GregorianCalendar();
        txtInfo = findViewById(R.id.txtInfo);
        txtDados = findViewById(R.id.txtEntradaDados);
        txtVecolicadade = findViewById(R.id.txtEntradaVelocidade);
    }

    private void lerETratarDados() {

        //Lendo Dados
        if (spinnerDados.getSelectedItemPosition() == 0) {
            kb = (Double.parseDouble(txtDados.getText().toString().replace(",", ".")) * 1024);
        } else if (spinnerDados.getSelectedItemPosition() == 1) {
            kb = (Double.parseDouble(txtDados.getText().toString().replace(",", ".")) * 1048576);
        } else {
            kb = (Double.parseDouble(txtDados.getText().toString().replace(",", ".")) * 1073741824);
        }

        //Lendo Velocidade

        if (spinnerVelocidade.getSelectedItemPosition() == 0) {
            kbs = (Double.parseDouble(txtVecolicadade.getText().toString().replace(",", ".")) * 1024) / 8;
        } else if (spinnerVelocidade.getSelectedItemPosition() == 1) {
            kbs = Double.parseDouble(txtVecolicadade.getText().toString().replace(",", "."));
        } else {
            kbs = Double.parseDouble(txtVecolicadade.getText().toString().replace(",", ".")) * 1024;
        }

        //spinnerDados.getSelectedItemPosition()
        //txtDados.getText().toString()
    }

    protected void calcularTempo() {
        ss = (long) (kb / kbs);
        String txt = ss + "s, " + kb + "KB, " + kbs + "KB/s.\n\n";
        SegundosemMin();
        txt = txt + "=====> " + hh + "h:" + mm + "m:" + ss + "s.\n";
        txt = txt + "=====> " + dd + "dia(s), " + hh + "h:" + mm + "m.\n";
        txt = txt + "=====> " + aa + "anos(s), " + dd + "dia(s), " + hh + "h:" + mm + "m.\n";
        txt = txt + "=====> " + aa + "anos(s), " + dd + "dia(s), " + hh + "h:" + mm + "m:" + ss + "s.\n\n";
        txt = txt + "Agora é: " + c.getTime() + ".\nTérmino: " + Date() + ".\n";
        txtInfo.setText(txt);
    }

    private java.util.Date Date() {
        c.add(Calendar.YEAR, (int) aa);
        c.add(Calendar.DAY_OF_MONTH, (int) dd);
        c.add(Calendar.HOUR_OF_DAY, (int) hh);
        c.add(Calendar.MINUTE, (int) mm);
        c.add(Calendar.MILLISECOND, (int) (ss * 1000));
        return c.getTime();
    }

    void SegundosemMin() {
        mm = ss / 60;
        ss = ss % 60;
        Horas();
    }

    void Horas() {
        hh = mm / 60;
        mm = mm % 60;
        Dias();
    }

    void Dias() {
        dd = hh / 24;
        hh = hh % 24;
        Anos();
    }

    void Anos() {
        aa = dd / 365;
        dd = dd % 365;
    }


    private void criadorDeSpinner() {
        listaDados = Arrays.asList("MB", "GB", "TB");
        spinnerDados = (Spinner) findViewById(R.id.spinnerDados);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listaDados);

        spinnerDados.setAdapter(adapter);

        listaVelocidade = Arrays.asList("mbit/s ", "KB/s", "MB/s");
        spinnerVelocidade = (Spinner) findViewById(R.id.spinnerVelocidade);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listaVelocidade);

        spinnerVelocidade.setAdapter(adapter2);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            return true;
        }
        return false;
    }
}
