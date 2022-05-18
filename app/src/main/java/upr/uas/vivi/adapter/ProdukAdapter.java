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
import upr.uas.vivi.crud.produk.UpdateProdukActivity;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Produk;

public class ProdukAdapter extends BaseAdapter {

  Context context;
  DBHandler db;
  ArrayList<Produk> produkList;

  public ProdukAdapter(Context context, ArrayList<Produk> produkList, DBHandler db) {
    this.context = context;
    this.produkList = produkList;
    this.db = db;
  }

  @Override
  public int getCount() {
    return this.produkList.size();
  }

  @Override
  public Object getItem(int position) {
    return produkList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    view = inflater.inflate(R.layout.list_produk, null);

    TextView tvKodeProduk = (TextView) view.findViewById(R.id.produk_kode_tv);
    TextView tvKodeBrand = (TextView) view.findViewById(R.id.produk_brand_tv);
    TextView tvNama = (TextView) view.findViewById(R.id.produk_nama_tv);
    TextView tvUkuran = (TextView) view.findViewById(R.id.produk_ukuran_tv);
    TextView tvWarna = (TextView) view.findViewById(R.id.produk_warna_tv);
    TextView tvSatuan = (TextView) view.findViewById(R.id.produk_satuan_tv);
    TextView tvHarga = (TextView) view.findViewById(R.id.produk_harga_tv);
    TextView tvStok = (TextView) view.findViewById(R.id.produk_stok_tv);

    Button btnUpdate = (Button) view.findViewById(R.id.post_update_btn);
    Button btnDelete = (Button) view.findViewById(R.id.post_delete_btn);

    Produk produk = produkList.get(position);
    tvKodeProduk.setText(produk.getKodeProduk());
    tvKodeBrand.setText(produk.getKodeBrand());
    tvNama.setText(produk.getNama());
    tvUkuran.setText(produk.getUkuran());
    tvWarna.setText(produk.getWarna());
    tvSatuan.setText(produk.getSatuan());
    tvHarga.setText(produk.getHarga());
    tvStok.setText(produk.getStok());

    btnUpdate.setOnClickListener(
        view1 -> {
          Intent i = new Intent(context.getApplicationContext(), UpdateProdukActivity.class);
          i.putExtra("Produk", produk);
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
                      boolean delete = db.deleteProduk(produk.getId());
                      produkList.remove(position);
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
