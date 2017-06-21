package com.izzan.pensetudiants;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.izzan.pensetudiants.adapters.MahasiswaRecyclerViewAdapter;
import com.izzan.pensetudiants.models.Mahasiswa;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MahasiswaFragment extends Fragment {

    private List<Mahasiswa> mMahasiswaList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MahasiswaRecyclerViewAdapter adapter;

    public static final int CREATE_MAHASISWA_REQUEST_CODE = 100;
    public static final int VIEW_MAHASISWA_REQUEST_CODE = 101;
    public static final int EDIT_MAHASISWA_REQUEST_CODE = 102;


    public MahasiswaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mahasiswa, container, false);

        adapter = new MahasiswaRecyclerViewAdapter(mMahasiswaList, view.getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.fragmentMahasiswa_recyclerView);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView,
                new RecyclerTouchListener.ClickListener() {


                    @Override
                    public void onClick(View view, int position) {
//                        ProgramStudi mProdi = mProgramStudiList.get(position);
//
//                        Intent intent = new Intent(getActivity(), ViewProdiActivity.class);
//                        intent.putExtra("PRODI_ID", mProdi.getId());
//
//                        startActivityForResult(intent, VIEW_PRODI_REQUEST_CODE);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }

                }));

        registerForContextMenu(recyclerView);

        reloadData();

        return view;
    }

    public void reloadData() {
        List<Mahasiswa> list = Mahasiswa.getAll();
        mMahasiswaList.clear();
        mMahasiswaList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void deleteMahasiswa(Mahasiswa mahasiswa) {
        Mahasiswa.delete(Mahasiswa.class, mahasiswa.getId());
        reloadData();
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }

        final MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.fab.setImageResource(R.drawable.ic_person_add_black_24dp);
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateMahasiswaActivity.class);
                startActivityForResult(intent, CREATE_MAHASISWA_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = adapter.getPosition();

        } catch (Exception e) {
//            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }

        if (position != -1) {
            Mahasiswa mMahasiswa = adapter.getItem(position);
            switch (item.getItemId()) {
                case MenuItemId.EDIT_MAHASISWA:
//                    Intent intent = new Intent(getActivity(), EditMahasiswaActivity.class);
//                    intent.putExtra("MAHASISWA_ID_EDIT", mMahasiswa.getId());
//                    startActivityForResult(intent, EDIT_MAHASISWA_REQUEST_CODE);
                    break;
                case MenuItemId.DELETE_MAHASISWA:
                    deleteMahasiswa(mMahasiswa);
                    Snackbar.make(getView(), mMahasiswa.getNama() + " telah dihapus.",
                            Snackbar.LENGTH_LONG).show();
                    break;
            }
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_MAHASISWA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String mahasiswaBaru = data.getStringExtra("MAHASISWA_BARU");
            Snackbar.make(getView(), mahasiswaBaru + " berhasil ditambahkan.",
                    Snackbar.LENGTH_LONG).show();

            reloadData();
        }

//        if (requestCode == EDIT_PRODI_REQUEST_CODE && resultCode == Activity.RESULT_OK){
//            String namaProdi = data.getStringExtra("NAMA_PRODI_UPDATED");
//            Snackbar.make(getView(), namaProdi + " berhasil diupdate.",
//                    Snackbar.LENGTH_LONG).show();
//
//            reloadData();
//        }
//
//        if (requestCode == VIEW_PRODI_REQUEST_CODE && resultCode == Activity.RESULT_OK){
//            reloadData();
//        }
    }

}
