package upr.uas.vivi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import upr.uas.vivi.R;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Produk;

public class HomeAdapter extends BaseAdapter {

  Context context;
  DBHandler db;
  ArrayList<Produk> homeList;

  public HomeAdapter(Context context, ArrayList<Produk> homeList, DBHandler db) {
    this.context = context;
    this.homeList = homeList;
    this.db = db;
  }

  @Override
  public int getCount() {
    return this.homeList.size();
  }

  @Override
  public Object getItem(int position) {
    return homeList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {

    LayoutInflater inflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    view = inflater.inflate(R.layout.list_data_home, null);

    TextView tvKodeBrand = (TextView) view.findViewById(R.id.home_brand_tv);
    TextView tvUkuran = (TextView) view.findViewById(R.id.home_ukuran_tv);
    TextView tvWarna = (TextView) view.findViewById(R.id.home_warna_tv);
    TextView tvSatuan = (TextView) view.findViewById(R.id.home_satuan_tv);
    TextView tvHarga = (TextView) view.findViewById(R.id.home_harga_tv);
    TextView tvStok = (TextView) view.findViewById(R.id.home_stok_tv);

    Produk home = homeList.get(position);
    tvKodeBrand.setText(home.getKodeBrand());
    tvUkuran.setText(home.getUkuran());
    tvWarna.setText(home.getWarna());
    tvSatuan.setText(home.getSatuan());
    tvHarga.setText(home.getHarga());
    tvStok.setText(home.getStok());

    return view;
  }
}
