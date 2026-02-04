package com.example.catatankeuangan;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class TambahDataActivity extends AppCompatActivity {

    private EditText etJml, etKet;
    private RadioGroup rgTipe;
    private Button btnSimpan;

    private DatabaseHelper dbHelper; // Panggil Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        // Inisialisasi Database
        dbHelper = new DatabaseHelper(this);

        // Handler tombol Back (Fisik/Gesture HP)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
                // PERBAIKAN ANIMASI:
                // R.anim.stay = Layar utama diam di belakang
                // R.anim.slide_out_down = Layar ini turun ke bawah
                overridePendingTransition(R.anim.stay, R.anim.slide_out_down);
            }
        });

        etJml = findViewById(R.id.et_jumlah);
        etKet = findViewById(R.id.et_keterangan);
        rgTipe = findViewById(R.id.rg_tipe);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(v -> simpan());
    }

    private void simpan() {
        String strJml = etJml.getText().toString();
        String ket = etKet.getText().toString();

        if (strJml.isEmpty() || ket.isEmpty()) {
            Toast.makeText(this, "Isi semua data!", Toast.LENGTH_SHORT).show();
            return;
        }

        double jml;
        try {
            jml = Double.parseDouble(strJml);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Format angka salah", Toast.LENGTH_SHORT).show();
            return;
        }

        String tipe = (rgTipe.getCheckedRadioButtonId() == R.id.rb_masuk) ? "MASUK" : "KELUAR";

        // SIMPAN KE SQLITE (OFFLINE)
        dbHelper.tambahTransaksi(tipe, ket, jml);

        Toast.makeText(this, "Berhasil disimpan!", Toast.LENGTH_SHORT).show();

        finish();
        // PERBAIKAN ANIMASI: Sama seperti tombol back, layar turun ke bawah
        overridePendingTransition(R.anim.stay, R.anim.slide_in_right);
    }
}