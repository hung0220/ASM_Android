package com.example.asm_android;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_Produc extends AppCompatActivity {
SharedPreferences sharedPreferences;

    @Override
    protected void onStop() {
//        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        if (sharedPreferences.getString("password","") == ""){
            xoaAll();
        }
        super.onStop();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_produc);













//        Menu Navigation botton ====================================================================
        TextView tvName = findViewById(R.id.tvName);
        Button btnCheckOclickLogOuts = findViewById(R.id.LogOutCrear);


        sharedPreferences = getSharedPreferences("AccountSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        tvName.setText(sharedPreferences.getString("LastName",""));

        btnCheckOclickLogOuts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("AccountSharedPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Toast.makeText(MainActivity_Produc.this, "Delete Account", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder mybuilders = new AlertDialog.Builder(MainActivity_Produc.this);

                mybuilders.setMessage("Notification!!!");
                mybuilders.setTitle("Do you really want to escape?");
                mybuilders.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.remove("FirstName");
                        editor.remove("LastName");
                        editor.remove("UserName");
                        editor.remove("Gmail");
                        editor.remove("password");
                        editor.commit();
                        xoaAll();
                        Intent intent = new Intent(MainActivity_Produc.this,MainActivity.class);
                        startActivity(intent);

                    }
                });
                mybuilders.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mybuilders.show();
            }
        });

//        onclick go form Sign Up
        sharedPreferences = getSharedPreferences("AccountSharedPreferences", MODE_PRIVATE);


//        kiểm tra có tài  mật  khẩu khoản Lưu  không nếu không thì xóa toàn bộ dữ liệu khi thoát ra
//Truy xuất dữ liệu SharedPreferences =============================================================
    }
    public void  xoaAll(){
        sharedPreferences = getSharedPreferences("AccountSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
    }

}