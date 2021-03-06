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
    private static TextView txtInfo, txtLogo;
    private List<String> listaDados, listaVelocidade;
    private EditText txtDados, txtVecolicadade;
    private Spinner spinnerDados, spinnerVelocidade;
    private double kbs, kb;
    private long aa, dd, hh, mm, ss;
    private GregorianCalendar c;

    public static void mensagem(String s) {
        txtInfo.setText(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        txtLogo = findViewById(R.id.txtTitulo);
        txtLogo.setEnabled(false);
        criadorDeSpinner();
    }

    public void preparacaoInicial(View v) {
        zerandoVariaveisGlonais();

        txtLogo.setEnabled(true);

        lerETratarDados();
        calcularTempo();
        esperar();

    }

    private void esperar() {
        //new Mensagem(":D obrigado por usar o Sistema Vox.");
        Mensagem mensagem = new Mensagem(":D obrigado por usar o Sistemas Vox.", 5);
        new Thread(mensagem).start();
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

    public void limparTela(View v) {
        txtDados.setText("");
        txtVecolicadade.setText("");
        txtInfo.setText("");
        spinnerDados.setSelection(0);
        spinnerVelocidade.setSelection(0);
    }

    private java.util.Date Date() {
        c.add(Calendar.YEAR, (int) aa);
        c.add(Calendar.DAY_OF_MONTH, (int) dd);
        c.add(Calendar.HOUR_OF_DAY, (int) hh);
        c.add(Calendar.MINUTE, (int) mm);
        c.add(Calendar.MILLISECOND, (int) (ss * 1000));
        return c.getTime();
    }

    private void SegundosemMin() {
        mm = ss / 60;
        ss = ss % 60;
        Horas();
    }

    private void Horas() {
        hh = mm / 60;
        mm = mm % 60;
        Dias();
    }

    private void Dias() {
        dd = hh / 24;
        hh = hh % 24;
        Anos();
    }

    private void Anos() {
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
    class Mensagem implements Runnable{
        String msg;
        int segundos;

        Mensagem(String msg, int segundos){
            this.msg = msg;
            this.segundos = segundos;
        }
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((segundos * 1000));
                        txtInfo.setText(msg);
                    } catch (InterruptedException e) {
                        txtInfo.setText(e.getMessage());
                    }
                }
            });
        }
    }
}
