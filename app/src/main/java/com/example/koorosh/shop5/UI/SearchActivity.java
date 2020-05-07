package com.example.koorosh.shop5.UI;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.koorosh.shop5.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button btn = (Button) findViewById(R.id.btnBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.super.onBackPressed();
            }
        });

        Typeface typeface= Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/BKOODB.TTF");

        EditText txt = (EditText) findViewById(R.id.txtSearch);
        txt.setTypeface(typeface);

    }
}
