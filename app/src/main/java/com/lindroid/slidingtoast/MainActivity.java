package com.lindroid.slidingtoast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void slidingToastClick(View view) {
        startActivity(new Intent(this, SlidingToastActivity.class));
    }

    public void croutonClick(View view) {
        startActivity(new Intent(this, CroutonActivity.class));
    }


}
