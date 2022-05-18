package upr.uas.vivi.crud.pembayaran;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import upr.uas.vivi.MainActivity;
import upr.uas.vivi.R;
import upr.uas.vivi.db.DBHandler;
import upr.uas.vivi.object.Pembayaran;

public class UpdatePembayaranActivity extends AppCompatActivity {

  final Calendar myCalendar = Calendar.getInstance();
  EditText etNota, etTanggal, etTotalPembelian, etTotalBayar;
  Spinner spinKodeTransaksi;
  Button btnUpdate;
  DBHandler db;
  Pembayaran pembayaran;

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_pembayaran);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("Update Pembayaran");

    initWidgets();

    pembayaran = getIntent().getParcelableExtra("Pembayaran");
    etNota.setText(pembayaran.getNota());
    etTanggal.setText(pembayaran.getTanggal());
    etTotalPembelian.setText(pembayaran.getTotalPembelian());
    etTotalBayar.setText(pembayaran.getTotalBayar());
    loadSpinnerData();
    setSelectedKodeBrand();

    DatePickerDialog.OnDateSetListener date =
        (view, year, month, day) -> {
          myCalendar.set(Calendar.YEAR, year);
          myCalendar.set(Calendar.MONTH, month);
          myCalendar.set(Calendar.DAY_OF_MONTH, day);
          updateLabel();
        };

    etTanggal.setOnClickListener(
        view ->
            new DatePickerDialog(
                    UpdatePembayaranActivity.this,
                    date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
                .show());

    btnUpdate.setOnClickListener(
        view -> {
          String nota = etNota.getText().toString().trim();
          String kode_transaksi = spinKodeTransaksi.getSelectedItem().toString();
          String tanggal = etTanggal.getText().toString().trim();
          String total_pembelian = etTotalPembelian.getText().toString();
          int final_total_pembelian = Integer.parseInt(total_pembelian);
          String total_bayar = etTotalBayar.getText().toString();
          int final_total_bayar = Integer.parseInt(total_bayar);

          boolean update =
              db.updatePembayaran(
                  pembayaran.getId(),
                  nota,
                  kode_transaksi,
                  tanggal,
                  final_total_pembelian,
                  final_total_bayar);

          if (update) {
            Toast.makeText(getBaseContext(), "Pembayaran berhasil diupdate", Toast.LENGTH_SHORT)
                .show();
          } else {
            Toast.makeText(getBaseContext(), "Pembayaran gagal diupdate", Toast.LENGTH_SHORT)
                .show();
          }

          Intent i = new Intent(getBaseContext(), MainActivity.class);
          i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          finish();
          startActivity(i);
        });
  }

  private void initWidgets() {
    db = new DBHandler(this);
    etNota = findViewById(R.id.pembayaran_update_nota_et);
    spinKodeTransaksi = findViewById(R.id.pembayaran_update_kode_transaksi_sp);
    etTanggal = findViewById(R.id.pembayaran_update_tanggal_et);
    etTotalPembelian = findViewById(R.id.pembayaran_update_total_pembelian_et);
    etTotalBayar = findViewById(R.id.pembayaran_update_total_bayar_et);

    btnUpdate = findViewById(R.id.pembayaran_update_btn);
  }

  private void loadSpinnerData() {
    DBHandler db = new DBHandler(getApplicationContext());
    List<String> kode_transaksi = db.getKodeTransaksi();

    // Creating adapter for spinner
    ArrayAdapter<String> dataAdapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, kode_transaksi);

    // Drop down layout style - list view with radio button
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    // attaching data adapter to spinner
    spinKodeTransaksi.setAdapter(dataAdapter);
  }

  private void setSelectedKodeBrand() {
    int spinnerPosition = getIndex(spinKodeTransaksi, pembayaran.getKodeTransaksi());
    spinKodeTransaksi.setSelection(spinnerPosition);
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  private void updateLabel() {
    String myFormat = "dd/MM/yyyy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
    etTanggal.setText(dateFormat.format(myCalendar.getTime()));
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
