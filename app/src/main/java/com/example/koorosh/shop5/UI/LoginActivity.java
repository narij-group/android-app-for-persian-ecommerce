package com.example.koorosh.shop5.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koorosh.shop5.API.APIClient;
import com.example.koorosh.shop5.API.APIInterface;
import com.example.koorosh.shop5.Classes.Result;
import com.example.koorosh.shop5.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnR = (Button) findViewById(R.id.btn_register);
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        TextView myTextView=(TextView)findViewById(R.id.textBox);
        Typeface typeFace=Typeface.createFromAsset(getAssets(), "fonts/BNAZANIN.TTF");
        myTextView.setTypeface(typeFace);

        Button btn1 = (Button) findViewById(R.id.btnlogin);

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Button btn = (Button) findViewById(R.id.btnlogin);
                btn.setText("در حال بارگذاری...");


                EditText editTextEmail = (EditText) findViewById(R.id.username);
                EditText editTextPassword = (EditText) findViewById(R.id.password);

                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                Call<Result> call = apiInterface.userLogin(email, password);

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (!response.body().getError()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("UserId",response.body().getMessage());
                            startActivity(intent);
                            LoginActivity.this.finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "نام کاربری یا رمزعبور اشتباه است!", Toast.LENGTH_LONG).show();
                            Button btn = (Button) findViewById(R.id.btnlogin);
                            btn.setText("ورود");
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        Button btn = (Button) findViewById(R.id.btnlogin);
                        btn.setText("ورود");
                    }
                });

            }
        });



    }
}
