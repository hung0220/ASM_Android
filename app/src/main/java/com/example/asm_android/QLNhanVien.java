package com.example.asm_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QLNhanVien extends AppCompatActivity {
    private ArrayList<NhanVien> nhanVienArrayList = new ArrayList<>();
    public NhanVienAdapter nvAdapter;
    ImageView btnMore;
    ListView lv;
    Database database;
    int vitri = -1;
    TextView tongso1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlnhan_vien);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dinhangNgayThangNam = new SimpleDateFormat("dd/MM/yyyy");

        EditText edtTen = (EditText) findViewById(R.id.editTennv);
        EditText edtGioiTinh = (EditText) findViewById(R.id.editGioiTinh);
        EditText edtChucVu = (EditText) findViewById(R.id.editchucVu);
        EditText edtNgayVaoLam = (EditText) findViewById(R.id.editNgayVaoLam);
        EditText edtLuong = (EditText) findViewById(R.id.editLuong);
        EditText edtSoDienThoai = (EditText) findViewById(R.id.editSoDienThoai);
        EditText edtDiaChi = (EditText) findViewById(R.id.editDiaChi);





// menu trên
        btnMore = findViewById(R.id.btnMore);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });

        // listView
        lv = findViewById(R.id.lvNhanVien);
        nhanVienArrayList = new ArrayList<>();

//        nhanVienArrayList.add(new NhanVien("1","Nguyễn Văn Anh","Nam","Giấm Đóc","20/3/2003","12000","0965450520","123 Hà Huy Tập"));
//        nhanVienArrayList.add(new NhanVien("2","Nguyễn Thị Anh","Nam","Giấm Đóc","20/3/2003","12000","0965450520","123 Hà Huy Tập"));
////        spAdapter = new NhanVienAdapter(MainActivity.this,R.layout.layout_nhanvien,nhanVienArrayList);
        nvAdapter = new NhanVienAdapter(this, R.layout.layout_nhanvien, nhanVienArrayList);

        lv.setAdapter(nvAdapter);
        //Tạo Bảng trong database
        database = new Database(this, "NhanVien", null, 1);

        database.QueryData(
                "CREATE TABLE IF NOT EXISTS NhanVien(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "TenNhanVien VARCHAR(200) NOT NULL," +
                        "GioiTinh VARCHAR(20) NOT NULL," +
                        "ChucVu VARCHAR(50) NOT NULL," +
                        "NgayVaoLam VARCHAR(50) NOT NULL," +
                        "Luong VARCHAR(50) NOT NULL," +
                        "SoDianThoai VARCHAR(10) NOT NULL," +
                        "Diachi VARCHAR(200) NOT NULL)"
        );

        //Thêm dữ liệu vào bảng
    //    database.QueryData("INSERT INTO NhanVien VALUES(null,'Nguyễn Thị Hoa','Nữ','Nhân Viên','5/5/2003','5000','0847577475','Tôn Hành Giả')");

        getdata();
//         xác định vị trí click

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String tenNVV = nhanVienArrayList.get(position).getChucVu();
//                   Toast.makeText(QLNhanVien.this, tenNVV, Toast.LENGTH_SHORT).show();
                String tenNV = nhanVienArrayList.get(position).getTenNv();
                String gioiTinh = nhanVienArrayList.get(position).getGioiTinh();
                String chucVu = nhanVienArrayList.get(position).getChucVu();
                String ngayVaoLam = nhanVienArrayList.get(position).getNgayVaoLam();
                String luong = nhanVienArrayList.get(position).getLuong();
                String soDienThoai = nhanVienArrayList.get(position).getSoDienThoai();
                String diaChi = nhanVienArrayList.get(position).getDiaChi();

                edtTen.setText(tenNV);
                edtGioiTinh.setText(gioiTinh);
                edtChucVu.setText(chucVu);
                edtNgayVaoLam.setText(ngayVaoLam);
                edtLuong.setText(luong);
                edtSoDienThoai.setText(soDienThoai);
                edtDiaChi.setText(diaChi);
                vitri = position;
            }
        });



        // Thêm

        LinearLayout btnThemNhanVien = findViewById(R.id.btnThemNhanVien);

        btnThemNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tenNhanVienNew = edtTen.getText().toString();
                String gioiTinhnew = edtGioiTinh.getText().toString();
                String chucVuNew = edtChucVu.getText().toString();
                String ngayVaoLamNew = edtNgayVaoLam.getText().toString();
                String luongNew = edtLuong.getText().toString();
                String soDienThoaiNew = edtSoDienThoai.getText().toString();
                String diaChiVienNew = edtDiaChi.getText().toString();

