package upr.uas.vivi.ui.brand;

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
import upr.uas.vivi.adapter.BrandAdapter;
import upr.uas.vivi.crud.brand.CreateBrandActivity;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Brand;

public class BrandFragment extends Fragment {

  DBHandler db;
  ArrayList<Brand> brandList;
  ListView lvBrand;
  BrandAdapter adapter;
  TextView tvNoBrand;
  FloatingActionButton fabCreateBrand;
  View mView;

  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View mView = inflater.inflate(R.layout.fragment_brand, container, false);
    db = new DBHandler(requireContext());

    brandList = new ArrayList<>();
    tvNoBrand = mView.findViewById(R.id.brand_subheader_tv);
    lvBrand = mView.findViewById(R.id.brand_lv);
    fabCreateBrand = mView.findViewById(R.id.viewBrand_fab_createBrand);
    brandList = db.getBrandData();
    adapter = new BrandAdapter(requireContext(), brandList, db);
    lvBrand.setAdapter(adapter);
    adapter.notifyDataSetChanged();

    if (!brandList.isEmpty()) {
      tvNoBrand.setVisibility(View.INVISIBLE);
    } else {
      tvNoBrand.setVisibility(View.VISIBLE);
    }

    fabCreateBrand.setOnClickListener(
        view -> {
          Intent i = new Intent(getContext(), CreateBrandActivity.class);
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
