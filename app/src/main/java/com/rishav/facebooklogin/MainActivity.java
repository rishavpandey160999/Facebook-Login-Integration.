package com.rishav.facebooklogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    private LoginButton loginButton;

    private TextView a, b;
    private CircleImageView c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        a = findViewById(R.id.info);
        b=findViewById(R.id.mail);
        c = findViewById(R.id.pic);
        loginButton.setPermissions(Arrays.asList("email","user_gender"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d("pandey", "Login Successful");

                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                Log.d("pandey", "Login Failed");

                Toast.makeText(MainActivity.this, "Login Cancelled", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onError(FacebookException error) {

                Log.d("pandey", "Login Error");

                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),

                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        Log.d("pandey", object.toString());


                        try {
                            String name = object.getString("name");
                            String email=object.getString("email");
                            String image=object.getJSONObject("picture").getJSONObject("data").getString("url");

                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            intent.putExtra("keyname",name);
                            intent.putExtra("keyemail",email);
                            intent.putExtra("keyimage",image);
                            startActivity(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }


        );

        Bundle bundle = new Bundle();
        bundle.putString("fields", "name,email,picture.width(200).height(200)");

        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

}