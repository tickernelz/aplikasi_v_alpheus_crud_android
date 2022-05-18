package upr.uas.vivi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import upr.uas.vivi.R;
import upr.uas.vivi.crud.pembayaran.UpdatePembayaranActivity;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Pembayaran;

public class PembayaranAdapter extends BaseAdapter {

  Context context;
  DBHandler db;
  ArrayList<Pembayaran> pembayaranList;

  public PembayaranAdapter(Context context, ArrayList<Pembayaran> pembayaranList, DBHandler db) {
    this.context = context;
    this.pembayaranList = pembayaranList;
    this.db = db;
  }

  @Override
  public int getCount() {
    return this.pembayaranList.size();
  }

  @Override
  public Object getItem(int position) {
    return pembayaranList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    view = inflater.inflate(R.layout.list_pembayaran, null);

    TextView tvNota = (TextView) view.findViewById(R.id.pembayaran_nota_tv);
    TextView tvKodeTransaksi = (TextView) view.findViewById(R.id.pembayaran_kode_transaksi_tv);
    TextView tvTanggal = (TextView) view.findViewById(R.id.pembayaran_tanggal_tv);
    TextView tvTotalPembelian = (TextView) view.findViewById(R.id.pembayaran_total_pembelian_tv);
    TextView tvTotalBayar = (TextView) view.findViewById(R.id.pembayaran_total_bayar_tv);
    TextView tvSisaBayar = (TextView) view.findViewById(R.id.pembayaran_sisa_bayar_tv);

    Button btnUpdate = (Button) view.findViewById(R.id.post_update_btn);
    Button btnDelete = (Button) view.findViewById(R.id.post_delete_btn);

    Pembayaran pembayaran = pembayaranList.get(position);
    tvNota.setText(pembayaran.getNota());
    tvKodeTransaksi.setText(pembayaran.getKodeTransaksi());
    tvTanggal.setText(pembayaran.getTanggal());
    //    tvTotalPembelian.setText("");
    //    tvTotalBayar.setText("");
    //    tvSisaBayar.setText("");
    tvTotalPembelian.setText(pembayaran.getTotalPembelian());
    tvTotalBayar.setText(pembayaran.getTotalBayar());
    tvSisaBayar.setText(pembayaran.getSisaBayar());

    btnUpdate.setOnClickListener(
        view1 -> {
          Intent i = new Intent(context.getApplicationContext(), UpdatePembayaranActivity.class);
          i.putExtra("Pembayaran", pembayaran);
          context.startActivity(i);
        });

    btnDelete.setOnClickListener(
        view1 -> {
          new AlertDialog.Builder(context)
              .setMessage("Are you sure you want to delete?")
              .setPositiveButton(
                  "Yes",
                  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      boolean delete = db.deletePembayaran(pembayaran.getId());
                      pembayaranList.remove(position);
                      notifyDataSetChanged();
                      if (delete) {
                        new AlertDialog.Builder(context)
                            .setMessage("Delete Success")
                            .setPositiveButton("OK", null)
                            .show();
                      } else {
                        new AlertDialog.Builder(context)
                            .setMessage("Delete Failed")
                            .setPositiveButton("OK", null)
                            .show();
                      }
                    }
                  })
              .setNegativeButton(
                  "No",
                  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      dialogInterface.cancel();
                    }
                  })
              .show();
        });

    return view;
  }
}
