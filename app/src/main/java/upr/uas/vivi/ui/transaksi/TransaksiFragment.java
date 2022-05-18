package upr.uas.vivi.ui.transaksi;

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
import upr.uas.vivi.adapter.TransaksiAdapter;
import upr.uas.vivi.crud.transaksi.CreateTransaksiActivity;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Transaksi;

public class TransaksiFragment extends Fragment {

  DBHandler db;
  ArrayList<Transaksi> transaksiList;
  ListView lvTransaksi;
  TransaksiAdapter adapter;
  TextView tvNoTransaksi;
  FloatingActionButton fabCreateTransaksi;
  View mView;

  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View mView = inflater.inflate(R.layout.fragment_transaksi, container, false);
    db = new DBHandler(requireContext());

    transaksiList = new ArrayList<>();
    tvNoTransaksi = mView.findViewById(R.id.transaksi_subheader_tv);
    lvTransaksi = mView.findViewById(R.id.transaksi_lv);
    fabCreateTransaksi = mView.findViewById(R.id.viewTransaksi_fab_createTransaksi);
    transaksiList = db.getTransaksiData();
    adapter = new TransaksiAdapter(requireContext(), transaksiList, db);
    lvTransaksi.setAdapter(adapter);
    adapter.notifyDataSetChanged();

    if (!transaksiList.isEmpty()) {
      tvNoTransaksi.setVisibility(View.INVISIBLE);
    } else {
      tvNoTransaksi.setVisibility(View.VISIBLE);
    }

    fabCreateTransaksi.setOnClickListener(
        view -> {
          Intent i = new Intent(getContext(), CreateTransaksiActivity.class);
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
