package com.example.catatankeuangan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankeuangan.adapter.TransaksiAdapter;
import com.example.catatankeuangan.data.ModelTransaksi;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ModelTransaksi> listData = new ArrayList<>();
    private DatabaseHelper dbHelper; // Database SQLite

    private TextView tvSaldoAkhir, tvBelumAda;
    private RecyclerView rvTransaksi;
    private Button btnTambah;
    private TransaksiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inisialisasi Database
        dbHelper = new DatabaseHelper(this);

        // 2. Hubungkan ID Layout
        tvSaldoAkhir = findViewById(R.id.tv_saldo_akhir);
        tvBelumAda = findViewById(R.id.tv_belum_ada_catatan);
        rvTransaksi = findViewById(R.id.recycler_view_transaksi);
        btnTambah = findViewById(R.id.btn_tambah_catatan);

        // 3. Setup RecyclerView
        adapter = new TransaksiAdapter(listData);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
        rvTransaksi.setAdapter(adapter);

        // 4. Load Data Awal (PENTING!)
        muatDataDanHitungSaldo();

        // 5. Aksi Tombol Tambah
        btnTambah.setOnClickListener(v -> {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, TambahDataActivity.class);
                startActivity(intent);

                // --- PERUBAHAN ANIMASI ---
                // Menggunakan slide_in_up (muncul dari bawah) dan stay (diam)
                // Pastikan file res/anim/slide_in_up.xml dan res/anim/stay.xml sudah dibuat
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

            }, 200);
        });
    }

    // --- KUNCI UPDATE SALDO OTOMATIS ADA DI SINI ---
    // Method ini dipanggil otomatis setiap kali kita kembali ke halaman ini
    @Override
    protected void onResume() {
        super.onResume();
        muatDataDanHitungSaldo();
    }

    private void muatDataDanHitungSaldo() {
        // A. Kosongkan List Lama (Biar ga numpuk/dobel)
        listData.clear();

        // B. Ambil Data Terbaru dari Database
        listData.addAll(dbHelper.ambilSemuaData());

        // C. Beritahu Adapter ada data baru
        adapter.notifyDataSetChanged();

        // D. HITUNG SALDO TOTAL
        double saldo = 0;
        for (ModelTransaksi t : listData) {
            // Cek Tipe Transaksi (Huruf Besar/Kecil harus sama persis dengan yang disimpan)
            // Di TambahDataActivity kita simpan "MASUK" atau "KELUAR"
            if ("MASUK".equalsIgnoreCase(t.getTipe())) {
                saldo += t.getJumlah(); // Tambah
            } else {
                saldo -= t.getJumlah(); // Kurang
            }
        }

        // E. Tampilkan Saldo dengan Format Rupiah
        NumberFormat formatRp = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        tvSaldoAkhir.setText("Saldo Akhir: " + formatRp.format(saldo));

        // F. Cek Jika Kosong
        if (listData.isEmpty()) {
            tvBelumAda.setVisibility(View.VISIBLE);
            rvTransaksi.setVisibility(View.GONE);
        } else {
            tvBelumAda.setVisibility(View.GONE);
            rvTransaksi.setVisibility(View.VISIBLE);
        }
    }
}