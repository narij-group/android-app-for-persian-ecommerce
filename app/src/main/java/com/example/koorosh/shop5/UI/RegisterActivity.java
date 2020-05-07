package com.example.koorosh.shop5.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koorosh.shop5.API.APIClient;
import com.example.koorosh.shop5.API.APIInterface;
import com.example.koorosh.shop5.Classes.Result;
import com.example.koorosh.shop5.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Button btnL = (Button) findViewById(R.id.btn_login);
        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });


        Button btnAdd = (Button) findViewById(R.id.btnregister);

        btnAdd.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                EditText txtName = (EditText) findViewById(R.id.name);
                EditText txtFamily = (EditText) findViewById(R.id.family);
                EditText txtEmail = (EditText) findViewById(R.id.email);
                EditText txtUsername = (EditText) findViewById(R.id.username);
                EditText txtPassword = (EditText) findViewById(R.id.password);
                EditText txtRPassword = (EditText) findViewById(R.id.repeatpassword);
                EditText txtNationalityCode = (EditText) findViewById(R.id.nationality_code);
                EditText txtMobile = (EditText) findViewById(R.id.mobile);
                EditText txtPhone = (EditText) findViewById(R.id.phone);


                String name = txtName.getText().toString().trim();
                String family = txtFamily.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String repeatpassword = txtRPassword.getText().toString().trim();

                long nationality_code;
                if (txtNationalityCode.getText().toString().equals("")) {
                    nationality_code = 0;
                } else {
                    nationality_code = Long.parseLong(txtNationalityCode.getText().toString());
                }

                String mobile = txtMobile.getText().toString().trim();
                String phone = txtPhone.getText().toString().trim();

                if (name.equals("") || family.equals("") || email.equals("") || username.equals("") || password.equals("") || repeatpassword.equals("") || nationality_code == 0 || mobile.equals("") || phone.equals("")) {
                    Toast.makeText(RegisterActivity.this, "لطفا تمام فیلد ها را پر کنید!", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(repeatpassword)) {
                    Toast.makeText(RegisterActivity.this, "رمز عبور و تکرار رمز عبور با هم برابر نیستند!", Toast.LENGTH_SHORT).show();
                } else {
                    final ProgressDialog progress2 = new ProgressDialog(RegisterActivity.this);
                    progress2.setTitle("در حال ثبت نام");
                    progress2.setMessage("لطفا کمی صبر کنید...");
                    progress2.setCancelable(false);
                    progress2.show();

                    Call<Result> call = apiInterface.register(name, family, email, username, password, nationality_code, mobile, phone);

                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (!response.body().getError()) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.putExtra("UserId", String.valueOf(UserId));

                                Toast.makeText(RegisterActivity.this, "شما با موفقیت ثبت نام شدید", Toast.LENGTH_LONG).show();
//                                            Toast.makeText(RegisterActivity.this , response.body().getMessage() , Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                RegisterActivity.this.finish();
                                progress2.dismiss();
                            } else {
                                Toast.makeText(RegisterActivity.this , response.body().getMessage() , Toast.LENGTH_LONG).show();
                                progress2.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                            progress2.dismiss();
                        }
                    });

                }
            }
        });
    }
}
