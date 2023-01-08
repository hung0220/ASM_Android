package com.example.asm_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    Database database;
    ArrayList<Account> listAccount = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
TextView btnSign = findViewById(R.id.singin);
btnSign.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
});



//        btn chuyển trang
        database = new Database(this, "myAccount", null, 1);
//        tạo bảng
        database.QueryData("CREATE TABLE IF NOT EXISTS myAccount(iD INTEGER PRIMARY KEY AUTOINCREMENT," +
                "firstName VARCHAR(20) NOT NULL," +
                "lastName VARCHAR(20) NOT NULL," +
                "username VARCHAR(100) NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "gmail VARCHAR(100) NOT NULL)");

//        Tạo các thành phần trong bảng


        Cursor dataAccount = database.GetData("SELECT * FROM myAccount");
//        moveTonext
        listAccount.clear();
        while (dataAccount.moveToNext()) {
            int iDAccount = dataAccount.getInt(0);
            String firstName = dataAccount.getString(1);
            String lastName = dataAccount.getString(2);
            String userName = dataAccount.getString(3);
            String passWord = dataAccount.getString(4);
            String gmail = dataAccount.getString(5);
            listAccount.add(new Account(iDAccount, firstName, lastName, userName, passWord, gmail));
            Log.d("vvv", userName);
        }

        themAccount();


    }

    //    Thêm tài khoản
    public void themAccount() {
        EditText newFirstName = findViewById(R.id.newFirstName);
        EditText newLastName = findViewById(R.id.newLastName);
        EditText newUserName = findViewById(R.id.newUsername);
        EditText newPassword = findViewById(R.id.newPassword);
        EditText newGmail = findViewById(R.id.newGmail);
//        ánh xạ EditText để lấy thông tin from điền
        TextView btnChekNewAccount = findViewById(R.id.btnsinup);
        btnChekNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = newFirstName.getText().toString();
                String lastName = newLastName.getText().toString();
                String userName = newUserName.getText().toString();
                String passWord = newPassword.getText().toString();
                String gmail = newGmail.getText().toString();
               CheckBox ckSavePassWord = findViewById(R.id.chkRemember1);
//                chuyển giá trị về String

                if (firstName.equals("")) {
                    Toast.makeText(Registration.this,  "Tên  không được để trống !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (firstName.length() < 2) {
                    Toast.makeText(Registration.this, "Tên không được ít hơn 2ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
//                firstName
                if (lastName.equals("")) {
                    Toast.makeText(Registration.this, "Họ không được để trống !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lastName.length() < 2) {
                    Toast.makeText(Registration.this, "Họ không được ít hơn 2ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
//                lastName
                if (userName.equals("")) {
                    Toast.makeText(Registration.this, "Tên Tài Khoản không được để trống !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userName.length() < 5) {
                    Toast.makeText(Registration.this, "Tên Phải lớn hơn 5 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }
//                userName
                if (passWord.equals("")) {
                    Toast.makeText(Registration.this, "Password không được để trống !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passWord.length() < 5) {
                    Toast.makeText(Registration.this, "Mật khẩu phải trên 5 kí tự", Toast.LENGTH_SHORT).show();
                    return;
                }
//                passWord
                if (gmail.equals("")) {
                    Toast.makeText(Registration.this, "Gmail không được để trống !!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ckSavePassWord.isChecked() == false){
                    Toast.makeText(Registration.this, "Chưa đồng ý Vs các Điều Khoản", Toast.LENGTH_SHORT).show();
                    return;
                }
//              Checkbox
                if (checkGmail(gmail) == false) {
                    Toast.makeText(Registration.this, "Sai Gmail, Nhập lại né!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Cursor chekUserName = database.GetData("select * from myAccount where username = '" + userName + "'");
                String user;

//1 Kiểm tra điều kiện đầu vào
                AlertDialog.Builder mybuider = new AlertDialog.Builder(Registration.this);
                mybuider.setTitle("Thông Báo");
                mybuider.setMessage("Bạn muốn tạo tài khoản?");
                mybuider.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.QueryData("INSERT INTO myAccount VALUES(null,'" + firstName + "','" + lastName + "','" + userName + "','" + passWord + "','" + gmail + "')");
                        GetDataAccount();
                        Toast.makeText(Registration.this, "Tạo TÀi Khoản Thành Công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Registration.this, MainActivity.class);
                        startActivity(intent);

                    }
                });
                mybuider.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mybuider.show();
//2 add thông tin vào database
//3gọi lại thông tin

            }
        });
//ánh xạ edittext
    }


    public void GetDataAccount() {
//        SELECT DATA
        Cursor dataAccount = database.GetData("SELECT * FROM myAccount");
//        moveTonext
        listAccount.clear();
        while (dataAccount.moveToNext()) {
            int iDAccount = dataAccount.getInt(0);
            String firstName = dataAccount.getString(1);
            String lastName = dataAccount.getString(2);
            String userName = dataAccount.getString(3);
            String passWord = dataAccount.getString(4);
            String gmail = dataAccount.getString(5);
            listAccount.add(new Account(iDAccount, firstName, lastName, userName, passWord, gmail));
//           Thêm dữ liệu vào arrLisst
        }

    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean checkGmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
//    check gmail

}