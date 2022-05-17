package upr.uas.vivi.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Pembayaran implements Parcelable {

  public static final Creator<Pembayaran> CREATOR =
      new Creator<Pembayaran>() {
        @Override
        public Pembayaran createFromParcel(Parcel in) {
          return new Pembayaran(in);
        }

        @Override
        public Pembayaran[] newArray(int size) {
          return new Pembayaran[size];
        }
      };
  private int id, total_pembelian, total_bayar, sisa_bayar;
  private String nota, kode_transaksi, tanggal;

  public Pembayaran(
      int id,
      String nota,
      String kode_transaksi,
      String tanggal,
      int total_pembelian,
      int total_bayar,
      int sisa_bayar) {
    this.id = id;
    this.nota = nota;
    this.kode_transaksi = kode_transaksi;
    this.tanggal = tanggal;
    this.total_pembelian = total_pembelian;
    this.total_bayar = total_bayar;
    this.sisa_bayar = sisa_bayar;
  }

  protected Pembayaran(Parcel in) {
    id = in.readInt();
    nota = in.readString();
    kode_transaksi = in.readString();
    tanggal = in.readString();
    total_pembelian = in.readInt();
    total_bayar = in.readInt();
    sisa_bayar = in.readInt();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNota() {
    return nota;
  }

  public void setNota(String nota) {
    this.nota = nota;
  }

  public String getKodeTransaksi() {
    return kode_transaksi;
  }

  public void setKodeTransaksi(String kode_transaksi) {
    this.kode_transaksi = kode_transaksi;
  }

  public String getTanggal() {
    return tanggal;
  }

  public void setTanggal(String tanggal) {
    this.tanggal = tanggal;
  }

  public int getTotalPembelian() {
    return total_pembelian;
  }

  public void setTotalPembelian(int total_pembelian) {
    this.total_pembelian = total_pembelian;
  }

  public int getTotalBayar() {
    return total_bayar;
  }

  public void setTotalBayar(int total_bayar) {
    this.total_bayar = total_bayar;
  }

  public int getSisaBayar() {
    return sisa_bayar;
  }

  public void setSisaBayar() {
    this.sisa_bayar = total_pembelian - total_bayar;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(id);
    parcel.writeString(nota);
    parcel.writeString(kode_transaksi);
    parcel.writeString(tanggal);
    parcel.writeInt(total_pembelian);
    parcel.writeInt(total_bayar);
    parcel.writeInt(sisa_bayar);
  }
}
