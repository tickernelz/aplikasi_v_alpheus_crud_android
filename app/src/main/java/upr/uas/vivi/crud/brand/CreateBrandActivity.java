package upr.uas.vivi.crud.brand;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import upr.uas.vivi.MainActivity;
import upr.uas.vivi.R;
import upr.uas.vivi.db.DBHandler;

public class CreateBrandActivity extends AppCompatActivity {

  DBHandler db;

  EditText etKode;
  Spinner spinNama, spinKategori;
  Button btnAdd;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_brand);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("Create Brand");
    db = new DBHandler(getApplicationContext());

    initWidgets();

    btnAdd.setOnClickListener(
        view -> {
          String kode = etKode.getText().toString().trim();
          String nama = spinNama.getSelectedItem().toString();
          String kategori = spinKategori.getSelectedItem().toString();

          if (!nama.equals("") && !kode.equals("") && !kategori.equals("")) {
            boolean insert = db.insertBrand(kode, nama, kategori);
            etKode.setText("");
            spinNama.setSelection(0);
            spinKategori.setSelection(0);
            if (insert) {
              Toast.makeText(getBaseContext(), "Brand berhasil ditambahkan", Toast.LENGTH_SHORT)
                  .show();
            } else {
              Toast.makeText(getBaseContext(), "Brand gagal ditambahkan", Toast.LENGTH_SHORT)
                  .show();
            }
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(i);
          } else {
            Toast.makeText(this, "Nama Brand, Kategori dan Kode harus diisi!", Toast.LENGTH_SHORT)
                .show();
          }
        });
  }

  private void initWidgets() {
    etKode = findViewById(R.id.brand_create_kode_et);

    spinNama = findViewById(R.id.brand_create_nama_sp);
    ArrayAdapter<CharSequence> namaAdapter =
        ArrayAdapter.createFromResource(
            this, R.array.brand_nama, android.R.layout.simple_spinner_item);
    namaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinNama.setAdapter(namaAdapter);

    spinKategori = findViewById(R.id.brand_create_kategori_sp);
    ArrayAdapter<CharSequence> kategoriAdapter =
        ArrayAdapter.createFromResource(
            this, R.array.brand_kategori, android.R.layout.simple_spinner_item);
    kategoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinKategori.setAdapter(kategoriAdapter);

    btnAdd = findViewById(R.id.brand_create_btn);
  }
}
