package com.example.microblog.activity.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.microblog.HomeActivity;
import com.example.microblog.R;
import com.example.microblog.activity.register.RegisterActivity;
import com.example.microblog.activity.scan.ScanActivity;
import com.example.microblog.http.HttpClient;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.microblog.Permission.REQUEST_CODE_PRESSION_PHONE_STATE;

/*
首页
 */
public class LoginActivity extends AppCompatActivity {

     private EditText userNameEt,passwordEt;
     private Button loginSubmitBtn ;
     private TextView registerTv, findPswTv;

     private String userName,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.init();
    }

    private  void  init(){

        userNameEt = (EditText)findViewById(R.id.et_user_name);
        passwordEt = (EditText)findViewById(R.id.et_psw);
        loginSubmitBtn = (Button)findViewById(R.id.btn_login);
        registerTv = (TextView)findViewById(R.id.tv_register);
        findPswTv = (TextView)findViewById(R.id.tv_find_psw);

        //绑定事件

        //找回密码
        findPswTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LoginActivity.this,"功能未实现",Toast.LENGTH_SHORT).show();
            }
        });

        //跳转注册
        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //登陆提交
        loginSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEt.getText().toString().trim();
                password = passwordEt.getText().toString().trim();

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    //登陆请求

                    RequestBody requestBody = new FormBody.Builder()
                            .add("userName",userName)
                            .add("password",password)
                            .build();

                    HttpClient.post("", requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT);

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {

                                JSONObject jsonObject =  new JSONObject().getJSONObject(response.body().string());
                                Long userId = (Long)jsonObject.get("userId");
                                String token = (String)jsonObject.get("token");

                                //保存信息
                                saveUserInfo(userId,token);

                                Intent intent =new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("isLogin",true);

                                setResult(RESULT_OK,intent);
                                LoginActivity.this.finish();


                            }
                            catch (Exception ex){

                            }
                        }
                    });

                    //从服务器获取到token

                    //保存token

                    //返回首页

                }


            }
        });


    }

    private void saveUserInfo(Long userId,String token){
        SharedPreferences sp = getSharedPreferences("USER_INFO",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("USER_ID",userId);
        editor.putString("TOKEN",token);
        editor.commit();

    }





}
