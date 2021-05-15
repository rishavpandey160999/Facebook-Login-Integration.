package com.rishav.facebooklogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

import de.hdodenhof.circleimageview.CircleImageView;

public class SecondActivity extends AppCompatActivity {

    private TextView a, b;
    private CircleImageView c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        a = findViewById(R.id.info);
        b = findViewById(R.id.mail);
        c = findViewById(R.id.pic);

        String name = getIntent().getStringExtra("keyname");
        String email = getIntent().getStringExtra("keyemail");
        String image = getIntent().getStringExtra("keyimage");

        a.setText(name);
        b.setText(email);
        Glide.with(this).load(image).into(c);


    }


    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if (currentAccessToken == null) {

                startActivity(new Intent(SecondActivity.this, MainActivity.class));
                Toast.makeText(SecondActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                finish();
            }

        }


    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

}