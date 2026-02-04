package com.example.catatankeuangan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catatankeuangan.R;
import com.example.catatankeuangan.data.ModelTransaksi;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {

    private ArrayList<ModelTransaksi> listData;

    public TransaksiAdapter(ArrayList<ModelTransaksi> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelTransaksi item = listData.get(position);

        // 1. Ambil Context dari holder agar tidak error "cannot find symbol context"
        Context context = holder.itemView.getContext();

        // Set Data
        holder.tvKeterangan.setText(item.getKeterangan());

        // Format Rupiah
        NumberFormat formatRp = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        String harga = formatRp.format(item.getJumlah());

        // Cek Tipe Transaksi
        if (item.getTipe().equals("MASUK")) {
            holder.tvJumlah.setText("+" + harga);
            // Gunakan 'context' yang sudah kita ambil di atas
            holder.tvJumlah.setTextColor(ContextCompat.getColor(context, R.color.hijau_masuk));
            holder.imgTipe.setImageResource(R.drawable.ic_arrow_up);
        } else {
            holder.tvJumlah.setText("-" + harga);
            // Gunakan 'context' yang sudah kita ambil di atas
            holder.tvJumlah.setTextColor(ContextCompat.getColor(context, R.color.merah_keluar));
            holder.imgTipe.setImageResource(R.drawable.ic_arrow_down);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    // Class ViewHolder harus public agar bisa diakses
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Pastikan variabel ini PUBLIC agar bisa diakses di onBindViewHolder
        public TextView tvKeterangan;
        public TextView tvJumlah; // Nama harus SAMA dengan yang dipanggil di atas
        public ImageView imgTipe;

        public ViewHolder(View v) {
            super(v);
            // Hubungkan dengan ID di XML (list_item_transaksi.xml)
            tvKeterangan = v.findViewById(R.id.tv_keterangan);
            tvJumlah = v.findViewById(R.id.tv_jumlah);
            imgTipe = v.findViewById(R.id.img_arrow);
        }
    }
}