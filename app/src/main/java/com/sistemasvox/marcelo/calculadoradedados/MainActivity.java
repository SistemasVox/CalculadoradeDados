package com.sistemasvox.marcelo.calculadoradedados;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity implements View.OnKeyListener {

    TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.txtInfo);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
            return true;
        }
        return false;
    }

    public void hojeInfo(View x) {
        try {
            AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
            Account[] list = manager.getAccounts();
            info.setText("Nome User: " + list[0].name);
        } catch (Exception e) {
            info.setText("Erro: " + e.getMessage() +", não foi possível ler seu nome no momento.");
        }
    }

    public void botaoCalculadora(View x) {
        try {
            Intent i = new Intent(this, Calculadora.class);
            startActivity(i);
        } catch (Exception e) {
            info.setText("Erro: " + e.getMessage());
        }
    }


}
