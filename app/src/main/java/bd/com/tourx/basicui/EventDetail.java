package bd.com.tourx.basicui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetail extends AppCompatActivity {
    TextView titlee,contente,feee,eid;
    ImageView eimg;
    AppCompatButton deletebtn,editbtn;

    ImageView ebackbtn;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Intent intent = getIntent();
        Integer[] img=new Integer[] {R.drawable.sajek, R.drawable.shundorbon,R.drawable.jaflong,R.drawable.hakaloki,R.drawable.saintmartin}; //inline initialization


        ebackbtn = findViewById(R.id.ebackbtn);

        editbtn = findViewById(R.id.editbtnx);
        deletebtn = findViewById(R.id.deletebtn);
        eid = findViewById(R.id.eid);



        ebackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        String title =intent.getStringExtra("title");
        String content =intent.getStringExtra("content");
        String fee =intent.getStringExtra("fee");
        String imgx =intent.getStringExtra("img");
        int id =Integer.parseInt(intent.getStringExtra("id"));

        //ebackbtn.se
        titlee = findViewById(R.id.etitle);
        feee = findViewById(R.id.efee);
        eimg = findViewById(R.id.eimg);

        contente = findViewById(R.id.econtent);
        titlee.setText(title);
        contente.setText(content);
        eimg.setImageResource(Integer.parseInt(imgx));
        feee.setText("BDT "+fee+" / Per Person");
        eid.setText(""+id);




        HelperDB helperDB = new HelperDB(this);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentx = new Intent(EventDetail.this,EditTour.class);
                intentx.putExtra("idx",""+id);
                intentx.putExtra("feex",fee);
                intentx.putExtra("titlex",titlee.getText().toString());
                intentx.putExtra("contentx",contente.getText().toString());
                startActivity(intentx);
                finish();
            }
        });


        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    boolean d= helperDB.deleteEvent(id);
                    if(d == true){
                        Intent intenty = new Intent(EventDetail.this,Home.class);
                        startActivity(intenty);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Something wrong!",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception err){
                    Toast.makeText(getApplicationContext(),"Error: "+err,Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}