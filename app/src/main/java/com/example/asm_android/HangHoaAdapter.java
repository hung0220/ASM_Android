package com.example.asm_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HangHoaAdapter extends BaseAdapter {
    QLHangHoa myContext;
    int myLayout;
    List<HangHoa> arrHangHoa;

    public HangHoaAdapter(QLHangHoa myContext, int myLayout, List<HangHoa> arrHangHoa) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrHangHoa = arrHangHoa;
    }

    @Override
    public int getCount() {
        return arrHangHoa.size();
    }

    @Override
    public Object getItem(int position) {
        return arrHangHoa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    protected class ViewHolder{


        TextView txtTenHangHoa,txtTinhTrang,txtSoLuong,txtGhiChu,id;

        ImageView imgXoa, imgSua;

    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {

        HangHoaAdapter.ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(myLayout,null);
            holder.id= view.findViewById(R.id.txtIdhh);
            holder.txtTenHangHoa= view.findViewById(R.id.txtTenhh);
            holder.txtTinhTrang= view.findViewById(R.id.txtTinhTrang);
            holder.txtSoLuong= view.findViewById(R.id.txtSoLuong);
            holder.txtGhiChu= view.findViewById(R.id.txtGhiChu);


            view.setTag(holder);

        } else {
            holder = (HangHoaAdapter.ViewHolder) view.getTag();
        }
        HangHoa hangHoa = arrHangHoa.get(i);
        holder.id.setText(hangHoa.getIdHh());
        holder.txtTenHangHoa.setText(hangHoa.getTenHh());
        holder.txtTinhTrang.setText(hangHoa.getTinhTrang());
        holder.txtSoLuong.setText(hangHoa.getSoLuong());
        holder.txtGhiChu.setText(hangHoa.getGhichu());
// Xóa cách 2
        ImageView btnXoaNV = (ImageView) view.findViewById(R.id.btnxoaHangHoa);

        btnXoaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myContext.DialogXoaHH(hangHoa.getTenHh(),hangHoa.getIdHh());

            }
        });

        return view;
    }
}
