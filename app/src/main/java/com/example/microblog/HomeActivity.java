package com.example.microblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.microblog.activity.scan.ScanActivity;
import com.example.microblog.ui.login.LoginActivity;

import static com.example.microblog.Permission.REQUEST_CODE_PRESSION_PHONE_STATE;

/*
首页
 */
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(HomeActivity.class.getName(),"onCreateOptionsMenu ");
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i(HomeActivity.class.getName(),"ITEM ID = " + item.getItemId());
        Intent intent;
        switch (item.getItemId()){
            case R.id.to_login_btn:
                intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.scan_btn:
                intent = new Intent(HomeActivity.this, ScanActivity.class);
                startActivity(intent);

                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //   super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i(this.getClass().getName(),"申请权限回调:"+requestCode);
        switch (requestCode) {
            case REQUEST_CODE_PRESSION_PHONE_STATE:
                Log.i(this.getClass().getName(),"申请权限回调:"+REQUEST_CODE_PRESSION_PHONE_STATE);
                break;
            default:
                break;

        }
    }

}
