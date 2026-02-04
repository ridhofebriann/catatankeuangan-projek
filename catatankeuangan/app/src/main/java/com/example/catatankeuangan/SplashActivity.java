package com.example.catatankeuangan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private static final int KODE_IZIN_LOKASI = 1001;
    private FusedLocationProviderClient fusedLocationClient;

    private ProgressBar progressBar;
    private RelativeLayout layoutInfoBahasa;
    private ImageView imgBendera;
    private TextView tvSelamatDatang;
    private TextView tvInfoBahasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        progressBar = findViewById(R.id.progress_bar);
        layoutInfoBahasa = findViewById(R.id.layout_info_bahasa);
        imgBendera = findViewById(R.id.img_bendera);
        tvSelamatDatang = findViewById(R.id.tv_selamat_datang);
        tvInfoBahasa = findViewById(R.id.tv_info_bahasa);

        // LANGSUNG CEK LOKASI (Tanpa Login-loginan)
        cekIzinLokasi();
    }

    private void cekIzinLokasi() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    KODE_IZIN_LOKASI);
        } else {
            dapatkanLokasi();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == KODE_IZIN_LOKASI) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dapatkanLokasi();
            } else {
                jalankanSkenarioGagal();
            }
        }
    }

    private void dapatkanLokasi() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            jalankanSkenarioGagal();
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        jalankanSkenarioSukses(location);
                    } else {
                        jalankanSkenarioGagal();
                    }
                })
                .addOnFailureListener(this, e -> jalankanSkenarioGagal());
    }

    private void jalankanSkenarioSukses(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String kodeNegara = "ID";
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                kodeNegara = addresses.get(0).getCountryCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tampilkanInfoBahasa(kodeNegara);
    }

    private void jalankanSkenarioGagal() {
        tampilkanInfoBahasa("ID");
    }

    private void tampilkanInfoBahasa(String kodeNegara) {
        progressBar.setVisibility(View.GONE);
        layoutInfoBahasa.setAlpha(0f);
        layoutInfoBahasa.setVisibility(View.VISIBLE);
        layoutInfoBahasa.animate().alpha(1f).setDuration(1000).setListener(null);

        if (kodeNegara == null) kodeNegara = "ID";
        switch (kodeNegara.toUpperCase()) {
            case "US":
                imgBendera.setImageResource(R.drawable.flag_us);
                tvSelamatDatang.setText("Welcome");
                tvInfoBahasa.setText("Language : English");
                break;
            case "CN":
                imgBendera.setImageResource(R.drawable.flag_cn);
                tvSelamatDatang.setText("欢迎");
                tvInfoBahasa.setText("语言 : 中文");
                break;
            case "JP":
                imgBendera.setImageResource(R.drawable.flag_jp);
                tvSelamatDatang.setText("ようこそ");
                tvInfoBahasa.setText("言語 : 日本語");
                break;
            case "ID":
            default:
                imgBendera.setImageResource(R.drawable.flag_id);
                tvSelamatDatang.setText("Selamat Datang");
                tvInfoBahasa.setText("Bahasa : Indonesia");
                break;
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, 2000);
    }
}