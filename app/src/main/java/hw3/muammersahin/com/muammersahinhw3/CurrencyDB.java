package hw3.muammersahin.com.muammersahinhw3;

import android.content.ContentValues;
import android.database.Cursor;


import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDB {

    public static final String TABLE_NAME="currency";
    public static final String FIELD_ID = "id";
    public static final String FIELD_FROM = "fromCurrency";
    public static final String FIELD_TO = "toCurrency";
    public static final String FIELD_RESULT = "resultCurrency";


    public static final String CREATE_TABLE_SQL = "CREATE TABLE "+
            TABLE_NAME+" ("+FIELD_ID+" number, "+FIELD_TO+" text, "+FIELD_RESULT+" text, "+FIELD_FROM+" text);";
    public static final String DROP_TABLE_SQL = "DROP TABLE if exists "+TABLE_NAME;


    public static List<CurrencyItems> getAllFlags(DatabaseHelper db) {


        Cursor cursor = db.getAllRecords(TABLE_NAME, null);
        List<CurrencyItems> data = new ArrayList<>();
        CurrencyItems anItem = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String to = cursor.getString(1);
            String result = cursor.getString(2);
            String from = cursor.getString(3);
            anItem = new CurrencyItems(id, from, to, result);
            data.add(anItem);
        }
        return data;

    }


        public static long insertShoppingItem(DatabaseHelper db, int id, String from, String to, String result){
            ContentValues contentValues = new ContentValues( );
            contentValues.put(FIELD_ID, id);
            contentValues.put(FIELD_FROM, from);
            contentValues.put(FIELD_TO, to);
            contentValues.put(FIELD_RESULT, result);

            long res = db.insert(TABLE_NAME ,contentValues);

            return res;
        }


}
