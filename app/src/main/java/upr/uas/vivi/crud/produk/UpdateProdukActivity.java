package upr.uas.vivi.crud.produk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import upr.uas.vivi.MainActivity;
import upr.uas.vivi.R;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Produk;

public class UpdateProdukActivity extends AppCompatActivity {

  EditText etKodeProduk, etNama, etUkuran, etWarna, etSatuan, etHarga, etStok;
  Spinner spinKodeBrand;
  Button btnUpdate;
  DBHandler db;
  Produk produk;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_produk);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("Update Produk");

    initWidgets();

    produk = getIntent().getParcelableExtra("Produk");
    etKodeProduk.setText(produk.getKodeProduk());
    etNama.setText(produk.getNama());
    etUkuran.setText(produk.getUkuran());
    etWarna.setText(produk.getWarna());
    etSatuan.setText(produk.getSatuan());
    etHarga.setText(produk.getHarga());
    etStok.setText(produk.getStok());
    loadSpinnerData();
    setSelectedKodeBrand();

    btnUpdate.setOnClickListener(
        view -> {
          String kode_produk = etKodeProduk.getText().toString().trim();
          String kode_brand = spinKodeBrand.getSelectedItem().toString();
          String nama = etNama.getText().toString().trim();
          String ukuran = etUkuran.getText().toString().trim();
          String warna = etWarna.getText().toString().trim();
          String satuan = etSatuan.getText().toString().trim();
          String harga = etHarga.getText().toString().trim();
          String stok = etStok.getText().toString().trim();

          boolean update =
              db.updateProduk(
                  produk.getId(),
                  kode_produk,
                  kode_brand,
                  nama,
                  ukuran,
                  warna,
                  satuan,
                  harga,
                  stok);

          if (update) {
            Toast.makeText(getBaseContext(), "Produk berhasil diupdate", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(getBaseContext(), "Produk gagal diupdate", Toast.LENGTH_SHORT).show();
          }

          Intent i = new Intent(getBaseContext(), MainActivity.class);
          i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          finish();
          startActivity(i);
        });
  }

  private void initWidgets() {
    db = new DBHandler(this);
    etKodeProduk = findViewById(R.id.produk_update_kode_et);
    spinKodeBrand = findViewById(R.id.produk_update_kode_brand_sp);
    etNama = findViewById(R.id.produk_update_nama_et);
    etUkuran = findViewById(R.id.produk_update_ukuran_et);
    etWarna = findViewById(R.id.produk_update_warna_et);
    etSatuan = findViewById(R.id.produk_update_satuan_et);
    etHarga = findViewById(R.id.produk_update_harga_et);
    etStok = findViewById(R.id.produk_update_stok_et);

    btnUpdate = findViewById(R.id.produk_update_btn);
  }

  private void loadSpinnerData() {
    DBHandler db = new DBHandler(getApplicationContext());
    List<String> kode_brand = db.getKodeBrand();

    // Creating adapter for spinner
    ArrayAdapter<String> dataAdapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kode_brand);

    // Drop down layout style - list view with radio button
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    // attaching data adapter to spinner
    spinKodeBrand.setAdapter(dataAdapter);
  }

  private void setSelectedKodeBrand() {
    int spinnerPosition = getIndex(spinKodeBrand, produk.getKodeBrand());
    spinKodeBrand.setSelection(spinnerPosition);
  }

  private int getIndex(Spinner spinner, String myString) {
    int index = 0;

    for (int i = 0; i < spinner.getCount(); i++) {
      if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
        index = i;
        break;
      }
    }
    return index;
  }
}
