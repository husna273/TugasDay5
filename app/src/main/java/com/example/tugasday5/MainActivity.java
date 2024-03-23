package com.example.tugasday5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etinputname, etkodeitem, etjmlbarang;
    private RadioGroup rgmembership;
    private Button btotal;
    private TextView tvdiskon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etinputname = findViewById(R.id.etinputname);
        etkodeitem = findViewById(R.id.etkodeitem);
        etjmlbarang = findViewById(R.id.etjmlbarang);
        rgmembership= findViewById(R.id.rgmembership);
        btotal = findViewById(R.id.btotal);
        tvdiskon = findViewById(R.id.tvdiskon);

        btotal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String customer = etinputname.getText().toString().trim();
        String kode = etkodeitem.getText().toString().trim();
        String total_item = etjmlbarang.getText().toString().trim();
        String membershipType = "";

        int selectedId = rgmembership.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        membershipType = selectedRadioButton.getText().toString();

        //  total harga
        int hargaBarang = 0;
        switch (kode) {
            case "SGS":
                hargaBarang = 12999999;
                break;
            case "IPX":
                hargaBarang = 5725300;
                break;
            case "AV4":
                hargaBarang = 1989123;
                break;
            default:
                etkodeitem.setError("MISSING ITEM CODE");
                return;
        }

        // potongan harga utk yang berbelanja lebih dari 10 juta
        int disc = 0;
        int total_harga = Integer.parseInt(total_item) * hargaBarang;
        if (total_harga > 10000000) {
            disc = 100000;
        }
        double potongan = 0;
        switch (membershipType) {
            case "Gold":
                potongan = (int) (total_harga * 0.1);
                break;
            case "Silver":
                potongan = (int)(total_harga * 0.05);
                break;
            case "Normal":
                potongan = (int) (total_harga * 0.02);
                break;
        }

        // potongan harga asli bagi yang mendapat diskon
        int totalBayar = (int)(total_harga - disc - potongan);

        // eksplisit intent untuk detail item
        Intent intent = new Intent(this, detailActivity.class);
        intent.putExtra("customer", customer);
        intent.putExtra("kode", kode);
        intent.putExtra("total_item", total_item);
        intent.putExtra("Membership", membershipType);
        intent.putExtra("total_harga", total_harga);
        intent.putExtra("disc", disc);
        intent.putExtra("total_bayar", totalBayar);

        // Memulai aktivitas DetailTransaksiActivity
        startActivity(intent);

    }
}