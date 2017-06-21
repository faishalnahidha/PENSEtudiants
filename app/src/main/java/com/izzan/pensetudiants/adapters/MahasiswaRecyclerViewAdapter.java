package com.izzan.pensetudiants.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.izzan.pensetudiants.MenuItemId;
import com.izzan.pensetudiants.R;
import com.izzan.pensetudiants.models.Mahasiswa;

import java.util.List;

/**
 * Created by Aizen on 21 Jun 2017.
 */

public class MahasiswaRecyclerViewAdapter extends RecyclerView.Adapter<MahasiswaRecyclerViewAdapter.MyViewHolder> {
    private List<Mahasiswa> mMahasiswaList;
    private Context context;
    private int position;
    ColorGenerator generator = ColorGenerator.MATERIAL;

    public MahasiswaRecyclerViewAdapter(List<Mahasiswa> mMahasiswaList, Context context) {
        this.mMahasiswaList = mMahasiswaList;
        this.context = context;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Mahasiswa getItem(int position) {
        return mMahasiswaList.get(position);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_mahasiswa, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Mahasiswa mahasiswa = mMahasiswaList.get(position);
        holder.nama.setText(mahasiswa.getNama());
        holder.nrp.setText(String.valueOf(mahasiswa.getNrp()));
        holder.prodi.setText(mahasiswa.getProgramStudi().getNamaProdi());

        String letter = String.valueOf(mahasiswa.getNama().charAt(0));
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getColor(getItem(position)));
        holder.letterIcon.setImageDrawable(drawable);


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
        return mMahasiswaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {

        public TextView nama, nrp, prodi;
        public ImageView letterIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.rowMahasiswa_textViewNama);
            nrp = (TextView) itemView.findViewById(R.id.rowMahasiswa_textViewNRP);
            prodi = (TextView) itemView.findViewById(R.id.rowMahasiswa_textViewProdi);
            letterIcon = (ImageView) itemView.findViewById(R.id.rowMahasiswa_imageView);

            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(nama.getText().toString());
            menu.add(100, MenuItemId.EDIT_MAHASISWA, 1, "Edit");
            menu.add(100, MenuItemId.DELETE_MAHASISWA, 2, "Hapus");
        }
    }
}
