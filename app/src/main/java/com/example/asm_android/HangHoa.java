package com.example.asm_android;

public class HangHoa {
    public String idHh;
    public String tenHh;
    public String tinhTrang;
    public String soLuong;
    public String ghichu;

    public String getIdHh() {
        return idHh;
    }

    public void setIdHh(String idHh) {
        this.idHh = idHh;
    }

    public String getTenHh() {
        return tenHh;
    }

    public void setTenHh(String tenHh) {
        this.tenHh = tenHh;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public HangHoa(String idHh, String tenHh, String tinhTrang, String soLuong, String ghichu) {
        this.idHh = idHh;
        this.tenHh = tenHh;
        this.tinhTrang = tinhTrang;
        this.soLuong = soLuong;
        this.ghichu = ghichu;
    }
}
