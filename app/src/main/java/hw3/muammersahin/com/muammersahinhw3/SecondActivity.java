package hw3.muammersahin.com.muammersahinhw3;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

public class SecondActivity extends AppCompatActivity {


    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv = findViewById(R.id.resultTxt);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        DatabaseHelper db = new DatabaseHelper(this);
        Intent intent = getIntent();

        Random generator = new Random();
        int id = generator.nextInt(100) + 1;

        String from = intent.getExtras().getString("from");
        String result = intent.getExtras().getDouble("result") + "";
        String to = intent.getExtras().getString("to");


        CurrencyDB.insertShoppingItem(db, id, from, to, result);
        List<CurrencyItems> items = CurrencyDB.getAllFlags(db);
        String out="";
        int k = 0;
        for(int i = items.size()-1; i>=0; i--){
          if(k!=10){
              out+=i+". Search: " +items.get(i).getResult() + "\n";
          }else {
              i = 0;
          }

            k++;
        }

        Toast.makeText(this, "Result = " + out,  Toast.LENGTH_LONG).show();

        tv.setText(out);


    }
}
