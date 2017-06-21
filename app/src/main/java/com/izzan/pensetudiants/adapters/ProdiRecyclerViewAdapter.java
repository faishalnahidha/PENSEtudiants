package com.izzan.pensetudiants.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.izzan.pensetudiants.MenuItemId;
import com.izzan.pensetudiants.R;
import com.izzan.pensetudiants.models.ProgramStudi;

import java.util.List;

/**
 * Created by Aizen on 17 Jun 2017.
 */

public class ProdiRecyclerViewAdapter extends RecyclerView.Adapter<ProdiRecyclerViewAdapter.MyViewHolder> {

    private List<ProgramStudi> mProgramStudiList;
    private Context context;
    private int position;

    public ProdiRecyclerViewAdapter(List<ProgramStudi> mProgramStudiList, Context context) {
        this.mProgramStudiList = mProgramStudiList;
        this.context = context;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ProgramStudi getItem(int position){
        return mProgramStudiList.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_prodi, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ProgramStudi prodi = mProgramStudiList.get(position);
        holder.namaProdi.setText(prodi.getNamaProdi());
        holder.departemen.setText(prodi.getDepartemen());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getLayoutPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProgramStudiList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnCreateContextMenuListener {

        public TextView namaProdi, departemen;

        public MyViewHolder(View itemView) {
            super(itemView);
            namaProdi = (TextView) itemView.findViewById(R.id.rowProdi_textViewNamaProdi);
            departemen = (TextView) itemView.findViewById(R.id.rowProdi_textViewDepartement);

            itemView.setOnCreateContextMenuListener(this);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(namaProdi.getText().toString());
            menu.add(200, MenuItemId.EDIT_PRODI, 1, "Edit");
            menu.add(200, MenuItemId.DELETE_PRODI, 2, "Hapus");
        }
    }
}
