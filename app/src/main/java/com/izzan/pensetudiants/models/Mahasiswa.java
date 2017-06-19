package com.izzan.pensetudiants.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Aizen on 17 Jun 2017.
 */

@Table(name = "mahasiswa")
public class Mahasiswa extends Model{

    @Column(name = "nama")
    private String nama;

    @Column(name = "nrp")
    private int nrp;

    @Column(name = "program_studi")
    private ProgramStudi programStudi;

    @Column(name = "no_hp")
    private String noHp;

    public Mahasiswa() {
        super();
    }

    public Mahasiswa(String nama, int nrp, ProgramStudi programStudi, String noHp) {
        super();
        this.nama = nama;
        this.nrp = nrp;
        this.programStudi = programStudi;
        this.noHp = noHp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getNrp() {
        return nrp;
    }

    public void setNrp(int nrp) {
        this.nrp = nrp;
    }

    public ProgramStudi getProgramStudi() {
        return programStudi;
    }

    public void setProgramStudi(ProgramStudi programStudi) {
        this.programStudi = programStudi;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public static List<Mahasiswa> getAll() {
        // This is how you execute a query
        return new Select()
                .from(Mahasiswa.class)
                .orderBy("title ASC")
                .execute();
    }

    public static Mahasiswa getRandom() {
        return new Select()
                .from(Mahasiswa.class)
                .orderBy("RANDOM()")
                .executeSingle();
    }
}
