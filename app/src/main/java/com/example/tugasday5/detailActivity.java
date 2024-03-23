package com.example.tugasday5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class detailActivity extends AppCompatActivity {

    private TextView tvNamaPembeli, tvNamaBarang, tvKodeBarang, tvJumlahBarang,
            tvTotalHarga, tvDiskon, tvTotalBayar, tvWelcome, tvMembership;
    private String[] codeofName = {"Samsung Galaxy S", "Iphone X", "Asus Vivobook 14"};
    private Button btnShare;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvNamaPembeli = findViewById(R.id.tvNamaPembeli);
        tvNamaBarang = findViewById(R.id.tvNamaBarang);
        tvKodeBarang = findViewById(R.id.tvKodeBarang);
        tvJumlahBarang = findViewById(R.id.tvJumlahBarang);
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        tvDiskon = findViewById(R.id.tvDiskon);
        tvTotalBayar = findViewById(R.id.tvTotalBayar);
        tvWelcome = findViewById(R.id.tvWelcome);
        tvMembership = findViewById(R.id.tvMembership);
        btnShare = findViewById(R.id.btnShare);

        // Data dari Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String customer = extras.getString("customer");
            String kode = extras.getString("kode");
//            String namaBarang = extras.getString("nameofItem");
            String total_item = extras.getString("total_item");
            int total_harga = extras.getInt("total_harga");
            int disc = extras.getInt("disc");
            String membershipType = extras.getString("Membership");
            int total_bayar = extras.getInt("total_bayar");

            // Nama barang berdasarkan kode barang
            String nama_item = "";
            switch (kode) {
                case "SGS":
                    nama_item = codeofName[0];
                    break;
                case "IPX":
                    nama_item = codeofName[1];
                    break;
                case "AV4":
                    nama_item = codeofName[2];
                    break;
            }

// Mengganti string statis dalam pesan dengan string terjemahan
            tvWelcome.setText("");
            tvMembership.setText(getString(R.string.Membership) + membershipType);
            tvNamaPembeli.setText(getString(R.string.customer) + customer);
            tvNamaBarang.setText(getString(R.string.name_item)  + nama_item);
            tvKodeBarang.setText(getString(R.string.kode) + kode);
            tvJumlahBarang.setText(getString(R.string.total_item) + total_item);
            tvTotalHarga.setText(getString(R.string.total_harga) + " Rp. " + total_harga);
            tvDiskon.setText(getString(R.string.disc) + " Rp. " + disc);
            tvTotalBayar.setText(getString(R.string.total_bayar) + " Rp. " + total_bayar);


            //implisit intent
            String finalNamaBarang = nama_item;
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareData(customer, finalNamaBarang, kode, total_item, membershipType, total_harga, disc, total_bayar);
                }
            });
        }
    }

    private void shareData(String customer, String nama_item, String kode, String total_item, String membershipType, int total_harga, int disc, int total_bayar) {
        Intent order = new Intent(Intent.ACTION_SEND);
        order.setType("text/plain");
        order.putExtra(Intent.EXTRA_SUBJECT, "Detail belanjaan Anda");

        // Mengumpulkan semua informasi dalam pesan
        String message = getString(R.string.Membership) + membershipType + "\n" +
                getString(R.string.customer) + customer + "\n" +
                getString(R.string.name_item) + nama_item + "\n" +
                getString(R.string.kode) + kode + "\n" +
                getString(R.string.total_item) + total_item + "\n" +
                getString(R.string.total_harga) + " Rp. " + total_harga + "\n" +
                getString(R.string.disc) + " Rp. " + disc + "\n" +
                getString(R.string.total_bayar) + " Rp. " + total_bayar;

        order.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(order, "Pilih aplikasi untuk mengirim"));
    }
}