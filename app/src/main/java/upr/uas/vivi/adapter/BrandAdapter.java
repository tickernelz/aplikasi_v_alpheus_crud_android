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
import upr.uas.vivi.crud.brand.UpdateBrandActivity;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Brand;

public class BrandAdapter extends BaseAdapter {

  Context context;
  DBHandler db;
  ArrayList<Brand> brandList;

  public BrandAdapter(Context context, ArrayList<Brand> brandList, DBHandler db) {
    this.context = context;
    this.brandList = brandList;
    this.db = db;
  }

  @Override
  public int getCount() {
    return this.brandList.size();
  }

  @Override
  public Object getItem(int position) {
    return brandList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    view = inflater.inflate(R.layout.list_brand, null);

    TextView tvKode = (TextView) view.findViewById(R.id.brand_kode_tv);
    TextView tvNama = (TextView) view.findViewById(R.id.brand_nama_tv);
    TextView tvKategori = (TextView) view.findViewById(R.id.brand_kategori_tv);

    Button btnUpdate = (Button) view.findViewById(R.id.post_update_btn);
    Button btnDelete = (Button) view.findViewById(R.id.post_delete_btn);

    Brand brand = brandList.get(position);
    tvKode.setText(brand.getKode());
    tvNama.setText(brand.getNama());
    tvKategori.setText(brand.getKategori());

    btnUpdate.setOnClickListener(
        view1 -> {
          Intent i = new Intent(context.getApplicationContext(), UpdateBrandActivity.class);
          i.putExtra("Brand", brand);
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
                      boolean delete = db.deleteBrand(brand.getId());
                      brandList.remove(position);
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
