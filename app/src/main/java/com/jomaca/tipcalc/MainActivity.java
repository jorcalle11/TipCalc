package com.jomaca.tipcalc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jomaca.tipcalc.models.About;
import com.jomaca.tipcalc.Fragments.TipHistoryList;
import com.jomaca.tipcalc.Fragments.TipHistoryListFragmentListener;
import com.jomaca.tipcalc.models.TipRecord;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getName();
    private final static int TIP_STEP_CHANGE = 1;
    private final static int DEFAULT_TIP_PERCENTAGE = 10;

    @Bind(R.id.inputBill)
    EditText inputBill;
    @Bind(R.id.txtTip)
    TextView txtTip;
    @Bind(R.id.inputPercentage)
    EditText inputPercentage;


    private TipHistoryListFragmentListener fragmentListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TipHistoryList fragment = (TipHistoryList) getSupportFragmentManager().findFragmentById(R.id.fragmentList);
        fragment.setRetainInstance(true);
        fragmentListener = (TipHistoryListFragmentListener) fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAbout:
                about();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSubmit)
    public void handleSubmit() {
        hideKeyboard();
        String strInputTotal = inputBill.getText().toString().trim();
        if (!strInputTotal.isEmpty()) {
            double total = Double.parseDouble(strInputTotal);
            int tipPercentage = getTipPercentage();

            TipRecord record = new TipRecord();
            record.setBill(total);
            record.setTipPercentage(tipPercentage);
            record.setTimestamp(new Date());

            String strTip = String.format(getString(R.string.global_message_tip),record.getTip());
            fragmentListener.addToList(record);
            txtTip.setVisibility(View.VISIBLE);
            txtTip.setText(strTip);
        } else {
            Toast.makeText(this,"Debes colocar el total", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnIncrease)
    public void handleIncrease () {
        hideKeyboard();
        handleTipChange(TIP_STEP_CHANGE);
    }


    @OnClick(R.id.btnDecrease)
    public void handleDecrease () {
        hideKeyboard();
        handleTipChange(-TIP_STEP_CHANGE);
    }

    @OnClick(R.id.btnClear)
    public void handleClear () {
        fragmentListener.clearList();
    }

    private void handleTipChange(int change) {
        int tipPercentage = getTipPercentage();
        tipPercentage += change;
        if (tipPercentage > 0) {
            inputPercentage.setText(String.valueOf(tipPercentage));
        }
    }

    public int getTipPercentage() {
        int tipPercentage = DEFAULT_TIP_PERCENTAGE;
        String strInputTipPercentage = inputPercentage.getText().toString().trim();
        if (!strInputTipPercentage.isEmpty()) {
            tipPercentage = Integer.parseInt(strInputTipPercentage);
        } else {
            inputPercentage.setText(String.valueOf(tipPercentage));
        }
        return tipPercentage;
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private void about() {
        About about = (About) getApplication();
        String strUrl = about.getAboutRul();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(strUrl));
        startActivity(intent);
    }
}
