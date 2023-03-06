package com.example.totcoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity{
    private TextView responseTV;
    private ProgressBar loadingPB;
    private EditText user_name;
    private EditText password,passwordConfirm;
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

//        checking whether the user has ever logged into the system

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String totco_agent = sh.getString("name","");

        if(totco_agent.isEmpty()){
            Toast.makeText(this, "Login info required", Toast.LENGTH_SHORT).show();

        }else{
            Intent nxt = new Intent(getApplicationContext(),HomeActivity.class);
            nxt.putExtra("agent_name",totco_agent);
            startActivity(nxt);
        }

        user_name = findViewById(R.id.username);
        password = findViewById(R.id.password);

        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxt =  new Intent(getApplicationContext(),Register.class);
                startActivity(nxt);
            }
        });


        Button go = findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user_name.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                    Toast.makeText(Login.this, "Fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginuser(user_name.getText().toString(),password.getText().toString());
            }

            private void loginuser(String username, String password) {
                OkHttpClient client = new OkHttpClient();
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://totco.kakebe.com/api/api/users/loginUser.php").newBuilder();
                urlBuilder.addQueryParameter("user_name",username);
                urlBuilder.addQueryParameter("password",password);

                String url = urlBuilder.build().toString();
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        responseTV.setText("No internet");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if(response.isSuccessful()){
                            String resp = response.body().string();

                            Login.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    responseTV.setText(resp);

                                    try {
                                        JSONObject jsonObject = new JSONObject(resp);
                                        String success = jsonObject.getString("success");
                                        String msg = jsonObject.getString("message");

                                        String data = jsonObject.getString("data");

                                        JSONObject jsonObject1 = new JSONObject(data);
                                        String name = jsonObject1.getString("name");
//                                        JSONArray jsonArray = jsonObject.getJSONArray("data");


                                        if(success.equals("1")){
                                            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                            SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                            myEdit.putString("name", user_name.getText().toString());
                                            myEdit.apply();

                                            Intent nxt = new Intent(getApplicationContext(),HomeActivity.class);
//                                            nxt.putExtra("name",username);
                                            startActivity(nxt);
                                        }else{
                                            responseTV.setText(msg);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        System.out.println("my error "+e);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username",user_name.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String totco_agent = sh.getString("name","");

        if(totco_agent.isEmpty()){
            Toast.makeText(this, "Login info required:"+totco_agent, Toast.LENGTH_SHORT).show();

        }else{
            Intent nxt = new Intent(getApplicationContext(),HomeActivity.class);
            nxt.putExtra("agent_name",totco_agent);
            startActivity(nxt);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("name", user_name.getText().toString());
//        myEdit.putInt("age", Integer.parseInt(age.getText().toString()));
        myEdit.apply();
    }

}