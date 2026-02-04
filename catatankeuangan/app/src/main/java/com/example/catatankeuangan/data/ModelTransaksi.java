package com.example.catatankeuangan.data;

public class ModelTransaksi {
    private String tipe;
    private String keterangan;
    private double jumlah;
    private long waktuDibuat; // Tambahan agar bisa diurutkan waktunya

    // WAJIB ADA: Constructor Kosong untuk Firebase
    public ModelTransaksi() {}

    public ModelTransaksi(String tipe, String keterangan, double jumlah) {
        this.tipe = tipe;
        this.keterangan = keterangan;
        this.jumlah = jumlah;
        this.waktuDibuat = System.currentTimeMillis();
    }

    // Getter & Setter
    public String getTipe() { return tipe; }
    public String getKeterangan() { return keterangan; }
    public double getJumlah() { return jumlah; }
    public long getWaktuDibuat() { return waktuDibuat; }
}