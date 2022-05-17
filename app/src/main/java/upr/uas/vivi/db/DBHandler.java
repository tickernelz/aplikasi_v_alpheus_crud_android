package upr.uas.vivi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import upr.uas.vivi.db.params.BrandParams;
import upr.uas.vivi.db.params.ProdukParams;
import upr.uas.vivi.db.params.UserParams;
import upr.uas.vivi.object.Brand;
import upr.uas.vivi.object.Produk;
import upr.uas.vivi.object.User;

public class DBHandler extends SQLiteOpenHelper {
  public static final int DB_VERSION = 1;
  public static final String DB_NAME = "Data.db";

  private static final String SQL_CREATE_USER =
      "CREATE TABLE "
          + UserParams.TABLE_NAME
          + "("
          + UserParams.KEY_ID
          + " INTEGER PRIMARY KEY, "
          + UserParams.KEY_NAME
          + " TEXT, "
          + UserParams.KEY_USERNAME
          + " TEXT, "
          + UserParams.KEY_PASSWORD
          + " TEXT, "
          + UserParams.KEY_IS_LOGIN
          + " INTEGER DEFAULT 0)";

  private static final String SQL_CREATE_BRAND =
      "CREATE TABLE "
          + BrandParams.TABLE_NAME
          + "("
          + BrandParams.KEY_ID
          + " INTEGER PRIMARY KEY, "
          + BrandParams.KEY_KODE
          + " TEXT, "
          + BrandParams.KEY_NAMA
          + " TEXT, "
          + BrandParams.KEY_KATEGORI
          + " TEXT "
          + ")";

  private static final String SQL_CREATE_PRODUK =
      "CREATE TABLE "
          + ProdukParams.TABLE_NAME
          + "("
          + ProdukParams.KEY_ID
          + " INTEGER PRIMARY KEY, "
          + ProdukParams.KEY_KODE_PRODUK
          + " TEXT, "
          + ProdukParams.KEY_KODE_BRAND
          + " TEXT, "
          + ProdukParams.KEY_NAMA
          + " TEXT, "
          + ProdukParams.KEY_UKURAN
          + " TEXT, "
          + ProdukParams.KEY_WARNA
          + " TEXT, "
          + ProdukParams.KEY_SATUAN
          + " TEXT, "
          + ProdukParams.KEY_HARGA
          + " TEXT, "
          + ProdukParams.KEY_STOK
          + " TEXT "
          + ")";

  private static final String SQL_DELETE_USER = "DROP TABLE IF EXISTS " + UserParams.TABLE_NAME;

  private static final String SQL_DELETE_BRAND = "DROP TABLE IF EXISTS " + BrandParams.TABLE_NAME;

  private static final String SQL_DELETE_PRODUK = "DROP TABLE IF EXISTS " + ProdukParams.TABLE_NAME;

