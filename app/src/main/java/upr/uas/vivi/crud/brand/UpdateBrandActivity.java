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
import upr.uas.vivi.object.Brand;

public class UpdateBrandActivity extends AppCompatActivity {

  EditText etKode;
  Spinner spinNama, spinKategori;
  Button btnUpdate;
  DBHandler db;
  Brand brand;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_brand);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("Update Brand");

    initWidgets();

    brand = getIntent().getParcelableExtra("Brand");
    etKode.setText(brand.getKode());
    setSelectedNama();
    setSelectedKategori();

    btnUpdate.setOnClickListener(
        view -> {
          String kode = etKode.getText().toString().trim();
          String nama = spinNama.getSelectedItem().toString();
          String kategori = spinKategori.getSelectedItem().toString();

          boolean update = db.updateBrand(brand.getId(), kode, nama, kategori);

          if (update) {
            Toast.makeText(getBaseContext(), "Brand berhasil diupdate", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(getBaseContext(), "Brand gagal diupdate", Toast.LENGTH_SHORT).show();
          }

          Intent i = new Intent(getBaseContext(), MainActivity.class);
          i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          finish();
          startActivity(i);
        });
  }

  private void initWidgets() {
    db = new DBHandler(this);
    etKode = findViewById(R.id.brand_update_kode_et);

    spinNama = findViewById(R.id.brand_update_nama_sp);
    ArrayAdapter<CharSequence> namaAdapter =
        ArrayAdapter.createFromResource(
            this, R.array.brand_nama, android.R.layout.simple_spinner_item);
    namaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinNama.setAdapter(namaAdapter);

    spinKategori = findViewById(R.id.brand_update_kategori_sp);
    ArrayAdapter<CharSequence> kategoriAdapter =
        ArrayAdapter.createFromResource(
            this, R.array.brand_kategori, android.R.layout.simple_spinner_item);
    kategoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinKategori.setAdapter(kategoriAdapter);
    btnUpdate = findViewById(R.id.brand_update_btn);
  }

  private void setSelectedNama() {
    String nama = brand.getNama();

    if (nama.equals("BALENCIAGA")) {
      spinNama.setSelection(0);
    } else if (nama.equals("BVLGARI")) {
      spinNama.setSelection(1);
    } else if (nama.equals("CELINE")) {
      spinNama.setSelection(2);
    } else if (nama.equals("CHANEL")) {
      spinNama.setSelection(3);
    } else if (nama.equals("DIOR")) {
      spinNama.setSelection(4);
    } else if (nama.equals("GUCCI")) {
      spinNama.setSelection(5);
    } else if (nama.equals("HERMES")) {
      spinNama.setSelection(6);
    } else if (nama.equals("LOUIS VUITTON")) {
      spinNama.setSelection(7);
    } else if (nama.equals("NIKE")) {
      spinNama.setSelection(8);
    } else if (nama.equals("PRADA")) {
      spinNama.setSelection(9);
    } else if (nama.equals("YSL")) {
      spinNama.setSelection(10);
    } else if (nama.equals("ZARA")) {
      spinNama.setSelection(11);
    }
  }

  private void setSelectedKategori() {
    String kategori = brand.getKategori();

    if (kategori.equals("Baju")) {
      spinKategori.setSelection(0);
    } else if (kategori.equals("Parfum")) {
      spinKategori.setSelection(1);
    } else if (kategori.equals("Tas")) {
      spinKategori.setSelection(2);
    } else if (kategori.equals("Sepatu")) {
      spinKategori.setSelection(3);
    } else if (kategori.equals("Accessories")) {
      spinKategori.setSelection(4);
    } else if (kategori.equals("Make Up")) {
      spinKategori.setSelection(5);
    }
  }
}
