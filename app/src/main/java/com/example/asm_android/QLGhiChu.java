package com.example.asm_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QLGhiChu extends AppCompatActivity {

    private ArrayList<GhiChu> ghiChuArrayList = new ArrayList<>();
    public GhiChuAdapter gcAdapter;
    ImageView btnMore;
    ListView lv;
    Database database;
    LinearLayout btnthem;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dinhangNgayThangNam = new SimpleDateFormat("dd/MM/yyyy");
    String homnay = dinhangNgayThangNam.format(calendar.getTime());
    SharedPreferences sharedPreferences;


    String vitri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlghi_chu);

        btnthem = findViewById(R.id.btnThemghichu);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialonglogin();
            }
        });

        lv = findViewById(R.id.lvghichu);
        ghiChuArrayList = new ArrayList<>();



        gcAdapter = new GhiChuAdapter(QLGhiChu.this, R.layout.layout_ghichu,ghiChuArrayList);

        lv.setAdapter(gcAdapter);


        //Tạo Bảng trong database
        database = new Database(this, "GhiChu", null, 1);


        database.QueryData(
                "CREATE TABLE IF NOT EXISTS GhiChu(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "NgayTao VARCHAR(20) NOT NULL," +
                        "TieuDe VARCHAR(200) NOT NULL," +
                        "NoiDung VARCHAR(200) NOT NULL)"
        );

     //   database.QueryData("INSERT INTO GhiChu VALUES(null,'20/2/2021','Xn Chào','HELLOOOOO')");

        getdatagc();

        sharedPreferences = getSharedPreferences("ghichu",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String layId1 = ghiChuArrayList.get(position).getId();

                Cursor checkId = database.GetData("select * from GhiChu where Id ='"+layId1+"'");

                ghiChuArrayList.clear();

                while (checkId.moveToNext()){
                    String Id = checkId.getString(0);
                    String ngayTao = checkId.getString(1);
                    String tieuDe = checkId.getString(2);
                    String noiDung = checkId.getString(3);

                    ghiChuArrayList.add(new GhiChu(Id,ngayTao,tieuDe,noiDung));


                }

                editor.putString("ngaytao",ghiChuArrayList.get(0).getNgayTao());
                editor.putString("tieuDe",ghiChuArrayList.get(0).getTieuDe());
                editor.putString("noiDung",ghiChuArrayList.get(0).getNoiDung());
                editor.apply();
                EditText editTieuDeThem = (EditText) findViewById(R.id.editTieuDeThem);
                EditText editNoiDungThem = (EditText) findViewById(R.id.editNoiDungThem);

                DialongThem(layId1);




            }
        });

        EditText editTim = findViewById(R.id.editTimtghichu);
        editTim.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                String nodeTimHangHoa = editTim.getText().toString().trim();



                if(nodeTimHangHoa.equals("")){
                    Toast.makeText(QLGhiChu.this, "Ô tìm kiếm bỏ trống rồi >.<", Toast.LENGTH_SHORT).show();
                    getdatagc();

                }else{
                    Cursor dataGhiChu = database.GetData("SELECT * FROM GhiChu WHERE TieuDe like '%"+nodeTimHangHoa+"%'");
                    ghiChuArrayList.clear();
                    while (dataGhiChu.moveToNext()) {
                        String id = dataGhiChu.getString(0);
                        String ngayTao = dataGhiChu.getString(1);
                        String tieuDe = dataGhiChu.getString(2);
                        String noiDung = dataGhiChu.getString(3);



                        ghiChuArrayList.add(new GhiChu(id, ngayTao, tieuDe, noiDung));
                    }
                    gcAdapter.notifyDataSetChanged();
                }
                if(lv.getAdapter().getCount() == 0){
                    Toast.makeText(QLGhiChu.this, "Không Tìm Thấy Tên ("+nodeTimHangHoa+")", Toast.LENGTH_SHORT).show();
                    getdatagc();
                }


                return false;
            }
        });


    }

    public void Dialonglogin(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popupghichu);
        dialog.setCanceledOnTouchOutside(false);
        EditText editTieuDe = (EditText) dialog.findViewById(R.id.editTieuDe);
        EditText editNoiDung = (EditText) dialog.findViewById(R.id.editNoiDung);

        Button btnhuy  = (Button)  dialog.findViewById(R.id.btnHuyGhiChu);
        Button btnThemGhiChu = (Button) dialog.findViewById(R.id.btnTThemGhiChu);


        btnThemGhiChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TieuDeNew = editTieuDe.getText().toString().trim();
                String NoiDungNew = editNoiDung.getText().toString().trim();

                if(TieuDeNew.equals("")){
                    Toast.makeText(QLGhiChu.this, "Tiêu Đề Trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(NoiDungNew.equals("")){
                    Toast.makeText(QLGhiChu.this, "Nội Dung Trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                database.QueryData("INSERT INTO GhiChu VALUES(null,'" + homnay + "','" + TieuDeNew + "','" + NoiDungNew + "')");
                Toast.makeText(QLGhiChu.this, "Đã Thêm", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getdatagc();
                
            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    public void DialongThem(String layid){


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popupthem);
        dialog.setCanceledOnTouchOutside(false);
        EditText editTieuDeThem = (EditText) dialog.findViewById(R.id.editTieuDeThem);
        EditText editNoiDungThem = (EditText) dialog.findViewById(R.id.editNoiDungThem);
        Button btnsua = (Button)  dialog.findViewById(R.id.btnSuaGhiChu1);
        Button btnhuy  = (Button)  dialog.findViewById(R.id.btnHuyGhiChu);

        editTieuDeThem.setText(sharedPreferences.getString("tieuDe",""));
        editNoiDungThem.setText(sharedPreferences.getString("noiDung",""));



        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TieuDeNew = editTieuDeThem.getText().toString().trim();
                String NoiDungNew = editNoiDungThem.getText().toString().trim();
                if(TieuDeNew.equals("")){
                    Toast.makeText(QLGhiChu.this, "Tiêu Đề Trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(NoiDungNew.equals("")){
                    Toast.makeText(QLGhiChu.this, "Nội Dung Trống", Toast.LENGTH_SHORT).show();
                    return;
                }
              database.QueryData("UPDATE GhiChu SET  TieuDe= '"+TieuDeNew+"', NoiDung ='"+NoiDungNew+"' WHERE Id ='"+layid+"'");
                dialog.dismiss();
                getdatagc();
            }
        });





//        btnhuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.QueryData("DELETE FROM GhiChu WHERE Id = '"+layid+"'");
                dialog.dismiss();
                getdatagc();
            }
        });
        ghiChuArrayList.clear();
        dialog.show();

    }



    private void getdatagc() {
        Cursor dataGhiChu = database.GetData("SELECT * FROM GhiChu");
        ghiChuArrayList.clear();
        while (dataGhiChu.moveToNext()) {
            String id = dataGhiChu.getString(0);
            String ngayTao = dataGhiChu.getString(1);
            String tieuDe = dataGhiChu.getString(2);
            String noiDung = dataGhiChu.getString(3);



            ghiChuArrayList.add(new GhiChu(id, ngayTao, tieuDe, noiDung));
        }
        gcAdapter.notifyDataSetChanged();
    }


}

