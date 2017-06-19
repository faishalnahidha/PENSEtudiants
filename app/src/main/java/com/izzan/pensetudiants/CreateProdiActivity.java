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

import com.izzan.pensetudiants.models.ProgramStudi;

public class CreateProdiActivity extends AppCompatActivity {

    private EditText editTextNamaProdi;
    private AutoCompleteTextView actvDepartemen;
    private EditText editTextNoTelp;

    private Button buttonSimpan;
    private ImageButton buttonCancel;

    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prodi);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.createProdi_coordinationLayout);

        editTextNamaProdi = (EditText) findViewById(R.id.createProdi_editTextNama);
        actvDepartemen = (AutoCompleteTextView) findViewById(R.id.createProdi_editTextDepartemen);
        editTextNoTelp = (EditText) findViewById(R.id.createProdi_editTextNoTelp);

        buttonSimpan = (Button) findViewById(R.id.createProdi_buttonSimpan);
        buttonCancel = (ImageButton) findViewById(R.id.createProdi_buttonCancel);

        /**
         * set autocomplete item for departemen
         */
        String[] arrayDepartement = getResources().getStringArray(R.array.list_departement);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, arrayDepartement);
        actvDepartemen.setAdapter(arrayAdapter);

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

                try  {
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }

                String namaProdi = editTextNamaProdi.getText().toString().trim();

                if (TextUtils.isEmpty(namaProdi)) {
                    //editTextNamaProdi is empty
                    Snackbar.make(coordinatorLayout, "Silahkan masukkan nama program studi",
                            Snackbar.LENGTH_LONG).show();
                } else {
                    //editTextNamaProdi is not empty
                    String departemen = actvDepartemen.getText().toString().trim();

                    if (TextUtils.isEmpty(departemen)) {
                        Snackbar.make(coordinatorLayout, "Silahkan masukkan nama departemen",
                                Snackbar.LENGTH_LONG).show();
                    } else {
                        int found = 0;
                        String[] arrayDepartement = getResources().getStringArray(R.array.list_departement);

                        for (int i = 0; i < arrayDepartement.length; i++) {
                            if (arrayDepartement[i].equalsIgnoreCase(departemen)) {
                                found = 1;
                            }
                        }

                        if (found == 1) {
                            saveProdi();
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("PRODI_BARU", namaProdi);
                            setResult(RESULT_OK, returnIntent);

                            finish();

                        } else {
                            Snackbar.make(coordinatorLayout, "Departemen tidak terdaftar",
                                    Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    private void saveProdi() {
        String namaProdi = editTextNamaProdi.getText().toString().trim();
        String departemen = actvDepartemen.getText().toString().trim();
        String noTelp = editTextNoTelp.getText().toString().trim();

        ProgramStudi mProdi = new ProgramStudi(namaProdi, departemen, noTelp);
        mProdi.save();

        Log.i("NEW_PRODI", "id = " + mProdi.getId().toString() + ", nama prodi = " +
                mProdi.getNamaProdi());
    }
}
