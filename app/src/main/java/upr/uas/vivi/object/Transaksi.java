package upr.uas.vivi.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaksi implements Parcelable {

  public static final Creator<Transaksi> CREATOR =
      new Creator<Transaksi>() {
        @Override
        public Transaksi createFromParcel(Parcel in) {
          return new Transaksi(in);
        }

        @Override
        public Transaksi[] newArray(int size) {
          return new Transaksi[size];
        }
      };
  private int id;
  private String kode_transaksi, nama_produk, jumlah;

  public Transaksi(int id, String kode_transaksi, String nama_produk, String jumlah) {
    this.id = id;
    this.kode_transaksi = kode_transaksi;
    this.nama_produk = nama_produk;
    this.jumlah = jumlah;
  }

  protected Transaksi(Parcel in) {
    id = in.readInt();
    kode_transaksi = in.readString();
    nama_produk = in.readString();
    jumlah = in.readString();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKodeTransaksi() {
    return kode_transaksi;
  }

  public void setKodeTransaksi(String kode_transaksi) {
    this.kode_transaksi = kode_transaksi;
  }

  public String getNamaProduk() {
    return nama_produk;
  }

  public void setNamaProduk(String nama_produk) {
    this.nama_produk = nama_produk;
  }

  public String getJumlah() {
    return jumlah;
  }

  public void setJumlah(String jumlah) {
    this.jumlah = jumlah;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(id);
    parcel.writeString(kode_transaksi);
    parcel.writeString(nama_produk);
    parcel.writeString(jumlah);
  }
}
