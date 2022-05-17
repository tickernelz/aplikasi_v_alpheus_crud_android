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
import upr.uas.vivi.crud.transaksi.UpdateTransaksiActivity;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Transaksi;

public class TransaksiAdapter extends BaseAdapter {

  Context context;
  DBHandler db;
  ArrayList<Transaksi> transaksiList;

  public TransaksiAdapter(Context context, ArrayList<Transaksi> transaksiList, DBHandler db) {
    this.context = context;
    this.transaksiList = transaksiList;
    this.db = db;
  }

  @Override
  public int getCount() {
    return this.transaksiList.size();
  }

  @Override
  public Object getItem(int position) {
    return transaksiList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    view = inflater.inflate(R.layout.list_transaksi, null);

    TextView tvKodeTransaksi = (TextView) view.findViewById(R.id.transaksi_kode_tv);
    TextView tvNamaProduk = (TextView) view.findViewById(R.id.transaksi_nama_produk_tv);
    TextView tvJumlah = (TextView) view.findViewById(R.id.transaksi_jumlah_tv);

    Button btnUpdate = (Button) view.findViewById(R.id.post_update_btn);
    Button btnDelete = (Button) view.findViewById(R.id.post_delete_btn);

    Transaksi transaksi = transaksiList.get(position);
    tvKodeTransaksi.setText(transaksi.getKodeTransaksi());
    tvNamaProduk.setText(transaksi.getNamaProduk());
    tvJumlah.setText(transaksi.getJumlah());

    btnUpdate.setOnClickListener(
        view1 -> {
          Intent i = new Intent(context.getApplicationContext(), UpdateTransaksiActivity.class);
          i.putExtra("Transaksi", transaksi);
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
                      boolean delete = db.deleteTransaksi(transaksi.getId());
                      transaksiList.remove(position);
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
