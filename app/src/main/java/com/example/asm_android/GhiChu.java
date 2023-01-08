package com.example.asm_android;

public class GhiChu {
    public  String id;
    public  String ngayTao;
    public  String tieuDe;
    public  String noiDung;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public GhiChu(String id, String ngayTao, String tieuDe, String noiDung) {
        this.id = id;
        this.ngayTao = ngayTao;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
    }
}
