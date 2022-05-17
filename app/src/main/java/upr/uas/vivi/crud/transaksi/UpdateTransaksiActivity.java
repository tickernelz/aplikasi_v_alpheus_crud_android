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
import upr.uas.vivi.object.Transaksi;

public class UpdateTransaksiActivity extends AppCompatActivity {

  EditText etKodeTransaksi, etJumlah;
  Spinner spinNamaProduk;
  Button btnUpdate;
  DBHandler db;
  Transaksi transaksi;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_transaksi);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("Update Transaksi");

    initWidgets();

    transaksi = getIntent().getParcelableExtra("Transaksi");
    etKodeTransaksi.setText(transaksi.getKodeTransaksi());
    etJumlah.setText(transaksi.getJumlah());
    loadSpinnerData();
    setSelectedNamaProduk();

    btnUpdate.setOnClickListener(
        view -> {
          String kode_transaksi = etKodeTransaksi.getText().toString().trim();
          String nama_produk = spinNamaProduk.getSelectedItem().toString();
          String jumlah = etJumlah.getText().toString().trim();

          boolean update =
              db.updateTransaksi(transaksi.getId(), kode_transaksi, nama_produk, jumlah);

          if (update) {
            Toast.makeText(getBaseContext(), "Transaksi berhasil diupdate", Toast.LENGTH_SHORT)
                .show();
          } else {
            Toast.makeText(getBaseContext(), "Transaksi gagal diupdate", Toast.LENGTH_SHORT).show();
          }

          Intent i = new Intent(getBaseContext(), MainActivity.class);
          i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          finish();
          startActivity(i);
        });
  }

  private void initWidgets() {
    db = new DBHandler(this);
    etKodeTransaksi = findViewById(R.id.transaksi_update_kode_et);
    spinNamaProduk = findViewById(R.id.transaksi_update_kode_produk_sp);
    etJumlah = findViewById(R.id.transaksi_update_jumlah_et);

    btnUpdate = findViewById(R.id.transaksi_update_btn);
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

  private void setSelectedNamaProduk() {
    int spinnerPosition = getIndex(spinNamaProduk, transaksi.getNamaProduk());
    spinNamaProduk.setSelection(spinnerPosition);
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
