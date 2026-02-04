package com.example.catatankeuangan.data; // Sesuaikan dengan package Anda

import java.util.ArrayList;

// PENTING: Nama class harus DataTransaksiManager, BUKAN DataHelper
public class DataTransaksiManager {

    private static DataTransaksiManager instance;
    private ArrayList<ModelTransaksi> daftarTransaksi = new ArrayList<>();

    public static DataTransaksiManager getInstance() {
        if (instance == null) {
            instance = new DataTransaksiManager();
        }
        return instance;
    }

    public void tambahTransaksi(String tipe, String keterangan, double jumlah) {
        // Tambah di index 0 agar muncul paling atas
        daftarTransaksi.add(0, new ModelTransaksi(tipe, keterangan, jumlah));
    }

    public ArrayList<ModelTransaksi> getDaftarTransaksi() {
        return daftarTransaksi;
    }

    public double getSaldoAkhir() {
        double saldo = 0;
        for (ModelTransaksi t : daftarTransaksi) {
            if (t.getTipe().equals("MASUK")) {
                saldo += t.getJumlah();
            } else {
                saldo -= t.getJumlah();
            }
        }
        return saldo;
    }
}