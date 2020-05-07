package com.example.koorosh.shop5.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.koorosh.shop5.API.APIClient;
import com.example.koorosh.shop5.API.APIInterface;
import com.example.koorosh.shop5.Classes.Result;
import com.example.koorosh.shop5.Globals;
import com.example.koorosh.shop5.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton btn = (ImageButton) findViewById(R.id.btnAdd);

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        final TextView txt = (TextView) findViewById(R.id.txtStatus);
        final int UserId = Integer.parseInt(getIntent().getStringExtra("UserId"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setEnabled(false);
                txt.setText("لطفا کمی صبر کنید...");

                Globals.sbf.append("######## START #########\n");

                Call<Result> call = apiInterface.insertBlankProduct2(System.currentTimeMillis() + "");
//                Call<Result> call = apiInterface.insertBlankProduct2();

                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if (!response.body().getError()) {
                            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                            Globals.sbf.append(" GET NEW ProductId ####\n UserId : " + UserId + " ProductId : " + Integer.parseInt(response.body().getMessage()) + "\n#### \n");
                            intent.putExtra("UserId", UserId);
                            intent.putExtra("ProductId", Integer.parseInt(response.body().getMessage()));
                            Globals.productId = Integer.parseInt(response.body().getMessage());
                            Log.d(Globals.LOG_TAG, Integer.parseInt(response.body().getMessage()) + "");

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "مشکلی پیش آمد! لطفا دوباره امتحان کنید.", Toast.LENGTH_LONG).show();
                            txt.setText("خطا");
                            btn.setEnabled(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "مشکلی پیش آمد! لطفا دوباره امتحان کنید.", Toast.LENGTH_LONG).show();
                        txt.setText("خطا");
                        btn.setEnabled(true);
                    }
                });

            }
        });


        final TextView txtSavePath = (TextView) findViewById(R.id.txtSavePath);
        Button btnLog = (Button) findViewById(R.id.btnlog);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File extDir = getExternalFilesDir(null);
                String path = extDir.getAbsolutePath();
                txtSavePath.setText(path);

                String FILENAME = "logs.txt";

                File file = new File(extDir, FILENAME);

                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(Globals.sbf.toString().getBytes());
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
