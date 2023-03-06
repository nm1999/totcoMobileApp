package com.example.totcoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        Button login = findViewById(R.id.register);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        EditText conf_password = findViewById(R.id.confirmpassword);
        TextView resp = findViewById(R.id.response);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()&& password.getText().toString().isEmpty()&&conf_password.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                    OkHttpClient client = new OkHttpClient();
                    HttpUrl.Builder httpurl = HttpUrl.parse("https://totco.kakebe.com/api/api/users/createUser.php").newBuilder();
                    httpurl.addQueryParameter("user_name",username.getText().toString());
                    httpurl.addQueryParameter("password",password.getText().toString());
                    httpurl.addQueryParameter("passwordConfirm",conf_password.getText().toString());
                    String url = httpurl.build().toString();

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            resp.setText("No internet");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String respdata = response.body().string();
//                            resp.setText(respdata);


                            try {
                                JSONObject jsonObject = new JSONObject(respdata);
                                String success = jsonObject.getString("success");
                                String msg = jsonObject.getString("message");
//                                resp.setText(msg);

                                if(success.equals("1")){
                                    Intent nxt = new Intent(getApplicationContext(),Login.class);
                                    startActivity(nxt);
                                }else{
                                   resp.setText(msg);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
            }
        });
    }
}