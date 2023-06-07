package bd.com.tourx.basicui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
   protected CardView flights,buses,stays,packages,guide;

   TextView helloname;
    ListView listView;

    Button logout;
    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ImageView menuIcon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        HelperDB helperDB = new HelperDB(this);

   menuIcon = findViewById(R.id.menu_icon);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        listView = findViewById(R.id.listviewitem);

        logout = findViewById(R.id.logout);

        Intent intent = getIntent();
        String name =intent.getStringExtra("name");

        helloname = findViewById(R.id.helloname);

        helloname.setText("Hello "+name.toString()+"!");
        helloname.setVisibility(View.VISIBLE);
        navigationDrawer();


        ArrayList<DBModel> data= helperDB.fetchAllData();

        System.out.print("Size: "+data.size());
        Log.d("helperDB","Size: "+data.size());

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),data);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Intent intentx = new Intent(Home.this,EventDetail.class);
               intentx.putExtra("content",  data.get(position).content);
                intentx.putExtra("fee", ""+data.get(position).fee);
                intentx.putExtra("title",  data.get(position).title);
                intentx.putExtra("id", ""+ data.get(position).id);
                intentx.putExtra("img", ""+ data.get(position).img);

                startActivity(intentx);


            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                myEdit.putString("email", "");
                myEdit.putString("name","");
                myEdit.apply();

             startActivity(new Intent(getApplicationContext(),Login.class
             ));

             finish();
            }
        });

    }
    //Navigation Drawer Functions
    private void navigationDrawer() {
        //Navigation Hocks
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }


        });


    }





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_home:

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case  R.id.nav_addtour:
                Intent intent = new Intent(Home.this,AddTour.class);
                startActivity(intent);
                break;

            case R.id.nav_exit:
                finish();
                break;

            case R.id.nav_share:
                Intent shareintent = new Intent();
                shareintent.setAction(Intent.ACTION_SEND);
                shareintent.putExtra(Intent.EXTRA_SUBJECT, "Install the latest TourX app.Which makes it easy to find travel guides & many more services.\nসর্বশেষতম ট্যুরএক্স অ্যাপ ইনস্টল করুন। যা ভ্রমণ গাইড ও অারও অনেক পরিষেবা সন্ধান করা সহজ করে তোলে।\n");
                shareintent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=bd.com.tourx");
                shareintent.setType("text/plain");
                startActivity(Intent.createChooser(shareintent, "share via"));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_rate:
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=bd.com.tourx");
                Intent intentx = new Intent(Intent.ACTION_VIEW,uri);
                try {
                    startActivity(intentx);
                }catch (Exception e){
                    Toast.makeText(this,"Unable to open\n"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                break;

        }




        return true;
    }
}