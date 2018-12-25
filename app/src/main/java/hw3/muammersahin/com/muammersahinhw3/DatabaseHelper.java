package hw3.muammersahin.com.muammersahinhw3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME="currencyDB";
    private static int  DATABASE_VERSION = 2;

    SQLiteDatabase db;

    public DatabaseHelper(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


        db= getWritableDatabase();//Wirtable and Readable mode
        Log.d("DATABASE OPERATIONS","Connection Provided");
    }

    public void close(){
        if(db != null)
            db.close();//Wirtable and Readable mode
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //onCreate called if database doesn't exist
        try {
            sqLiteDatabase.execSQL(CurrencyDB.CREATE_TABLE_SQL);
        }catch (SQLException e){
            e.printStackTrace();
        }
        Log.d("DATABASE OPERATIONS","OnCreate, table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //onUpgrade called ven DATABASE_VERSION is changed
        //SQLiteDatabase object used to execute SQL statements
        try {
            sqLiteDatabase.execSQL(CurrencyDB.DROP_TABLE_SQL);

        }catch (SQLException e){
            e.printStackTrace();
        }
        onCreate(sqLiteDatabase);
        Log.d("DATABASE OPERATIONS","onUpgrade,  table dropped, old version "+oldVersion+" new version "+newVersion);
    }
    public Cursor getAllRecords(String tableName, String[] columns   ){

        Cursor cursor = db.query(tableName, columns, null, null, null, null, null );

        Log.d("DATABASE OPERATIONS", "GET THE RECORDS");
        return cursor;
    }


    public long insert( String tableName, ContentValues contentValues){
        //ContentValues  allows to define key value pairs.
        //The key represents the table column identifier and the value represents the content for the table record in this column.
        //ContentVales can be used for insert and update operations over table
        Log.d("DATABASE OPERATIONS", "INSERT DONE");

        return db.insert(tableName, null, contentValues);
    }

}