  public DBHandler(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_USER);
    db.execSQL(SQL_CREATE_BRAND);
    db.execSQL(SQL_CREATE_PRODUK);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(SQL_DELETE_USER);
    db.execSQL(SQL_DELETE_BRAND);
    db.execSQL(SQL_DELETE_PRODUK);
    onCreate(db);
  }

  @Override
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }

  public String getName(User user) {
    SQLiteDatabase db = this.getReadableDatabase();

    // Generating query to read from DataBase
    String select =
        "SELECT * FROM "
            + UserParams.TABLE_NAME
            + " WHERE "
            + UserParams.KEY_ID
            + " = "
            + user.getId();
    Cursor cursor = db.rawQuery(select, null);
    if (cursor.moveToFirst()) {
      user.setName(cursor.getString(1));
      cursor.close();
      db.close();
      return user.getName();
    } else {
      cursor.close();
      db.close();
      return null;
    }
  }

  public String getUsername(User user) {
    SQLiteDatabase db = this.getReadableDatabase();

    // Generating query to read from DataBase
    String select =
        "SELECT * FROM "
            + UserParams.TABLE_NAME
            + " WHERE "
            + UserParams.KEY_ID
            + " = "
            + user.getId();
    Cursor cursor = db.rawQuery(select, null);
    if (cursor.moveToFirst()) {
      user.setUsername(cursor.getString(2));
      cursor.close();
      db.close();
      return user.getUsername(false);
    } else {
      cursor.close();
      db.close();
      return null;
    }
  }

  public void InsertUser(User user) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(UserParams.KEY_NAME, user.getName());
    values.put(UserParams.KEY_USERNAME, user.getUsername(false));
    values.put(UserParams.KEY_PASSWORD, user.getPassword(false));

    db.insert(UserParams.TABLE_NAME, null, values);
    db.close();
  }

  public boolean checkUser(User user) {
    SQLiteDatabase db = this.getReadableDatabase();

    // Generating query to read from DataBase
    String select =
        "SELECT * FROM "
            + UserParams.TABLE_NAME
            + " WHERE "
            + UserParams.KEY_USERNAME
            + " = "
            + user.getUsername(true)
            + " AND "
            + UserParams.KEY_PASSWORD
            + " = "
            + user.getPassword(true);
    Cursor cursor = db.rawQuery(select, null);
    if (cursor.moveToFirst()) {
      user.setId(cursor.getInt(0));
      user.setName(cursor.getString(1));
      user.setUsername(cursor.getString(2));
      user.setPassword(cursor.getString(3));
      user.setIsLogin(cursor.getInt(4));
      cursor.close();
      db.close();
      return true;
    } else {
      cursor.close();
      db.close();
      return false;
    }
  }

  public boolean checkIsLogin(User user) {
    SQLiteDatabase db = this.getReadableDatabase();

    // Generating query to read from DataBase
    String select =
        "SELECT * FROM " + UserParams.TABLE_NAME + " WHERE " + UserParams.KEY_IS_LOGIN + " = " + 1;
    Cursor cursor = db.rawQuery(select, null);
    if (cursor.moveToFirst()) {
      user.setId(cursor.getInt(0));
      user.setName(cursor.getString(1));
      user.setUsername(cursor.getString(2));
      user.setPassword(cursor.getString(3));
      cursor.close();
      db.close();
      return true;
    } else {
      cursor.close();
      db.close();
      return false;
    }
  }

  public int updateIsLogin(User user) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();

    values.put(UserParams.KEY_IS_LOGIN, user.getIsLogin());

    // Updating
    return db.update(
        UserParams.TABLE_NAME,
        values,
        UserParams.KEY_ID + "=?",
        new String[] {String.valueOf(user.getId())});
  }

  /* Brand */

  public boolean insertBrand(String kode, String nama, String kategori) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(BrandParams.KEY_KODE, kode);
    contentValues.put(BrandParams.KEY_NAMA, nama);
    contentValues.put(BrandParams.KEY_KATEGORI, kategori);

    long result = db.insert(BrandParams.TABLE_NAME, null, contentValues);

    return result != -1;
  }

  public boolean updateBrand(int id, String kode, String nama, String kategori) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(BrandParams.KEY_KODE, kode);
    contentValues.put(BrandParams.KEY_NAMA, nama);
    contentValues.put(BrandParams.KEY_KATEGORI, kategori);

    long result =
        db.update(
            BrandParams.TABLE_NAME,
            contentValues,
            BrandParams.KEY_ID + "=?",
            new String[] {String.valueOf(id)});

    db.close();
    return result != -1;
  }

  public boolean deleteBrand(int id) {
    SQLiteDatabase db = this.getWritableDatabase();
    long result =
        db.delete(
            BrandParams.TABLE_NAME, BrandParams.KEY_ID + "=?", new String[] {String.valueOf(id)});

    db.close();
    return result != -1;
  }

  public ArrayList<Brand> getBrandData() {
    ArrayList<Brand> brandList = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + BrandParams.TABLE_NAME, null);

    while (cursor.moveToNext()) {
      int id = cursor.getInt(0);
      String kode = cursor.getString(1);
      String nama = cursor.getString(2);
      String kategori = cursor.getString(3);

      Brand brand = new Brand(id, kode, nama, kategori);
      brandList.add(brand);
    }
    return brandList;
  }

  /* Produk */

  public boolean insertProduk(
      String kode_produk,
      String kode_brand,
      String nama,
      String ukuran,
      String warna,
      String satuan,
      String harga,
      String stok) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(ProdukParams.KEY_KODE_PRODUK, kode_produk);
    contentValues.put(ProdukParams.KEY_KODE_BRAND, kode_brand);
    contentValues.put(ProdukParams.KEY_NAMA, nama);
    contentValues.put(ProdukParams.KEY_UKURAN, ukuran);
    contentValues.put(ProdukParams.KEY_WARNA, warna);
    contentValues.put(ProdukParams.KEY_SATUAN, satuan);
    contentValues.put(ProdukParams.KEY_HARGA, harga);
    contentValues.put(ProdukParams.KEY_STOK, stok);

    long result = db.insert(ProdukParams.TABLE_NAME, null, contentValues);

    return result != -1;
  }

  public boolean updateProduk(
      int id,
      String kode_produk,
      String kode_brand,
      String nama,
      String ukuran,
      String warna,
      String satuan,
      String harga,
      String stok) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(ProdukParams.KEY_KODE_PRODUK, kode_produk);
    contentValues.put(ProdukParams.KEY_KODE_BRAND, kode_brand);
    contentValues.put(ProdukParams.KEY_NAMA, nama);
    contentValues.put(ProdukParams.KEY_UKURAN, ukuran);
    contentValues.put(ProdukParams.KEY_WARNA, warna);
    contentValues.put(ProdukParams.KEY_SATUAN, satuan);
    contentValues.put(ProdukParams.KEY_HARGA, harga);
    contentValues.put(ProdukParams.KEY_STOK, stok);

    long result =
        db.update(
            ProdukParams.TABLE_NAME,
            contentValues,
            ProdukParams.KEY_ID + "=?",
            new String[] {String.valueOf(id)});

    db.close();
    return result != -1;
  }

  public boolean deleteProduk(int id) {
    SQLiteDatabase db = this.getWritableDatabase();
    long result =
        db.delete(
            ProdukParams.TABLE_NAME, ProdukParams.KEY_ID + "=?", new String[] {String.valueOf(id)});

    db.close();
    return result != -1;
  }

  public ArrayList<Produk> getProdukData() {
    ArrayList<Produk> produkList = new ArrayList<>();
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + ProdukParams.TABLE_NAME, null);

    while (cursor.moveToNext()) {
      int id = cursor.getInt(0);
      String kode_produk = cursor.getString(1);
      String kode_brand = cursor.getString(2);
      String nama = cursor.getString(3);
      String ukuran = cursor.getString(4);
      String warna = cursor.getString(5);
      String satuan = cursor.getString(6);
      String harga = cursor.getString(7);
      String stok = cursor.getString(8);

      Produk produk =
          new Produk(id, kode_produk, kode_brand, nama, ukuran, warna, satuan, harga, stok);
      produkList.add(produk);
    }
    return produkList;
  }

  public List<String> getKodeBrand() {
    List<String> list = new ArrayList<>();

    // Select All Query
    String selectQuery = "SELECT " + BrandParams.KEY_KODE + " FROM " + BrandParams.TABLE_NAME;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null); // selectQuery,selectedArguments

    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
      do {
        list.add(cursor.getString(0)); // adding 2nd column data
      } while (cursor.moveToNext());
    }
    // closing connection
    cursor.close();
    db.close();
    // returning lables
    return list;
  }
}
