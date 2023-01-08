package com.example.asm_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NhanVienAdapter  extends BaseAdapter {
    QLNhanVien myContext;
    int myLayout;
    List<NhanVien> arrNhanVien;

    public NhanVienAdapter(QLNhanVien myContext, int myLayout, List<NhanVien> arrNhanVien) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrNhanVien = arrNhanVien;
    }

    @Override
    public int getCount() {
        return arrNhanVien.size();
    }

    @Override
    public Object getItem(int position) {
        return arrNhanVien.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    private class ViewHolder {
        TextView txtTenNhanVien,txtGioiTinh,txtchucVu,txtngayVaoLam,txtluong,txtsoDienThoai,txtDiaChi,id;

        ImageView imgXoa, imgSua;

    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(myLayout,null);
            holder.id = view.findViewById(R.id.txtId);
            holder.txtTenNhanVien= view.findViewById(R.id.txtTen);
            holder.txtGioiTinh= view.findViewById(R.id.txtGioiTinh);
            holder.txtchucVu= view.findViewById(R.id.txtChucVu);
            holder.txtngayVaoLam= view.findViewById(R.id.txtNgayVaoLam);
            holder.txtluong= view.findViewById(R.id.txtLuong);
            holder.txtsoDienThoai= view.findViewById(R.id.txtSoDT);
            holder.txtDiaChi= view.findViewById(R.id.txtDiaChi);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        NhanVien nhanVien = arrNhanVien.get(i);
        holder.id.setText(nhanVien.getIdNv());
        holder.txtTenNhanVien.setText(nhanVien.getTenNv());
        holder.txtGioiTinh.setText(nhanVien.getGioiTinh());
        holder.txtDiaChi.setText(nhanVien.getChucVu());
        holder.txtchucVu.setText(nhanVien.getChucVu());
        holder.txtngayVaoLam.setText(nhanVien.getNgayVaoLam());
        holder.txtluong.setText(nhanVien.getLuong());
        holder.txtsoDienThoai.setText(nhanVien.getSoDienThoai());
        holder.txtDiaChi.setText(nhanVien.getDiaChi());




        ImageView btnXoaNV = (ImageView) view.findViewById(R.id.btnNVXoa);

        btnXoaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myContext.DialogXoaNV(nhanVien.getTenNv(),nhanVien.getIdNv());

            }
        });

        return view;


    }


}
