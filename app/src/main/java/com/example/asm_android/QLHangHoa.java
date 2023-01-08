package com.example.asm_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QLHangHoa extends AppCompatActivity {
    private ArrayList<HangHoa> hangHoaArrayList = new ArrayList<>();
    public HangHoaAdapter hhAdapter;
    ImageView btnMore;
    ListView lv;
    Database database;
    int vitri = -1;
    TextView tongsohh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlhang_hoa);

        EditText edtTenHH = (EditText) findViewById(R.id.editTenhh);
        EditText edtTinhTrang = (EditText) findViewById(R.id.editTinhTrang);
        EditText edtSoLuong = (EditText) findViewById(R.id.editSoLuong);
        EditText edtGhiChu = (EditText) findViewById(R.id.editGhiChu);


        btnMore = findViewById(R.id.btnMore);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });


        lv = findViewById(R.id.lvHangHoa);
        hangHoaArrayList = new ArrayList<>();

//        hangHoaArrayList.add(new HangHoa("1","Hoa Cỏ May","CÒn","100","Không"));
//        hangHoaArrayList.add(new HangHoa("2","Hoa Hồng","Còn","50","Không"));


        hhAdapter = new HangHoaAdapter(QLHangHoa.this, R.layout.layout_hanghoa, hangHoaArrayList);

        lv.setAdapter(hhAdapter);

        //Tạo Bảng trong database
        database = new Database(this, "HangHoa", null, 1);


        database.QueryData(
                "CREATE TABLE IF NOT EXISTS HangHoa(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "TenHangHoa VARCHAR(200) NOT NULL," +
                        "TinhTRang VARCHAR(20) NOT NULL," +
                        "SoLuong VARCHAR(50) NOT NULL," +
                        "GhiChu VARCHAR(50) NOT NULL)"
        );

        //    database.QueryData("INSERT INTO HangHoa VALUES(null,'Hoa  ANh Đào ','Còn','50 Bông','Không')");

        getdatahh();
// Tìm Kiếm


        EditText editTimHangHoa = findViewById(R.id.editTimKiemHangHoa);

        ImageView btnTimHangHoa = findViewById(R.id.btnTimHangHoa);
        editTimHangHoa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String nodeTimHangHoa = editTimHangHoa.getText().toString().trim();
                Cursor dataHangHoa = database.GetData("SELECT * FROM HangHoa WHERE TenHangHoa like '%"+nodeTimHangHoa+"%'");
                hangHoaArrayList.clear();
                while (dataHangHoa.moveToNext()) {
                    String id = dataHangHoa.getString(0);
                    String tenhh = dataHangHoa.getString(1);
                    String tinhTang = dataHangHoa.getString(2);
                    String soLuong = dataHangHoa.getString(3);
                    String ghiChu = dataHangHoa.getString(4);


                    hangHoaArrayList.add(new HangHoa(id, tenhh, tinhTang, soLuong, ghiChu));
                }
                hhAdapter.notifyDataSetChanged();

                if(lv.getAdapter().getCount() == 0){
                    Toast.makeText(QLHangHoa.this, "Không Tìm Thấy Tên ("+nodeTimHangHoa+")", Toast.LENGTH_SHORT).show();
                    getdatahh();

                }

                return false;
            }
        });



        btnTimHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nodeTimHangHoa = editTimHangHoa.getText().toString().trim();



                if(nodeTimHangHoa.equals("")){
                    Toast.makeText(QLHangHoa.this, "Ô tìm kiếm bỏ trống rồi >.<", Toast.LENGTH_SHORT).show();
                    getdatahh();

                }else{
                    Cursor dataHangHoa = database.GetData("SELECT * FROM HangHoa WHERE TenHangHoa like '%"+nodeTimHangHoa+"%'");
                    hangHoaArrayList.clear();
                    while (dataHangHoa.moveToNext()) {
                        String id = dataHangHoa.getString(0);
                        String tenhh = dataHangHoa.getString(1);
                        String tinhTang = dataHangHoa.getString(2);
                        String soLuong = dataHangHoa.getString(3);
                        String ghiChu = dataHangHoa.getString(4);


                        hangHoaArrayList.add(new HangHoa(id, tenhh, tinhTang, soLuong, ghiChu));
                    }
                    hhAdapter.notifyDataSetChanged();
                }
                if(lv.getAdapter().getCount() == 0){
                    Toast.makeText(QLHangHoa.this, "Không Tìm Thấy Tên ("+nodeTimHangHoa+")", Toast.LENGTH_SHORT).show();
                    getdatahh();
                }


            }
        });
