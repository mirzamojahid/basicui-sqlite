package bd.com.tourx.basicui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    Button signup,signup_login;

    EditText name,gender,email,password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        HelperDB helperDB = new HelperDB(this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }



        signup_login = findViewById(R.id.signup_login);
        signup = findViewById(R.id.signup_btn);

        name = findViewById(R.id.signup_name);
        gender = findViewById(R.id.signup_gender);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);

        signup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentx = new Intent(Signup.this,Login.class);
                startActivity(intentx);
                finish();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String namee = name.getText().toString();
                    String genderr = gender.getText().toString();
                    String emaill = email.getText().toString();
                    String passwordd = password.getText().toString();


                    if( !namee.equals("") && !genderr.equals("") && !emaill.equals("")  && !passwordd.equals("")){

                        HashMap<String,String> data=helperDB.signup(namee,genderr,emaill,passwordd);
                        if(data !=null && data.get("error").equals("False")){
                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sharedPreferences.edit();

                            myEdit.putString("email", data.get("email"));
                            myEdit.putString("name", data.get("name"));
                            myEdit.apply();


                            Intent intent = new Intent(Signup.this,Home.class);
                            intent.putExtra("name",data.get("name"));
                            intent.putExtra("email",data.get("email"));
                            intent.putExtra("gender",data.get("gender"));
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),""+data.get("msg"),Toast.LENGTH_LONG).show();

                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"All input fields are required!",Toast.LENGTH_LONG).show();

                    }



                }catch (Exception err){
                    Toast.makeText(getApplicationContext(),"Error: "+err,Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}