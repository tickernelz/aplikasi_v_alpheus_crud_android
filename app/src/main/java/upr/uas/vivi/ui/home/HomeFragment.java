package upr.uas.vivi.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import upr.uas.vivi.R;
import upr.uas.vivi.adapter.HomeAdapter;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Produk;

public class HomeFragment extends Fragment {

  DBHandler db;
  ArrayList<Produk> produkList;
  ListView lvHome;
  HomeAdapter adapter;
  TextView tvNoHome;
  View mView;

  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View mView = inflater.inflate(R.layout.fragment_home, container, false);
    db = new DBHandler(requireContext());

    produkList = new ArrayList<>();
    tvNoHome = mView.findViewById(R.id.home_data_subheader_tv);
    lvHome = mView.findViewById(R.id.home_data_lv);
    produkList = db.getProdukData();
    adapter = new HomeAdapter(requireContext(), produkList, db);
    lvHome.setAdapter(adapter);
    adapter.notifyDataSetChanged();

    if (!produkList.isEmpty()) {
      tvNoHome.setVisibility(View.INVISIBLE);
    } else {
      tvNoHome.setVisibility(View.VISIBLE);
    }

    return mView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mView = null;
  }
}