// check định dạng

                String homnay = dinhangNgayThangNam.format(calendar.getTime());

                try {
                    Date nvl = dinhangNgayThangNam.parse(ngayVaoLamNew);
                    Date nhn = dinhangNgayThangNam.parse(homnay);
                    if (nvl.after(nhn)) {
                        Toast.makeText(QLNhanVien.this, "Ngày VÀo làm phải trước ngày hôm nay", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(QLNhanVien.this, "Sai đinh dạng dd//DD//yyyy", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tenNhanVienNew.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Tên Nhân Viên", Toast.LENGTH_SHORT).show();
                    return;
                } else if (gioiTinhnew.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Giới Tính", Toast.LENGTH_SHORT).show();
                    return;
                } else if (chucVuNew.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Chức Vụ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ngayVaoLamNew.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Ngày Vào Làm", Toast.LENGTH_SHORT).show();
                    return;
                } else if (luongNew.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Lương", Toast.LENGTH_SHORT).show();
                    return;
                } else if (soDienThoaiNew.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Số Điện Thoại", Toast.LENGTH_SHORT).show();
                    return;
                } else if (diaChiVienNew.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập ĐỊa Chỉ", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(!luongNew.matches( "^[0-9]*$")||luongNew.equals("0")){
                    Toast.makeText(QLNhanVien.this, "Lương là Số Phải Lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!soDienThoaiNew.matches("^[0-9]{10}$")){
                    Toast.makeText(QLNhanVien.this, "Sai Số điện toại", Toast.LENGTH_SHORT).show();
                    return;
                }


                else {
                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(QLNhanVien.this);
                    aBuilder.setMessage("Bạn có chắc chắn thêm (_" + tenNhanVienNew + "_) không?");
                    aBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int j) {
                            database.QueryData("INSERT INTO NhanVien VALUES(null,'" + tenNhanVienNew + "','" + gioiTinhnew + "','" + chucVuNew + "','" + ngayVaoLamNew + "','" + luongNew + "','" + soDienThoaiNew + "','" + diaChiVienNew + "')");

                            Toast.makeText(QLNhanVien.this, "Đã Thêm", Toast.LENGTH_SHORT).show();

                            getdata();

                            edtTen.setText("");
                            edtChucVu.setText("");
                            edtDiaChi.setText("");
                            edtGioiTinh.setText("");
                            edtLuong.setText("");
                            edtNgayVaoLam.setText("");
                            edtSoDienThoai.setText("");
                            TongSoNhanVien();

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

            }

        });
// sua

        LinearLayout btnSuaNhanVien = findViewById(R.id.btnSuaNhanVien);

        btnSuaNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenNhanVienSua = edtTen.getText().toString();
                String gioiTinhSua = edtGioiTinh.getText().toString();
                String chucVuSua = edtChucVu.getText().toString();
                String ngayVaoLamSua = edtNgayVaoLam.getText().toString();
                String luongSua = edtLuong.getText().toString();
                String soDienThoaiSua = edtSoDienThoai.getText().toString();
                String diaChiVienSua = edtDiaChi.getText().toString();
                String idSuaNhanVien = nhanVienArrayList.get(vitri).getIdNv();



                String homnay = dinhangNgayThangNam.format(calendar.getTime());

                try {
                    Date nvl = dinhangNgayThangNam.parse(ngayVaoLamSua);
                    Date nhn = dinhangNgayThangNam.parse(homnay);
                    if (nvl.after(nhn)) {
                        Toast.makeText(QLNhanVien.this, "Ngày VÀo làm phải trước ngày hôm nay", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(QLNhanVien.this, "Sai đinh dạng dd//DD//yyyy", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tenNhanVienSua.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Tên Nhân Viên", Toast.LENGTH_SHORT).show();
                    return;
                } else if (gioiTinhSua.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Giới Tính", Toast.LENGTH_SHORT).show();
                    return;
                } else if (chucVuSua.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Chức Vụ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ngayVaoLamSua.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Ngày Vào Làm", Toast.LENGTH_SHORT).show();
                    return;
                } else if (luongSua.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Lương", Toast.LENGTH_SHORT).show();
                    return;
                } else if (soDienThoaiSua.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập Số Điện Thoại", Toast.LENGTH_SHORT).show();
                    return;
                } else if (diaChiVienSua.equals("")) {
                    Toast.makeText(QLNhanVien.this, "Vui lòng nhập ĐỊa Chỉ", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(!luongSua.matches( "^[0-9]*$")||luongSua.equals("0")){
                    Toast.makeText(QLNhanVien.this, "Lương là Số Phải Lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!soDienThoaiSua.matches("^[0-9]{10}$")){
                    Toast.makeText(QLNhanVien.this, "Sai Số điện toại", Toast.LENGTH_SHORT).show();
                    return;
                }


                else {

                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(QLNhanVien.this);
                    aBuilder.setMessage("Bạn có chắc chắn sửa không?");
                    aBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int j) {

                            database.QueryData("UPDATE NhanVien SET TenNhanVien = '" + tenNhanVienSua + "',GioiTinh = '" + gioiTinhSua + "',ChucVu = '" + chucVuSua + "',NgayVaoLam = '" + ngayVaoLamSua + "',Luong = '" + luongSua + "',SoDianThoai='" + soDienThoaiSua + "',Diachi ='" + diaChiVienSua + "' WHERE Id = '" + idSuaNhanVien + "'");
                            getdata();
                            Toast.makeText(QLNhanVien.this, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            edtTen.setText("");
                            edtChucVu.setText("");
                            edtDiaChi.setText("");
                            edtGioiTinh.setText("");
                            edtLuong.setText("");
                            edtNgayVaoLam.setText("");
                            edtSoDienThoai.setText("");
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

            }
        });

        // sắp sếp tăng dần
        LinearLayout btnLuongTangDan = findViewById(R.id.btnLuongtangDan);
        btnLuongTangDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor dataNhanVien = database.GetData("SELECT * FROM NhanVien  ORDER BY Luong ASC");
                nhanVienArrayList.clear();
                while (dataNhanVien.moveToNext()) {
                    String id = dataNhanVien.getString(0);
                    String tenNv = dataNhanVien.getString(1);
                    String gioiTinh = dataNhanVien.getString(2);
                    String chucVu = dataNhanVien.getString(3);
                    String ngayVaoLam = dataNhanVien.getString(4);
                    String luong = dataNhanVien.getString(5);
                    String soDianThoai = dataNhanVien.getString(6);
                    String diaChi = dataNhanVien.getString(7);

                    nhanVienArrayList.add(new NhanVien(id, tenNv, gioiTinh, chucVu, ngayVaoLam, luong, soDianThoai, diaChi));
                }
                nvAdapter.notifyDataSetChanged();
            }
        });

        //Sắp xếp giảm dần
        LinearLayout btnLuongGiamDan = findViewById(R.id.btnluongGiamdam);
        btnLuongGiamDan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor dataNhanVien = database.GetData("SELECT * FROM NhanVien  ORDER BY Luong DESC");
                nhanVienArrayList.clear();
                while (dataNhanVien.moveToNext()) {
                    String id = dataNhanVien.getString(0);
                    String tenNv = dataNhanVien.getString(1);
                    String gioiTinh = dataNhanVien.getString(2);
                    String chucVu = dataNhanVien.getString(3);
                    String ngayVaoLam = dataNhanVien.getString(4);
                    String luong = dataNhanVien.getString(5);
                    String soDianThoai = dataNhanVien.getString(6);
                    String diaChi = dataNhanVien.getString(7);

                    nhanVienArrayList.add(new NhanVien(id, tenNv, gioiTinh, chucVu, ngayVaoLam, luong, soDianThoai, diaChi));
                }
                nvAdapter.notifyDataSetChanged();
            }
        });



//tim kiém
        ImageView btnTimNhanVien = findViewById(R.id.btnTimNhanVien);
        EditText editTimKiemNhanVien = findViewById(R.id.editTimKiemNhanVien1);

// Sắp xếp SELECT * FROM NhanVien ORDER BY Luong DESC
editTimKiemNhanVien.setOnEditorActionListener(new TextView.OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String nodeTimKiem = editTimKiemNhanVien.getText().toString().trim();
        Cursor dataNhanVien = database.GetData("SELECT * FROM NhanVien WHERE TenNhanVien like '%"+nodeTimKiem+"%'");
        nhanVienArrayList.clear();
        while (dataNhanVien.moveToNext()) {
            String id = dataNhanVien.getString(0);
            String tenNv = dataNhanVien.getString(1);
            String gioiTinh = dataNhanVien.getString(2);
            String chucVu = dataNhanVien.getString(3);
            String ngayVaoLam = dataNhanVien.getString(4);
            String luong = dataNhanVien.getString(5);
            String soDianThoai = dataNhanVien.getString(6);
            String diaChi = dataNhanVien.getString(7);

            nhanVienArrayList.add(new NhanVien(id, tenNv, gioiTinh, chucVu, ngayVaoLam, luong, soDianThoai, diaChi));
        }
        nvAdapter.notifyDataSetChanged();

        if(lv.getAdapter().getCount() == 0){
            Toast.makeText(QLNhanVien.this, "Không Tìm Thấy Tên ("+nodeTimKiem+")", Toast.LENGTH_SHORT).show();
            getdata();
        }

        return false;
    }
});
        btnTimNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nodeTimKiem = editTimKiemNhanVien.getText().toString().trim();
                if(nodeTimKiem.equals("")){
                    Toast.makeText(QLNhanVien.this, "Ô tìm kiếm bỏ trống rồi >.<", Toast.LENGTH_SHORT).show();
                    Cursor dataNhanVien = database.GetData("SELECT * FROM NhanVien ");
                    nhanVienArrayList.clear();
                   getdata();

                }else{
                Cursor dataNhanVien = database.GetData("SELECT * FROM NhanVien WHERE TenNhanVien like '%"+nodeTimKiem+"%'");
                nhanVienArrayList.clear();
                while (dataNhanVien.moveToNext()) {
                    String id = dataNhanVien.getString(0);
                    String tenNv = dataNhanVien.getString(1);
                    String gioiTinh = dataNhanVien.getString(2);
                    String chucVu = dataNhanVien.getString(3);
                    String ngayVaoLam = dataNhanVien.getString(4);
                    String luong = dataNhanVien.getString(5);
                    String soDianThoai = dataNhanVien.getString(6);
                    String diaChi = dataNhanVien.getString(7);

                    nhanVienArrayList.add(new NhanVien(id, tenNv, gioiTinh, chucVu, ngayVaoLam, luong, soDianThoai, diaChi));
                }
                nvAdapter.notifyDataSetChanged();
                }

                if(lv.getAdapter().getCount() == 0){
                    Toast.makeText(QLNhanVien.this, "Không Tìm Thấy Tên ("+nodeTimKiem+")", Toast.LENGTH_SHORT).show();
                    getdata();
                }


            }
        });



