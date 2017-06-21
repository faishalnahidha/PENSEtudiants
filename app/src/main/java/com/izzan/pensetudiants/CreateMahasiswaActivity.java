package com.izzan.pensetudiants;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.activeandroid.query.Select;
import com.izzan.pensetudiants.models.Mahasiswa;
import com.izzan.pensetudiants.models.ProgramStudi;

import java.util.List;

public class CreateMahasiswaActivity extends AppCompatActivity {

    private EditText editTextNama;
    private EditText editTextNRP;
    private AutoCompleteTextView actvProdi;
    private EditText editTextNoHp;

    private Button buttonSimpan;
    private ImageButton buttonCancel;

    private CoordinatorLayout coordinatorLayout;

    private String[] arrayNamaProdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_mahasiswa);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.createMahasiswa_coordinationLayout);

        editTextNama = (EditText) findViewById(R.id.createMahasiswa_editTextNama);
        editTextNRP = (EditText) findViewById(R.id.createMahasiswa_editTextNRP);
        actvProdi = (AutoCompleteTextView) findViewById(R.id.createMahasiswa_editTextProdi);
        editTextNoHp = (EditText) findViewById(R.id.createMahasiswa_editTextNoHP);

        buttonSimpan = (Button) findViewById(R.id.createMahasiswa_buttonSimpan);
        buttonCancel = (ImageButton) findViewById(R.id.createMahasiswa_buttonCancel);

        /**
         * set autocomplete item for program studi
         */
        arrayNamaProdi = getNamaProdiArray();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, arrayNamaProdi);
        actvProdi.setAdapter(arrayAdapter);

        /**
         *  listener for (X) button
         */
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         *  listener for SIMPAN button
         */
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

                String nama = editTextNama.getText().toString().trim();

                if (TextUtils.isEmpty(nama)) {
                    //editTextNama is empty
                    Snackbar.make(coordinatorLayout, "Silahkan masukkan nama mahasiswa.",
                            Snackbar.LENGTH_LONG).show();
                } else {
                    String nrp = editTextNRP.getText().toString().trim();

                    if (TextUtils.isEmpty(nrp)) {
                        Snackbar.make(coordinatorLayout, "Silahkan masukkan NRP mahasiswa.",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        String prodi = actvProdi.getText().toString().trim();

                        if (TextUtils.isEmpty(prodi)) {
                            Snackbar.make(coordinatorLayout, "Silahkan masukkan program studi.",
                                    Snackbar.LENGTH_LONG).show();
                        } else {
                            int found = 0;

                            for(int i=0; i<arrayNamaProdi.length; i++){
                                if(arrayNamaProdi[i].equalsIgnoreCase(prodi)){
                                    found = 1;
                                    break;
                                }
                            }

                            if (found == 1){
                                saveMahasiswa();
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("MAHASISWA_BARU", nama);
                                setResult(RESULT_OK, returnIntent);

                                finish();
                            } else {
                                Snackbar.make(coordinatorLayout, "Program studi tidak terdaftar.",
                                        Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });

    }

    private void saveMahasiswa() {
        String nama = editTextNama.getText().toString().trim();
        String nrp = editTextNRP.getText().toString().trim();
        String namaProdi = actvProdi.getText().toString().trim();
        String noHp = editTextNoHp.getText().toString().trim();

        Mahasiswa mMahasiswa = new Mahasiswa();
        mMahasiswa.setNama(nama);
        mMahasiswa.setNrp(Integer.parseInt(nrp));
        mMahasiswa.setNoHp(noHp);

        ProgramStudi prodi = ProgramStudi.getByNamaProdi(namaProdi);
        mMahasiswa.setProgramStudi(prodi);

        mMahasiswa.save();

        Log.i("NEW_MAHASISWA", "id = " + mMahasiswa.getId().toString() +
                ", nama = " + mMahasiswa.getNama() +
                ", prodi = " + mMahasiswa.getProgramStudi().getNamaProdi());
    }

    private String[] getNamaProdiArray() {
        List<ProgramStudi> mProdiList =
                new Select(new String[]{"_id, nama_prodi"})
                        .from(ProgramStudi.class)
                        .execute();

        String[] mProdiArray = new String[mProdiList.size()];

        int i = 0;
        for(ProgramStudi programStudi:mProdiList){
            mProdiArray[i] = programStudi.getNamaProdi();
            Log.d("NAMA_PRODI", mProdiArray[i]);
            i++;
        }

        return mProdiArray;
    }


}
