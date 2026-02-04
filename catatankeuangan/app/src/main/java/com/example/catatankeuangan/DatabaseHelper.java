package com.example.catatankeuangan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.catatankeuangan.data.ModelTransaksi;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String NAMA_DATABASE = "CatatanKeuangan.db";
    private static final int VERSI_DATABASE = 1;

    // Nama Tabel & Kolom
    private static final String TABEL_TRANSAKSI = "transaksi";
    private static final String KOLOM_ID = "id";
    private static final String KOLOM_TIPE = "tipe";
    private static final String KOLOM_KET = "keterangan";
    private static final String KOLOM_JUMLAH = "jumlah";
    private static final String KOLOM_WAKTU = "waktu";

    public DatabaseHelper(Context context) {
        super(context, NAMA_DATABASE, null, VERSI_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Buat Tabel Baru
        String query = "CREATE TABLE " + TABEL_TRANSAKSI + " (" +
                KOLOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KOLOM_TIPE + " TEXT, " +
                KOLOM_KET + " TEXT, " +
                KOLOM_JUMLAH + " REAL, " +
                KOLOM_WAKTU + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_TRANSAKSI);
        onCreate(db);
    }

    // --- FUNGSI TAMBAH DATA ---
    public void tambahTransaksi(String tipe, String keterangan, double jumlah) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KOLOM_TIPE, tipe);
        values.put(KOLOM_KET, keterangan);
        values.put(KOLOM_JUMLAH, jumlah);
        values.put(KOLOM_WAKTU, System.currentTimeMillis());

        db.insert(TABEL_TRANSAKSI, null, values);
        db.close();
    }

    // --- FUNGSI AMBIL SEMUA DATA ---
    public ArrayList<ModelTransaksi> ambilSemuaData() {
        ArrayList<ModelTransaksi> listData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Urutkan dari yang terbaru (DESC)
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABEL_TRANSAKSI + " ORDER BY " + KOLOM_WAKTU + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                String tipe = cursor.getString(cursor.getColumnIndexOrThrow(KOLOM_TIPE));
                String ket = cursor.getString(cursor.getColumnIndexOrThrow(KOLOM_KET));
                double jum = cursor.getDouble(cursor.getColumnIndexOrThrow(KOLOM_JUMLAH));

                // Masukkan ke Model
                // Kita pakai constructor ModelTransaksi yang sudah ada
                ModelTransaksi data = new ModelTransaksi(tipe, ket, jum);
                // Kita set waktu manual karena di constructor Anda otomatis 'now'
                // Tapi ini tidak masalah untuk tampilan.

                listData.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listData;
    }
}