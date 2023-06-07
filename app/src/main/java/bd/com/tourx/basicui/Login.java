package bd.com.tourx.basicui;

import static bd.com.tourx.basicui.R.id.login_signup;
import static bd.com.tourx.basicui.R.id.signup_name;

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

public class Login extends AppCompatActivity {

    Button login,login_signup;
    EditText email,password;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        HelperDB helperDB = new HelperDB(this);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        login = findViewById(R.id.login_btn);
        login_signup = findViewById(R.id.login_signup);


        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);

        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentx = new Intent(Login.this,Signup.class);
                startActivity(intentx);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{

                    String emaill = email.getText().toString();
                    String passwordd = password.getText().toString();

                    if(!emaill.equals("")  && !passwordd.equals("")){

                        try{

                            HashMap<String,String> data=helperDB.login(emaill,passwordd);

                            if(data !=null && data.get("error").equals("False")){

                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                myEdit.putString("email", data.get("email"));
                                myEdit.putString("name", data.get("name"));
                                myEdit.apply();

                                Intent intent = new Intent(Login.this,Home.class);
                                intent.putExtra("name",data.get("name"));
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),""+data.get("msg"),Toast.LENGTH_LONG).show();

                            }

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Something Wrong!",Toast.LENGTH_LONG).show();

                        }



                    }else{
                        Toast.makeText(getApplicationContext(),"Make sure email and password fillup",Toast.LENGTH_LONG).show();

                    }




                }catch (Exception err){
                    Toast.makeText(getApplicationContext(),"Error: "+err,Toast.LENGTH_LONG).show();
                }






            }
        });


    }
}