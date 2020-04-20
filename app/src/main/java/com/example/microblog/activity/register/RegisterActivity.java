package com.example.microblog.activity.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.microblog.R;
import com.example.microblog.activity.login.LoginActivity;
import com.example.microblog.http.HttpClient;
import com.google.zxing.common.StringUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/*
首页
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText userNameEt,passwordEt,passwordAgainEt;
    private Button registerSubmitBtn ;
    private TextView registerTv, findPswTv;

    private String userName,password,passwordAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.init();
    }

    private void init(){

        userNameEt = (EditText) findViewById(R.id.et_psw);
        passwordEt = (EditText) findViewById(R.id.et_user_name);
        passwordAgainEt = (EditText) findViewById(R.id.et_psw_again);
        registerSubmitBtn= (Button) findViewById(R.id.btn_register);

        registerSubmitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userName = userNameEt.getText().toString().trim();
                password = passwordEt.getText().toString().trim();
                passwordAgain = passwordAgainEt.getText().toString().trim();

                if((TextUtils.isEmpty(userName)) || (TextUtils.isEmpty(userName))  || (TextUtils.isEmpty(userName))  ){
                    Toast.makeText(RegisterActivity.this , "输入不能为空",Toast.LENGTH_SHORT).show();
                }
                else if( !password.equals(passwordAgain)){
                    Toast.makeText(RegisterActivity.this , "两次输入密码不一致",Toast.LENGTH_SHORT).show();
                }
                else{

                    //网络请求

                    RequestBody  requestBody = new FormBody.Builder()
                            .add("userName",userName)
                            .add("password",password)
                            .build();

                    HttpClient.post("", requestBody, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e(RegisterActivity.class.getName(),"注册失败:"+e.getMessage());
                            Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseJsonData = response.body().string();
                            try {
//                                JSONObject jsonObject =  new JSONObject().getJSONObject(responseJsonData);
//                                Long userId = (Long)jsonObject.get("userId");
//                                String token = (String)jsonObject.get("token");

                                //后端只是返回注册成功状态
                                if(true){
                                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.putExtra("userName",userName);
                                    setResult(RESULT_OK,intent);
                                    RegisterActivity.this.finish();
                                }
                                //注册失败，其他原因
                                else {
                                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception ex){

                            }

                        }
                    });
                }
            }
        });
    }





}
