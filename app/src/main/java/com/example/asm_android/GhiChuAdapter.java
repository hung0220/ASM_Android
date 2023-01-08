package com.example.asm_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GhiChuAdapter extends BaseAdapter {
    QLGhiChu myContext;
    int mylayout;
    List<GhiChu> arrGhiChu;

    public GhiChuAdapter(QLGhiChu myContext, int mylayout, List<GhiChu> arrGhiChu) {
        this.myContext = myContext;
        this.mylayout = mylayout;
        this.arrGhiChu = arrGhiChu;
    }

    @Override
    public int getCount() {
        return arrGhiChu.size();
    }

    @Override
    public Object getItem(int position) {
        return arrGhiChu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{


        TextView txtNgayTao,txtTieuDe,txtNoiDUng,id;

    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {

        GhiChuAdapter.ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mylayout,null);

            holder.txtNgayTao= view.findViewById(R.id.txtNgayTao);
            holder.txtTieuDe= view.findViewById(R.id.txttieuDe);
            holder.txtNoiDUng= view.findViewById(R.id.txtnoiDung);



            view.setTag(holder);

        } else {
            holder = (GhiChuAdapter.ViewHolder) view.getTag();
        }
        GhiChu ghiChu = arrGhiChu.get(i);

        holder.txtNgayTao.setText(ghiChu.getNgayTao());
        holder.txtTieuDe.setText(ghiChu.getTieuDe());
        holder.txtNoiDUng.setText(ghiChu.getNoiDung());



        return view;
    }
}
