package com.izzan.pensetudiants.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Aizen on 17 Jun 2017.
 */

@Table(name = "program_studi", id = "_id")
public class ProgramStudi extends Model{

    @Column(name = "nama_prodi")
    private String namaProdi;

    @Column(name = "departemen")
    private String departemen;

    @Column(name = "no_telp")
    private String noTelp;

    public ProgramStudi() {
        super();
    }

    public ProgramStudi(String namaProdi, String departemen, String noTelp) {
        this.namaProdi = namaProdi;
        this.departemen = departemen;
        this.noTelp = noTelp;
    }

    public String getNamaProdi() {
        return namaProdi;
    }

    public void setNamaProdi(String namaProdi) {
        this.namaProdi = namaProdi;
    }

    public String getDepartemen() {
        return departemen;
    }

    public void setDepartemen(String departemen) {
        this.departemen = departemen;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public static List<ProgramStudi> getAll() {
        // This is how you execute a query
        return new Select()
                .from(ProgramStudi.class)
                .orderBy("nama_prodi asc")
                .execute();
    }

    public static ProgramStudi getRandom() {
        return new Select()
                .from(ProgramStudi.class)
                .orderBy("RANDOM()")
                .executeSingle();
    }

    public static ProgramStudi getByNamaProdi(String namaProdi){
        return new Select()
                .from(ProgramStudi.class)
                .where("nama_prodi = ?", namaProdi)
                .executeSingle();
    }
}
