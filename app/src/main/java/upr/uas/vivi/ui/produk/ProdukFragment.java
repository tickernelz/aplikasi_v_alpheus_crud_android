package upr.uas.vivi.ui.produk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import upr.uas.vivi.R;
import upr.uas.vivi.adapter.ProdukAdapter;
import upr.uas.vivi.crud.produk.CreateProdukActivity;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Produk;

public class ProdukFragment extends Fragment {

  DBHandler db;
  ArrayList<Produk> produkList;
  ListView lvProduk;
  ProdukAdapter adapter;
  TextView tvNoProduk;
  FloatingActionButton fabCreateProduk;
  View mView;

  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View mView = inflater.inflate(R.layout.fragment_produk, container, false);
    db = new DBHandler(requireContext());

    produkList = new ArrayList<>();
    tvNoProduk = mView.findViewById(R.id.produk_subheader_tv);
    lvProduk = mView.findViewById(R.id.produk_lv);
    fabCreateProduk = mView.findViewById(R.id.viewProduk_fab_createProduk);
    produkList = db.getProdukData();
    adapter = new ProdukAdapter(requireContext(), produkList, db);
    lvProduk.setAdapter(adapter);
    adapter.notifyDataSetChanged();

    if (!produkList.isEmpty()) {
      tvNoProduk.setVisibility(View.INVISIBLE);
    } else {
      tvNoProduk.setVisibility(View.VISIBLE);
    }

    fabCreateProduk.setOnClickListener(
        view -> {
          Intent i = new Intent(getContext(), CreateProdukActivity.class);
          startActivity(i);
        });
    return mView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mView = null;
  }
}
