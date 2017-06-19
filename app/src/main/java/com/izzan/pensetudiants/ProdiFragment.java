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

import com.izzan.pensetudiants.adapters.ProdiRecyclerViewAdapter;
import com.izzan.pensetudiants.models.ProgramStudi;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProdiFragment extends Fragment {

    private List<ProgramStudi> mProgramStudiList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private ProdiRecyclerViewAdapter mAdapter;

    public static final int VIEW_PRODI_REQUEST_CODE = 201;
    public static final int EDIT_PRODI_REQUEST_CODE = 202;

    public ProdiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prodi, container, false);

        mAdapter = new ProdiRecyclerViewAdapter(mProgramStudiList, view.getContext());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentProdi_recyclerView);
        mRecyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        ProgramStudi mProdi = mProgramStudiList.get(position);

                        Intent intent = new Intent(getActivity(), ViewProdiActivity.class);
                        intent.putExtra("PRODI_ID", mProdi.getId());

                        startActivityForResult(intent, VIEW_PRODI_REQUEST_CODE);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

        registerForContextMenu(mRecyclerView);

        reloadData();

        return view;
    }

    public void reloadData() {
        List<ProgramStudi> list = ProgramStudi.getAll();
        mProgramStudiList.clear();
        mProgramStudiList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    private void deleteProdi(ProgramStudi prodi) {
        ProgramStudi.delete(ProgramStudi.class, prodi.getId());
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
        mainActivity.fab.setImageResource(R.drawable.ic_add_black_24dp);
        mainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goToCreateProdiActivity();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = mAdapter.getPosition();

        } catch (Exception e) {
//            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }

        if (position != -1) {
            ProgramStudi mProdi = mAdapter.getItem(position);
            switch (item.getItemId()) {
                case 1:
                    Intent intent = new Intent(getActivity(), EditProdiActivity.class);
                    intent.putExtra("PRODI_ID_EDIT", mProdi.getId());
                    startActivityForResult(intent, EDIT_PRODI_REQUEST_CODE);
                    break;
                case 2:
                    deleteProdi(mProdi);
                    Snackbar.make(getView(), mProdi.getNamaProdi() + " telah dihapus.",
                            Snackbar.LENGTH_LONG).show();
            }
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PRODI_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            String namaProdi = data.getStringExtra("NAMA_PRODI_UPDATED");
            Snackbar.make(getView(), namaProdi + " berhasil diupdate.",
                    Snackbar.LENGTH_LONG).show();

            reloadData();
        }

        if (requestCode == VIEW_PRODI_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            reloadData();
        }
    }

}
