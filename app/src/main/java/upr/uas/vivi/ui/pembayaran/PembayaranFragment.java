package upr.uas.vivi.ui.pembayaran;

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
import upr.uas.vivi.adapter.PembayaranAdapter;
import upr.uas.vivi.crud.pembayaran.CreatePembayaranActivity;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Pembayaran;

public class PembayaranFragment extends Fragment {

  DBHandler db;
  ArrayList<Pembayaran> pembayaranList;
  ListView lvPembayaran;
  PembayaranAdapter adapter;
  TextView tvNoPembayaran;
  FloatingActionButton fabCreatePembayaran;
  View mView;

  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View mView = inflater.inflate(R.layout.fragment_pembayaran, container, false);
    db = new DBHandler(requireContext());

    pembayaranList = new ArrayList<>();
    tvNoPembayaran = mView.findViewById(R.id.pembayaran_subheader_tv);
    lvPembayaran = mView.findViewById(R.id.pembayaran_lv);
    fabCreatePembayaran = mView.findViewById(R.id.viewPembayaran_fab_createPembayaran);
    pembayaranList = db.getPembayaranData();
    adapter = new PembayaranAdapter(requireContext(), pembayaranList, db);
    lvPembayaran.setAdapter(adapter);
    adapter.notifyDataSetChanged();

    if (!pembayaranList.isEmpty()) {
      tvNoPembayaran.setVisibility(View.INVISIBLE);
    } else {
      tvNoPembayaran.setVisibility(View.VISIBLE);
    }

    fabCreatePembayaran.setOnClickListener(
        view -> {
          Intent i = new Intent(getContext(), CreatePembayaranActivity.class);
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
