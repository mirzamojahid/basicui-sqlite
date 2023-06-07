package bd.com.tourx.basicui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DELAY = 2600;
/*
Author: Mirza Mojahid
Github: https://githhub.com/mirzamojahid
Linkedin: https://linkedin.com/in/mirzamojahid
Email: mirzamojahid41@gmail.com
Mobile/Telegram/Whatsapp: +8801872945299
website: https://tourx.com.bd
Overview: This project mainly focuses on Sqlite CRUD operations.
          Where can go to home screen through login or register account.
          You can see the event list on the home screen and you can see
          the details of the event on the details page.
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        if(! sh.getString("event", "").equals("created")){
            HelperDB helperDB = new HelperDB(this);
            SharedPreferences.Editor myEdit = sh.edit();
            helperDB.createEvent();
            myEdit.putString("event", "created");
            myEdit.apply();
        }

       // SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String email = sh.getString("email", "");
        String name = sh.getString("name", "");

        new Handler().postDelayed(()->{
            if(!email.equals("") && !name.equals("")){
                Intent intent =new Intent(SplashScreen.this, Home.class);
                intent.putExtra("name",name);
                startActivity(intent);

                finish();
            }else{

                startActivity(new Intent(SplashScreen.this, Login.class));
                finish();
            }

        },SPLASH_DELAY);
    }
}