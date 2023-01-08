package com.example.asm_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Database mydatabase;
    SharedPreferences sharedPreferences;
    MainActivity mainActivity_signUp;
    int check = 0;
    EditText edtUsernames,edtPasswords;
    CheckBox ckSavePassWord;
    @Override
    protected void onStart() {
        Log.d("vvv","onStart");
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Log.d("vvv","onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("vvv","onStop");
        finish();
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.d("vvv","onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d("vvv","onRestart");
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        GetDataAccount();

        mydatabase = new Database(this);
        mydatabase = new Database(this, "myAccount", null, 1);
//      con trỏ chỉ vị trí database lấy dữ liệu


        sharedPreferences = getSharedPreferences("dataLogin",MODE_PRIVATE);
// ánh xạ
        TextView tvOnClickGoFromSignUp = findViewById(R.id.tvOnGoFromSignUp);
         edtUsernames = findViewById(R.id.edtUsername);
         edtPasswords = findViewById(R.id.edtPassword);
         ckSavePassWord = findViewById(R.id.ckbRemember);


         // set lại tk mk
        edtUsernames.setText(sharedPreferences.getString("taiKhoan",""));
        edtPasswords.setText(sharedPreferences.getString("matKhau",""));
        ckSavePassWord.setChecked(sharedPreferences.getBoolean("ckbRemember",false));
//        ánh xạ lấy giá trị trên form đăng kí
        TextView onClickGoCheckAcount = findViewById(R.id.btnOnSignInNow);
        onClickGoCheckAcount.setOnClickListener(new View.OnClickListener() {
            //            ánh xạ onclick
            @Override
            public void onClick(View v) {
                String UsernameCheck = edtUsernames.getText().toString();
                String passwordCheck = edtPasswords.getText().toString();
// lấy giá trị trong from edittetx


                if (UsernameCheck.equals("") || passwordCheck.equals("")) {
                    Toast.makeText(MainActivity.this, "Tài khoản không được để trống", Toast.LENGTH_SHORT).show();
                } else {

                    int checkaccount = mydatabase.checkTKMK(UsernameCheck, passwordCheck);
//                        gọi hàm check tài khoản bên database
                    if (checkaccount == 1) {
//                            kiểm tra điều kiện trả về
                        check = check + 1;
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Cursor dataAccount = mydatabase.GetData("select * from myAccount where username = '" + UsernameCheck + "' and  password = '" + passwordCheck + "'");

                        Toast.makeText(MainActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
//                      chạy tới màn hình chính nếu điều kiện là đúng
                        if(ckSavePassWord.isChecked()){
                            SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.putString("taiKhoan",UsernameCheck);
                            editor.putString("matKhau",passwordCheck);
                            editor.putBoolean("ckbRemember",true);
                            editor.commit();
                        }else {
                            SharedPreferences.Editor editor= sharedPreferences.edit();
                            editor.remove("taiKhoan");
                            editor.remove("matKhau");
                            editor.remove("ckbRemember");
                            editor.commit();

                        }


                    }

                }
                if (check == 0) {
                    Toast.makeText(MainActivity.this, "Sai Tài Khoản Hoặc Mật Khẩu", Toast.LENGTH_SHORT).show();
                }
            }


        });



//        ánh xạ btn login và signUP
        tvOnClickGoFromSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });
//        go from Sign Up



    }



}