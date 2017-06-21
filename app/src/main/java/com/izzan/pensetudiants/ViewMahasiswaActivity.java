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

import com.izzan.pensetudiants.models.Mahasiswa;

public class ViewMahasiswaActivity extends AppCompatActivity {

    private TextView textViewNama;
    private TextView textViewNRP;
    private TextView textViewProdi;
    private TextView textViewNoHp;

    private ImageButton buttonBack;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;

    private Mahasiswa mMahasiswa;
    private int mahasiswaEdited = 0;
    public static final int EDIT_MAHASISWA_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mahasiswa);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Long mahasiswaId = extras.getLong("MAHASISWA_ID");
            mMahasiswa = Mahasiswa.load(Mahasiswa.class, mahasiswaId);
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.viewMahasiswa_coordinationLayout);

        textViewNama = (TextView) findViewById(R.id.viewMahasiswa_textViewNama);
        textViewNRP  = (TextView) findViewById(R.id.viewMahasiswa_textViewNRP);
        textViewProdi  = (TextView) findViewById(R.id.viewMahasiswa_textViewProdi);
        textViewNoHp = (TextView) findViewById(R.id.viewMahasiswa_textViewNoHp);

        buttonBack = (ImageButton) findViewById(R.id.viewMahasiswa_buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                if (mahasiswaEdited == 1){
                    setResult(RESULT_OK, returnIntent);
                } else {
                    setResult(RESULT_CANCELED, returnIntent);
                }

                finish();
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.viewMahasiswa_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewMahasiswaActivity.this, EditMahasiswaActivity.class);
                intent.putExtra("MAHASISWA_ID_EDIT", mMahasiswa.getId());
                startActivityForResult(intent, EDIT_MAHASISWA_REQUEST_CODE);
            }
        });

        setViewComponent();
    }

    private void setViewComponent(){
        textViewNama.setText(mMahasiswa.getNama());
        textViewNRP.setText(String.valueOf(mMahasiswa.getNrp()));
        textViewProdi.setText(mMahasiswa.getProgramStudi().getNamaProdi());

        if(!TextUtils.isEmpty(mMahasiswa.getNoHp())){
            textViewNoHp.setText(mMahasiswa.getNoHp());
            textViewNoHp.setTextColor(ContextCompat.getColor(
                    getApplicationContext(), android.R.color.primary_text_light));
            textViewNoHp.setTypeface(null, Typeface.NORMAL);
        } else {
            textViewNoHp.setText("tidak ada nomor telepon");
            textViewNoHp.setTextColor(ContextCompat.getColor(
                    getApplicationContext(), android.R.color.tertiary_text_light));
            textViewNoHp.setTypeface(null, Typeface.ITALIC);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_MAHASISWA_REQUEST_CODE && resultCode == RESULT_OK) {
            String namaMahasiswa = data.getStringExtra("NAMA_MAHASISWA_UPDATED");
            Snackbar.make(coordinatorLayout, "Data " + namaMahasiswa + " berhasil diupdate.",
                    Snackbar.LENGTH_LONG).show();

            mMahasiswa = Mahasiswa.load(Mahasiswa.class, mMahasiswa.getId());
            setViewComponent();

            mahasiswaEdited = 1;
        }
    }
}
