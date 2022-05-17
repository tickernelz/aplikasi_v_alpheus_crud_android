package upr.uas.vivi.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Produk implements Parcelable {

  public static final Creator<Produk> CREATOR =
      new Creator<Produk>() {
        @Override
        public Produk createFromParcel(Parcel in) {
          return new Produk(in);
        }

        @Override
        public Produk[] newArray(int size) {
          return new Produk[size];
        }
      };
  private int id;
  private String kode_produk, kode_brand, nama, ukuran, warna, satuan, harga, stok;

  public Produk(
      int id,
      String kode_produk,
      String kode_brand,
      String nama,
      String ukuran,
      String warna,
      String satuan,
      String harga,
      String stok) {
    this.id = id;
    this.kode_produk = kode_produk;
    this.kode_brand = kode_brand;
    this.nama = nama;
    this.ukuran = ukuran;
    this.warna = warna;
    this.satuan = satuan;
    this.harga = harga;
    this.stok = stok;
  }

  protected Produk(Parcel in) {
    id = in.readInt();
    kode_produk = in.readString();
    kode_brand = in.readString();
    nama = in.readString();
    ukuran = in.readString();
    warna = in.readString();
    satuan = in.readString();
    harga = in.readString();
    stok = in.readString();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKodeProduk() {
    return kode_produk;
  }

  public void setKodeProduk(String kode_produk) {
    this.kode_produk = kode_produk;
  }

  public String getKodeBrand() {
    return kode_brand;
  }

  public void setKodeBrand(String kode_brand) {
    this.kode_brand = kode_brand;
  }

  public String getNama() {
    return nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public String getUkuran() {
    return ukuran;
  }

  public void setUkuran(String ukuran) {
    this.ukuran = ukuran;
  }

  public String getWarna() {
    return warna;
  }

  public void setWarna(String warna) {
    this.warna = warna;
  }

  public String getSatuan() {
    return satuan;
  }

  public void setSatuan(String satuan) {
    this.satuan = satuan;
  }

  public String getHarga() {
    return harga;
  }

  public void setHarga(String harga) {
    this.harga = harga;
  }

  public String getStok() {
    return stok;
  }

  public void setStok(String stok) {
    this.stok = stok;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(id);
    parcel.writeString(kode_produk);
    parcel.writeString(kode_brand);
    parcel.writeString(nama);
    parcel.writeString(ukuran);
    parcel.writeString(warna);
    parcel.writeString(satuan);
    parcel.writeString(harga);
    parcel.writeString(stok);
  }
}
