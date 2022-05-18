package upr.uas.vivi.crud.transaksi;

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

public class CreateTransaksiActivity extends AppCompatActivity {

  DBHandler db;

  EditText etKodeTransaksi, etJumlah;
  Spinner spinNamaProduk;
  Button btnAdd;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_transaksi);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("Create Transaksi");
    db = new DBHandler(getApplicationContext());

    initWidgets();

    btnAdd.setOnClickListener(
        view -> {
          String kode_transaksi = etKodeTransaksi.getText().toString().trim();
          String nama_produk = spinNamaProduk.getSelectedItem().toString();
          String jumlah = etJumlah.getText().toString().trim();

          if (!kode_transaksi.equals("") && !nama_produk.equals("") && !jumlah.equals("")) {
            boolean insert = db.insertTransaksi(kode_transaksi, nama_produk, jumlah);
            etKodeTransaksi.setText("");
            spinNamaProduk.setSelection(0);
            etJumlah.setText("");
            if (insert) {
              Toast.makeText(getBaseContext(), "Transaksi berhasil ditambahkan", Toast.LENGTH_SHORT)
                  .show();
            } else {
              Toast.makeText(getBaseContext(), "Transaksi gagal ditambahkan", Toast.LENGTH_SHORT)
                  .show();
            }
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(i);
          } else {
            Toast.makeText(
                    this, "Nama Transaksi, Kategori dan Kode harus diisi!", Toast.LENGTH_SHORT)
                .show();
          }
        });
  }

  private void initWidgets() {
    etKodeTransaksi = findViewById(R.id.transaksi_create_kode_et);
    spinNamaProduk = findViewById(R.id.transaksi_create_kode_produk_sp);
    etJumlah = findViewById(R.id.transaksi_create_jumlah_et);
    loadSpinnerData();

    btnAdd = findViewById(R.id.transaksi_create_btn);
  }

  private void loadSpinnerData() {
    DBHandler db = new DBHandler(getApplicationContext());
    List<String> nama_produk = db.getNamaProduk();

    // Creating adapter for spinner
    ArrayAdapter<String> dataAdapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nama_produk);

    // Drop down layout style - list view with radio button
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    // attaching data adapter to spinner
    spinNamaProduk.setAdapter(dataAdapter);
  }
}
