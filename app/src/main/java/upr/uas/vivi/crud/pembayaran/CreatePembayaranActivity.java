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

public class CreatePembayaranActivity extends AppCompatActivity {

  final Calendar myCalendar = Calendar.getInstance();
  DBHandler db;
  EditText etNota, etTanggal, etTotalPembelian, etTotalBayar;
  Spinner spinKodeTransaksi;
  Button btnAdd;

  @RequiresApi(api = Build.VERSION_CODES.N)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_pembayaran);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setTitle("Create Pembayaran");
    db = new DBHandler(getApplicationContext());

    initWidgets();

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
                    CreatePembayaranActivity.this,
                    date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
                .show());

    btnAdd.setOnClickListener(
        view -> {
          String nota = etNota.getText().toString().trim();
          String kode_transaksi = spinKodeTransaksi.getSelectedItem().toString();
          String tanggal = etTanggal.getText().toString().trim();
          String total_pembelian = etTotalPembelian.getText().toString();
          int final_total_pembelian = Integer.parseInt(total_pembelian);
          String total_bayar = etTotalBayar.getText().toString();
          int final_total_bayar = Integer.parseInt(total_bayar);

          if (!nota.equals("")
              && !tanggal.equals("")
              && !total_pembelian.equals("")
              && !kode_transaksi.equals("")
              && !total_bayar.equals("")) {
            boolean insert =
                db.insertPembayaran(
                    nota, kode_transaksi, tanggal, final_total_pembelian, final_total_bayar);
            etNota.setText("");
            spinKodeTransaksi.setSelection(0);
            etTanggal.setText("");
            etTotalPembelian.setText("");
            etTotalBayar.setText("");
            if (insert) {
              Toast.makeText(
                      getBaseContext(), "Pembayaran berhasil ditambahkan", Toast.LENGTH_SHORT)
                  .show();
            } else {
              Toast.makeText(getBaseContext(), "Pembayaran gagal ditambahkan", Toast.LENGTH_SHORT)
                  .show();
            }
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(i);
          } else {
            Toast.makeText(
                    this, "Nama Pembayaran, Kategori dan Kode harus diisi!", Toast.LENGTH_SHORT)
                .show();
          }
        });
  }

  private void initWidgets() {
    etNota = findViewById(R.id.pembayaran_create_nota_et);
    spinKodeTransaksi = findViewById(R.id.pembayaran_create_kode_transaksi_sp);
    etTanggal = findViewById(R.id.pembayaran_create_tanggal_et);
    etTotalPembelian = findViewById(R.id.pembayaran_create_total_pembelian_et);
    etTotalBayar = findViewById(R.id.pembayaran_create_total_bayar_et);
    loadSpinnerData();

    btnAdd = findViewById(R.id.pembayaran_create_btn);
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  private void updateLabel() {
    String myFormat = "dd/MM/yyyy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
    etTanggal.setText(dateFormat.format(myCalendar.getTime()));
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
}