// Sắp Xếp tăng Dần
        LinearLayout btnSoLuongTangDan = findViewById(R.id.btnSoLuongtangDan);
        btnSoLuongTangDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor dataHangHoa = database.GetData("SELECT * FROM HangHoa  ORDER BY SoLuong ASC");
                hangHoaArrayList.clear();
                while (dataHangHoa.moveToNext()) {
                    String id = dataHangHoa.getString(0);
                    String tenhh = dataHangHoa.getString(1);
                    String tinhTang = dataHangHoa.getString(2);
                    String soLuong = dataHangHoa.getString(3);
                    String ghiChu = dataHangHoa.getString(4);


                    hangHoaArrayList.add(new HangHoa(id, tenhh, tinhTang, soLuong, ghiChu));
                }
                hhAdapter.notifyDataSetChanged();
            }
        });
        // Sắp Xếp Giảm Dần
        LinearLayout btnSoLuongGiamDan = findViewById(R.id.btnSoLuongGiamdam);
        btnSoLuongGiamDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor dataHangHoa = database.GetData("SELECT * FROM HangHoa  ORDER BY SoLuong DESC");
                hangHoaArrayList.clear();
                while (dataHangHoa.moveToNext()) {
                    String id = dataHangHoa.getString(0);
                    String tenhh = dataHangHoa.getString(1);
                    String tinhTang = dataHangHoa.getString(2);
                    String soLuong = dataHangHoa.getString(3);
                    String ghiChu = dataHangHoa.getString(4);


                    hangHoaArrayList.add(new HangHoa(id, tenhh, tinhTang, soLuong, ghiChu));
                }
                hhAdapter.notifyDataSetChanged();
            }
        });

// thêm hàng hóa
        LinearLayout btnThemHangHoa = findViewById(R.id.btnThemHangHoa);

        btnThemHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tenHangHoaNew = edtTenHH.getText().toString();
                String tinhTrangnew = edtTinhTrang.getText().toString();
                String soLuongNew = edtSoLuong.getText().toString();
                String ghiChuNew = edtGhiChu.getText().toString();

                if(tenHangHoaNew.equals("")){
                    Toast.makeText(QLHangHoa.this, "Chưa Nhập Tên Hàng Hóa", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tinhTrangnew.equals("")){
                    Toast.makeText(QLHangHoa.this, "Chưa Nhập Tình Trạng Hàng Hóa", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(soLuongNew.equals("")){
                    Toast.makeText(QLHangHoa.this, "Chưa Nhập Số Lượng Hàng Hóa", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tinhTrangnew.equals("")){
                    Toast.makeText(QLHangHoa.this, "Chưa Nhập Tình Trạng Hàng Hóa", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!soLuongNew.matches( "^[0-9]*$")||soLuongNew.equals("0")) {
                    Toast.makeText(QLHangHoa.this, "Lương là Số Phải Lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;

                }

                AlertDialog.Builder aBuilder = new AlertDialog.Builder(QLHangHoa.this);
                aBuilder.setMessage("Bạn có chắc chắn thêm (_" + tenHangHoaNew + "_) không?");
                aBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int j) {
                        database.QueryData("INSERT INTO HangHoa VALUES(null,'" + tenHangHoaNew + "','" + tinhTrangnew + "','" + soLuongNew + "','" + ghiChuNew + "')");
                        Toast.makeText(QLHangHoa.this, "Đã Thêm", Toast.LENGTH_SHORT).show();
                        getdatahh();

                        edtTenHH.setText("");
                        edtTinhTrang.setText("");
                        edtSoLuong.setText("");
                        edtGhiChu.setText("");
                        TongSoHangHoa();
                    }
                });
                //nút không
                aBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aBuilder.show();

            }
        });


        hhAdapter.notifyDataSetChanged();
