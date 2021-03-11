package com.example.elevator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class activity_join extends AppCompatActivity {

    private EditText id, password, phoneNum, name;
    private Button join_button, cancel_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        id = findViewById(R.id.id);
        password = findViewById(R.id.password);
        phoneNum = findViewById(R.id.phoneNum);
        name = findViewById(R.id.name);

        join_button = findViewById(R.id.join_button);
        cancel_button = findViewById(R.id.cancel_button);

        cancel_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(activity_join.this, Login.class);
                startActivity(intent);
            }
        });

        join_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userID = id.getText().toString();
                String userPass = password.getText().toString();
                String userPhone = phoneNum.getText().toString();
                String userName = name.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(activity_join.this, Login.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                JoinRequest joinRequest = new JoinRequest(userID, userPass, userPhone, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue(activity_join.this);
                queue.add(joinRequest);
            }
        });
    }
}
