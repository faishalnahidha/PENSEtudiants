package com.izzan.pensetudiants;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.izzan.pensetudiants.R;
import com.izzan.pensetudiants.models.ProgramStudi;

public class ViewProdiActivity extends AppCompatActivity {

    private TextView textViewNamaProdi;
    private TextView textViewDepartemen;
    private TextView textViewNoTelp;

    private ImageButton buttonBack;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;

    private ProgramStudi mProdi;
    private int prodiEdited = 0;
    public static final int EDIT_PRODI_REQUEST_CODE = 202;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_prodi);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Long prodiId = extras.getLong("PRODI_ID");
            mProdi = ProgramStudi.load(ProgramStudi.class, prodiId);
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.viewProdi_coordinationLayout);

        textViewNamaProdi = (TextView) findViewById(R.id.viewProdi_textViewNama);
        textViewDepartemen = (TextView) findViewById(R.id.viewProdi_textViewDepartemen);
        textViewNoTelp = (TextView) findViewById(R.id.viewProdi_textViewNoTelp);

        buttonBack = (ImageButton) findViewById(R.id.viewProdi_buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnIntent = new Intent();
                if (prodiEdited == 1){
                    setResult(RESULT_OK, returnIntent);
                } else {
                    setResult(RESULT_CANCELED, returnIntent);
                }

                finish();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.viewProdi_fabEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProdiActivity.this, EditProdiActivity.class);
                intent.putExtra("PRODI_ID_EDIT", mProdi.getId());
                startActivityForResult(intent, EDIT_PRODI_REQUEST_CODE);
            }
        });

        setViewComponent();

    }

    private void setViewComponent() {
        textViewNamaProdi.setText(mProdi.getNamaProdi());
        textViewDepartemen.setText(mProdi.getDepartemen());

        if (!TextUtils.isEmpty(mProdi.getNoTelp())) {
            textViewNoTelp.setText(mProdi.getNoTelp());
            textViewNoTelp.setTextColor(ContextCompat.getColor(
                    getApplicationContext(), android.R.color.secondary_text_light));
            textViewNoTelp.setTypeface(null, Typeface.NORMAL);
        } else {
            textViewNoTelp.setText("tidak ada nomor telepon");
            textViewNoTelp.setTextColor(ContextCompat.getColor(
                    getApplicationContext(), android.R.color.tertiary_text_light));
            textViewNoTelp.setTypeface(null, Typeface.ITALIC);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_PRODI_REQUEST_CODE && resultCode == RESULT_OK) {
            String namaProdi = data.getStringExtra("NAMA_PRODI_UPDATED");
            Snackbar.make(coordinatorLayout, namaProdi + " berhasil diupdate.",
                    Snackbar.LENGTH_LONG).show();

            mProdi = ProgramStudi.load(ProgramStudi.class, mProdi.getId());
            setViewComponent();

            prodiEdited = 1;
        }
    }
}