//xoa
        //    nvAdapter.notifyDataSetChanged();


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder aBuilder = new AlertDialog.Builder(QLNhanVien.this);
                aBuilder.setMessage("Bạn có chắc chắn xóa không?");
                aBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int j) {
                        //Nếu click nút có thì sẽ xóa
                        String idxoa = nhanVienArrayList.get(position).getIdNv();
//                            Toast.makeText(QLNhanVien.this, idxoa+"xoa", Toast.LENGTH_SHORT).show();

                        database.QueryData("DELETE FROM NhanVien WHERE Id='" + idxoa + "'");
                        Toast.makeText(QLNhanVien.this, "Đã xóa thành công ", Toast.LENGTH_SHORT).show();
                        getdata();
                        TongSoNhanVien();
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


        TongSoNhanVien();
    }

    private  void TongSoNhanVien(){
        tongso1 = (TextView) findViewById(R.id.tongso);
        int tong = nhanVienArrayList.size();
        tongso1.setText("Có Tổng Số "+tong+" Nhân viên");


    }

    private void getdata() {
        Cursor dataNhanVien = database.GetData("SELECT * FROM NhanVien");
        nhanVienArrayList.clear();
        while (dataNhanVien.moveToNext()) {
            String id = dataNhanVien.getString(0);
            String tenNv = dataNhanVien.getString(1);
            String gioiTinh = dataNhanVien.getString(2);
            String chucVu = dataNhanVien.getString(3);
            String ngayVaoLam = dataNhanVien.getString(4);
            String luong = dataNhanVien.getString(5);
            String soDianThoai = dataNhanVien.getString(6);
            String diaChi = dataNhanVien.getString(7);

            nhanVienArrayList.add(new NhanVien(id, tenNv, gioiTinh, chucVu, ngayVaoLam, luong, soDianThoai, diaChi));
        }
        nvAdapter.notifyDataSetChanged();

    }
    public void DialogXoaNV(String tenSP, String vitri) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn Có Chắc Chắn Muốn Xóa Sản Phẩm (" + tenSP + ") Không???");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                xoaNhanVien(vitri);
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
    public void xoaNhanVien(String idxoanv) {

        database.QueryData("DELETE FROM NhanVien WHERE Id='" + idxoanv + "'");
        Toast.makeText(QLNhanVien.this, "Đã xóa thành công ", Toast.LENGTH_SHORT).show();
        getdata();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void showMenu() {
        PopupMenu popupMenu = new PopupMenu(QLNhanVien.this, btnMore);
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