//         xác định vị trí click

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tenHH = hangHoaArrayList.get(position).getTenHh();
                String tinhTrang = hangHoaArrayList.get(position).getTinhTrang();
                String soLuong = hangHoaArrayList.get(position).getSoLuong();
                String ghichu = hangHoaArrayList.get(position).getGhichu();

                edtTenHH.setText(tenHH);
                edtTinhTrang.setText(tinhTrang);
                edtSoLuong.setText(soLuong);
                edtGhiChu.setText(ghichu);
                vitri = position;
            }
        });
        // sửa nè
        LinearLayout btnSuaHangHoa = findViewById(R.id.btnSuaHangHoa);
        btnSuaHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tenHangHoaSua = edtTenHH.getText().toString();
                String tinhTrangSua = edtTinhTrang.getText().toString();
                String soLuongSua = edtSoLuong.getText().toString();
                String ghiChuSua = edtGhiChu.getText().toString();
                String idHH = hangHoaArrayList.get(vitri).getIdHh();



                if(tenHangHoaSua.equals("")){
                    Toast.makeText(QLHangHoa.this, "Chưa Nhập Tên Hàng Hóa", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tinhTrangSua.equals("")){
                    Toast.makeText(QLHangHoa.this, "Chưa Nhập Tình Trạng Hàng Hóa", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(soLuongSua.equals("")){
                    Toast.makeText(QLHangHoa.this, "Chưa Nhập Số Lượng Hàng Hóa", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!soLuongSua.matches( "^[0-9]*$")||soLuongSua.equals("0")) {
                    Toast.makeText(QLHangHoa.this, "Lương là Số Phải Lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;

                }
                AlertDialog.Builder aBuilder = new AlertDialog.Builder(QLHangHoa.this);
                aBuilder.setMessage("Bạn có chắc chắn sửa không?");
                aBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int j) {
                        database.QueryData("UPDATE HangHoa SET TenHangHoa ='"+tenHangHoaSua+"',TinhTRang ='"+tinhTrangSua+"',SoLuong='"+soLuongSua+"',GhiChu='"+ghiChuSua+"' Where Id = '"+idHH+"'");
                        Toast.makeText(QLHangHoa.this, "Đã Thêm", Toast.LENGTH_SHORT).show();
                        getdatahh();

                        edtTenHH.setText("");
                        edtTinhTrang.setText("");
                        edtSoLuong.setText("");
                        edtGhiChu.setText("");

                    }
                });
                //nút không
                aBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aBuilder.show();


            }
        });

        //Xóa

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder aBuilder = new AlertDialog.Builder(QLHangHoa.this);
                aBuilder.setMessage("Bạn có chắc chắn xóa không?");
                aBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int j) {

                        //Nếu click nút có thì sẽ xóa
                        String idxoa = hangHoaArrayList.get(position).getIdHh();
//                            Toast.makeText(QLHangHoa.this, idxoa+"  id", Toast.LENGTH_SHORT).show();

                        database.QueryData("DELETE FROM HangHoa WHERE Id='" + idxoa + "'");
                        Toast.makeText(QLHangHoa.this, "Đã xóa thành công ", Toast.LENGTH_SHORT).show();


                        getdatahh();
                        TongSoHangHoa();
                    }
                });
                //nút không
                aBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                aBuilder.show();

                return false;
            }
        });
        TongSoHangHoa();
    }
    public void DialogXoaHH(String tenHH, String vitri) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn Có Chắc Chắn Muốn Xóa Sản Phẩm (" + tenHH + ") Không???");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                xoaHangHoa(vitri);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }

    // xóa Cách 2
    public void xoaHangHoa(String idxoanv) {

        database.QueryData("DELETE FROM HangHoa WHERE Id='" + idxoanv + "'");
        Toast.makeText(QLHangHoa.this, "Đã xóa thành công ", Toast.LENGTH_SHORT).show();
        getdatahh();
    }

    private  void TongSoHangHoa(){
        tongsohh = (TextView) findViewById(R.id.tongsohanghoa);
        int tong = hangHoaArrayList.size();
        tongsohh.setText("Có Tổng Số "+tong+" Hàng Hóa");



    }
    private void getdatahh() {
        Cursor dataHangHoa = database.GetData("SELECT * FROM HangHoa");
        hangHoaArrayList.clear();
        while (dataHangHoa.moveToNext()) {
            String id = dataHangHoa.getString(0);
            String tenhh = dataHangHoa.getString(1);
            String tinhTang = dataHangHoa.getString(2);
            String soLuong = dataHangHoa.getString(3);
            String ghiChu = dataHangHoa.getString(4);


            hangHoaArrayList.add(new HangHoa(id, tenhh, tinhTang, soLuong, ghiChu));
        }
        hhAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void showMenu() {
        PopupMenu popupMenu = new PopupMenu(QLHangHoa.this, btnMore);
        popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                    case  R.id.dangXuat:
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });
        popupMenu.show();
    }
}