package bd.com.tourx.basicui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTour extends AppCompatActivity {

    EditText title,fee,content;
    Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        HelperDB helperDB = new HelperDB(this);

        title = findViewById(R.id.add_title);
        fee = findViewById(R.id.add_fee);
        content = findViewById(R.id.add_content);
        button = findViewById(R.id.addbtn_submit);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                 String tit= title.getText().toString();
                  int fe= Integer.parseInt(fee.getText().toString());
                  String con= content.getText().toString();
                  boolean datax= helperDB.insertEvent(tit,fe,con);
                 if( datax == true ){
                     Intent intentx = new Intent(AddTour.this,Home.class);
                     startActivity(intentx);
                     finish();
                 }else{
                     Toast.makeText(AddTour.this," wrong",Toast.LENGTH_LONG).show();

                 }
//
              }catch (Exception err){
                  Toast.makeText(getApplicationContext(),"Error: "+err,Toast.LENGTH_LONG).show();
              }
              //
            }
        });


    }
}