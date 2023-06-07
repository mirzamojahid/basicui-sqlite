package bd.com.tourx.basicui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditTour extends AppCompatActivity {


    EditText title,fee,content;
    TextView edit_id;
    Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tour);
        HelperDB helperDB = new HelperDB(this);



        title = findViewById(R.id.edit_title);
        edit_id = findViewById(R.id.edit_id);
        fee = findViewById(R.id.edit_fee);
        content = findViewById(R.id.edit_content);
        button = findViewById(R.id.editbtn_submit);
        Intent intent =getIntent();


try{
    edit_id.setText(intent.getStringExtra("idx"));
    title.setText(intent.getStringExtra("titlex"));
    fee.setText(intent.getStringExtra("feex"));
    content.setText(intent.getStringExtra("contentx"));
}catch (Exception err){
    Log.d("mirzaapp","Error: "+err);

}


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{


                    Toast.makeText(getApplicationContext(),""+fee.getText().toString(),Toast.LENGTH_SHORT).show();


helperDB.editEvent(Integer.parseInt(edit_id.getText().toString()),""+title.getText().toString(),Integer.parseInt(fee.getText().toString()),content.getText().toString());

                            Intent vv = new Intent(EditTour.this,Home.class);
                            startActivity(vv);
                finish();


                }catch (Exception err){
                    Toast.makeText(getApplicationContext(),"Error: "+err,Toast.LENGTH_LONG).show();
                }
                //
            }
        });



    }
}