package upr.uas.vivi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import upr.uas.vivi.db.params.UserParams;
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

  private static final String SQL_DELETE_USER = "DROP TABLE IF EXISTS " + UserParams.TABLE_NAME;

  public DBHandler(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_USER);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(SQL_DELETE_USER);
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
}
