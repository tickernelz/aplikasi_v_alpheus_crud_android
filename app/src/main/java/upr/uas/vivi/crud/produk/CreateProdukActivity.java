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

public class CreateProdukActivity extends AppCompatActivity {

  DBHandler db;

  EditText etKodeProduk, etNama, etUkuran, etWarna, etSatuan, etHarga, etStok;
  Spinner spinKodeBrand;
  Button btnAdd;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_produk);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("Create Produk");
    db = new DBHandler(getApplicationContext());

    initWidgets();

    btnAdd.setOnClickListener(
        view -> {
          String kode_produk = etKodeProduk.getText().toString().trim();
          String kode_brand = spinKodeBrand.getSelectedItem().toString();
          String nama = etNama.getText().toString().trim();
          String ukuran = etUkuran.getText().toString().trim();
          String warna = etWarna.getText().toString().trim();
          String satuan = etSatuan.getText().toString().trim();
          String harga = etHarga.getText().toString().trim();
          String stok = etStok.getText().toString().trim();

          if (!nama.equals("")
              && !ukuran.equals("")
              && !satuan.equals("")
              && !kode_produk.equals("")
              && !kode_brand.equals("")
              && !warna.equals("")
              && !harga.equals("")
              && !stok.equals("")) {
            boolean insert =
                db.insertProduk(kode_produk, kode_brand, nama, ukuran, warna, satuan, harga, stok);
            etKodeProduk.setText("");
            spinKodeBrand.setSelection(0);
            etNama.setText("");
            etUkuran.setText("");
            etWarna.setText("");
            etSatuan.setText("");
            etHarga.setText("");
            etStok.setText("");
            if (insert) {
              Toast.makeText(getBaseContext(), "Produk berhasil ditambahkan", Toast.LENGTH_SHORT)
                  .show();
            } else {
              Toast.makeText(getBaseContext(), "Produk gagal ditambahkan", Toast.LENGTH_SHORT)
                  .show();
            }
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(i);
          } else {
            Toast.makeText(this, "Nama Produk, Kategori dan Kode harus diisi!", Toast.LENGTH_SHORT)
                .show();
          }
        });
  }

  private void initWidgets() {
    etKodeProduk = findViewById(R.id.produk_create_kode_et);
    spinKodeBrand = findViewById(R.id.produk_create_kode_brand_sp);
    etNama = findViewById(R.id.produk_create_nama_et);
    etUkuran = findViewById(R.id.produk_create_ukuran_et);
    etWarna = findViewById(R.id.produk_create_warna_et);
    etSatuan = findViewById(R.id.produk_create_satuan_et);
    etHarga = findViewById(R.id.produk_create_harga_et);
    etStok = findViewById(R.id.produk_create_stok_et);
    loadSpinnerData();

    btnAdd = findViewById(R.id.produk_create_btn);
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
}
