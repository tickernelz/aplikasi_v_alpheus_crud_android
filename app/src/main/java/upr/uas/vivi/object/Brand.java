package upr.uas.vivi.object;

import android.os.Parcel;
import android.os.Parcelable;

public class Brand implements Parcelable {

  public static final Creator<Brand> CREATOR =
      new Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel in) {
          return new Brand(in);
        }

        @Override
        public Brand[] newArray(int size) {
          return new Brand[size];
        }
      };
  private int id;
  private String kode, nama, kategori;

  public Brand(int id, String kode, String nama, String kategori) {
    this.id = id;
    this.kode = kode;
    this.nama = nama;
    this.kategori = kategori;
  }

  protected Brand(Parcel in) {
    id = in.readInt();
    kode = in.readString();
    nama = in.readString();
    kategori = in.readString();
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKode() {
    return kode;
  }

  public void setKode(String kode) {
    this.kode = kode;
  }

  public String getNama() {
    return nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public String getKategori() {
    return kategori;
  }

  public void setKategori(String kategori) {
    this.kategori = kategori;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(id);
    parcel.writeString(kode);
    parcel.writeString(nama);
    parcel.writeString(kategori);
  }
}
